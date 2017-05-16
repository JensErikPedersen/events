package org.serik.dao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;

import org.serik.entity.Person;
import org.serik.exception.MySqlException;
import org.slf4j.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersonDao {
    @PersistenceContext(unitName = "EventsManager")
    private EntityManager em;

    @Inject
    private Logger logger;

    public void save(Person person) {
	em.persist(person);
    }

    public Person findPersonById(long id) {
	return em.find(Person.class, id);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public Person findPersonByUserId(String userid) throws MySqlException {
	logger.info("userid to find: " + userid);
	try {
	    TypedQuery<Person> q = em.createNamedQuery(Person.QRY_FINDPERSON_BYUSER, Person.class);
	    q.setParameter("userid", userid);
	    return q.getSingleResult();
	} catch (NoResultException e) {
	    logger.error(e.getMessage());
	    throw new MySqlException("No Person where found with user id: " + userid, e);
	} catch (QueryTimeoutException e) {
	    logger.error(e.getMessage(), e);
	    throw new MySqlException(
		    "Finding the Person with user id " + userid + " could not be performed due to timeout", e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new MySqlException("Exception occured while retrieving Person with user: " + userid, e);
	}
    }
}
