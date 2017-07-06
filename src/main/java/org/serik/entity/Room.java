package org.serik.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.serik.validator.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "rooms")
@NamedQueries({
	@NamedQuery(name = Room.QRY_FINDROOM_BYNAME, query = "SELECT r FROM Room r WHERE r.name=:name"),
	@NamedQuery(name = Room.QRY_FINDALLROOMS, query = "SELECT r FROM Room r ORDER BY r.id")
})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
// @JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class,
// property = "@id")
public class Room {
    public final static String QRY_FINDALLROOMS = "Room.findAllRooms";
    public final static String QRY_FINDROOM_BYNAME = "Room.findByName";

    @Transient
    private static final Logger logger = LoggerFactory.getLogger(Room.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name = "name", length = 20)
    private String name;

    @NotEmpty
    @Column(name = "location", length = 20)
    private String location;

    @Min(1)
    @Column(name = "max_participants")
    private int maxParticipants;

    // // @JsonIgnore
    // @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    // private Set<EventType> eventTypes;

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

    public String getLocation() {
	return location;
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
	Room other = (Room) obj;
	if (id != other.id)
	    return false;
	return true;
    }

    public void setLocation(String location) {
	this.location = location;
    }

    public int getMaxParticipants() {
	return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
	this.maxParticipants = maxParticipants;
    }

    // public Set<EventType> getEventTypes() {
    // return eventTypes;
    // }
    //
    // public void setEventTypes(Set<EventType> eventTypes) {
    // this.eventTypes = eventTypes;
    // }

}
