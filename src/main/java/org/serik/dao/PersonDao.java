package org.serik.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.serik.MySqlException;
import org.serik.entity.Person;

@Stateless
public class PersonDao {
	@PersistenceContext(unitName="EventsManager")
	private EntityManager em;
	
	
	public void save(Person person) {
		em.persist(person);
	}
	
	public Person findPersonById(long id) {
		return em.find(Person.class, id);
	}
	
	public Person findPersonById(String id) throws MySqlException {
		TypedQuery<Person> q = em.createNamedQuery(Person.QRY_FINDPERSON_BYUSER, Person.class);
		return q.getSingleResult();
	}
}
