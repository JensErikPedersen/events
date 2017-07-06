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
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.TypedQuery;

import org.serik.entity.Room;
import org.serik.exception.SqlException;
import org.slf4j.Logger;

@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class RoomService {
    @PersistenceContext(unitName = "EventsManager")
    private EntityManager em;

    @Inject
    private Logger logger;

    public Room findRoomById(long id) {
	return em.find(Room.class, id);
    }

    public Room findRoomByName(String name) {
	try {
	    TypedQuery<Room> q = em.createNamedQuery(Room.QRY_FINDROOM_BYNAME, Room.class);
	    q.setParameter("name", name);
	    return q.getSingleResult();
	} catch (NoResultException e) {
	    logger.error(e.getMessage());
	    throw new SqlException("No Room were found with name: " + name, e);
	} catch (QueryTimeoutException e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException(
		    "Finding the Room with name " + name + " could not be performed due to timeout", e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while retrieving Room with name: " + name, e);
	}
    }

    public List<Room> findAllRooms(int maxResults) {
	try {
	    TypedQuery<Room> q = em.createNamedQuery(Room.QRY_FINDALLROOMS, Room.class);
	    List<Room> rooms = q.setMaxResults(maxResults).getResultList();
	    return rooms;
	} catch (QueryTimeoutException e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("List of Rooms could not be found due to timeout", e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while retrieving Room List", e);
	}
    }

    public Room create(Room room) {
	try {
	    em.persist(room);
	    em.flush();
	    em.refresh(room);
	    return room;
	} catch (EntityExistsException e) {
	    throw new SqlException("Room with name: " + room.getName() + " already exists in database", e);
	} catch (PersistenceException e) {
	    logger.error("Exception catched: " + e);
	    // for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
	    // logger.info("Exception:" + t);
	    // }
	    throw new SqlException(e.getMessage(), e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while creating Room with name: " + room.getName(), e);
	}
    }

    public Room update(Room room) {
	Room r = em.merge(room);
	em.flush();
	em.refresh(r);
	return r;
    }

    public void delete(Room room) {
	em.remove(em.merge(room));
    }

    public void delete(long id) {
	try {
	    Room r = em.getReference(Room.class, id);
	    em.remove(r);
	} catch (EntityNotFoundException e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Person with id " + id + " could not be found", e);
	} catch (Exception e) {
	    logger.error(e.getMessage(), e);
	    throw new SqlException("Exception occured while deleting Person", e);
	}
    }
}
