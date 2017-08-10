package org.serik.facade;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.serik.service.EventService;
import org.slf4j.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EventFacade {
    @Inject
    private EventService service;

    @Inject
    private Logger logger;
}
