package org.serik.service;

import java.util.List;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;

import org.serik.entity.Person;
import org.serik.exception.SqlException;
import org.slf4j.Logger;

/**
 * Service & Facade Layer combined since the need for a Facade is very limited
 * Client call straight to this Service
 * 
 * @author eky
 *
 */

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class PersonService {
    @PersistenceContext(unitName = "EventsManager")
    private EntityManager em;

    @Inject
    private Logger logger;

    public Person create(Person person) {
	try {
	    em.persist(person);
	    em.flush();
	    em.refresh(person);
	    return person;
	} catch (EntityExistsException e) {
	    throw new SqlException("Person with user id: " + person.getId() + " already exists in database", e);
	} catch (PersistenceException e) {
	    logger.error("Exception catched: " + e);
	    for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
		logger.info("Exception:" + t);
	    }
	    throw new SqlException(e.getMessage(), e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while creating Person with user id: " + person.getId(), e);
	}
    }

    @Lock(LockType.READ)
    public Person findPersonById(long id) {
	return em.find(Person.class, id);
    }

    // @TransactionAttribute(TransactionAttributeType.REQUIRED)
    @Lock(LockType.READ)
    public Person findPersonByUserId(String userid) {
	logger.info("userid to find: " + userid);
	try {
	    TypedQuery<Person> q = em.createNamedQuery(Person.QRY_FINDPERSON_BYUSER, Person.class);
	    q.setParameter("userid", userid);
	    return q.getSingleResult();
	} catch (NoResultException e) {
	    logger.error(e.getMessage());
	    throw new SqlException("No Person where found with user id: " + userid, e);
	} catch (QueryTimeoutException e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException(
		    "Finding the Person with user id " + userid + " could not be performed due to timeout", e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while retrieving Person with user: " + userid, e);
	}
    }

    @Lock(LockType.READ)
    public List<Person> findAllPersons(int maxResult) {
	try {
	    TypedQuery<Person> q = em.createNamedQuery(Person.QRY_FINDALLPERSONS, Person.class);
	    return q.setMaxResults(maxResult).getResultList();
	} catch (QueryTimeoutException e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("List of Persons could not be found due to timeout", e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while retrieving Person List", e);
	}

    }

    public Person update(Person person) {
	Person p = em.merge(person);
	em.flush(); // without these next two steps, the updated entity is not
		    // returned from the database. However, there is an extra
	em.refresh(p);
	return p;
    }

    public void delete(Person person) {
	em.remove(em.merge(person));
    }

    public void delete(long id) {
	try {
	    Person p = em.getReference(Person.class, id);
	    em.remove(p);
	} catch (EntityNotFoundException e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Person with id " + id + " could not be found", e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while deleting Person", e);
	}
    }
}
