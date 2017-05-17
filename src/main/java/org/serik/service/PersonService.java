package org.serik.service;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
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
@Local
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
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while creating Person with user id: " + person.getId(), e);
	}
    }

    public Person findPersonById(long id) {
	return em.find(Person.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
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
}
