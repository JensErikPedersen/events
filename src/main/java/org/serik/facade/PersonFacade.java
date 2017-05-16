package org.serik.facade;

import javax.inject.Inject;

import org.serik.dao.PersonDao;
import org.serik.entity.Person;
import org.serik.exception.MySqlException;
import org.slf4j.Logger;

public class PersonFacade {

    @Inject
    private Logger logger;

    @Inject
    private PersonDao personDao;

    public Person findPersonByUserId(String userid) throws MySqlException {
	return personDao.findPersonByUserId(userid);
    }
}
