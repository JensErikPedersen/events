package org.serik.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;

import org.serik.entity.EventType;
import org.serik.exception.SqlException;
import org.slf4j.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EventTypeService {
    @PersistenceContext(unitName = "EventsManager")
    private EntityManager em;

    @Inject
    private Logger logger;

    public List<EventType> findAllEventTypes(int maxResults) {
	try {
	    TypedQuery<EventType> q = em.createNamedQuery(EventType.QRY_FINDALL_EVENTTYPES, EventType.class);
	    List<EventType> list = q.setMaxResults(maxResults).getResultList();
	    logger.info("Found " + list.size() + " EventTypes");
	    return list;
	} catch (QueryTimeoutException e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("List of EventType could not be found due to timeout", e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while retrieving EventType List", e);
	}
    }

    public EventType create(EventType eventtype) {
	if (eventtype.getRoom() == null) {
	    throw new IllegalArgumentException("EventType has not a Room attached");
	}
	try {
	    em.persist(eventtype);
	    em.flush();
	    em.refresh(eventtype);
	    return eventtype;
	} catch (EntityExistsException e) {
	    throw new SqlException("EventType with name: " + eventtype.getName() + " already exists in database", e);
	} catch (PersistenceException e) {
	    logger.error("Exception catched: " + e);
	    throw new SqlException(e.getMessage(), e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while creating EventType with name: " + eventtype.getName(), e);
	}
    }
}
