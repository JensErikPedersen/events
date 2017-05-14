package org.serik.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.serik.entity.Person;

@Stateless
public class PersonDao {
	@PersistenceContext(unitName="EventsManager")
	private EntityManager em;
	
	
	public void save(Person person) {
		em.persist(person);
	}
}
