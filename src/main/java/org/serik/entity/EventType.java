package org.serik.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "event_types")
@NamedQueries({
	@NamedQuery(name = EventType.QRY_FINDALL_EVENTTYPES, query = "SELECT e FROM EventType e ORDER BY e.name"),
	@NamedQuery(name = EventType.QRY_FIND_EVENTTYPE_BY_NAME, query = "SELECT e FROM EventType e WHERE e.name=:name"),
	@NamedQuery(name = EventType.QRY_FIND_EVENTTYPE_BY_PREFIX, query = "SELECT e FROM EventType e WHERE e.prefix=:prefix") })
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
// @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,
// property = "@id")
public class EventType {
    public static final String QRY_FINDALL_EVENTTYPES = "EventType.findAllEventTypes";
    public static final String QRY_FIND_EVENTTYPE_BY_NAME = "EventType.findEventTypeByName";
    public static final String QRY_FIND_EVENTTYPE_BY_PREFIX = "EventType.findEventTypeByPrefix";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false)
    @Size(min = 1, max = 20)
    private String name;

    @Column(name = "event_type_prefix", nullable = false)
    @Size(min = 3, max = 3)
    private String prefix;

    @Column(name = "max_participants", nullable = false)
    @Min(1)
    private int maximumParticipants;

    @Column(name = "waiting_list")
    private boolean waitingList;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    // GETTERS AND SETTERS

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getPrefix() {
	return prefix;
    }

    public void setPrefix(String prefix) {
	this.prefix = prefix;
    }

    public int getMaximumParticipants() {
	return maximumParticipants;
    }

    public void setMaximumParticipants(int maximumParticipants) {
	this.maximumParticipants = maximumParticipants;
    }

    public boolean isWaitingList() {
	return waitingList;
    }

    public void setWaitingList(boolean waitingList) {
	this.waitingList = waitingList;
    }

    public Room getRoom() {
	return room;
    }

    public void setRoom(Room room) {
	this.room = room;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	EventType other = (EventType) obj;
	if (id != other.id)
	    return false;
	return true;
    }

}
