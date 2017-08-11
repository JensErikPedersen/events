package org.serik.service;

import javax.ejb.Lock;
import javax.ejb.LockType;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceUnit;

import org.serik.entity.Event;
import org.slf4j.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class EventService {
    @PersistenceUnit(unitName = "EventsManager")
    private EntityManager em;

    @Inject
    private Logger logger;

    @Lock(LockType.READ)
    public Event findById(long id) {
	return em.find(Event.class, id);
    }

}
