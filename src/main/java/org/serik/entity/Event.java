package org.serik.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "event_type_id", nullable = false, updatable = false)
    private EventType eventtype;

    @Column(name = "start_datetime", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDateTime;

    @Column(name = "end_datetime", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDateTime;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false, updatable = false)
    private Person instructor;

    @ManyToOne
    @JoinColumn(name = "parent_event_id")
    private Event parent;

    // @OneToMany(mappedBy = "parent")
    // private Collection<Event> children;

    @Column(name = "status", nullable = false)
    private char status;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public EventType getEventtype() {
	return eventtype;
    }

    public void setEventtype(EventType eventtype) {
	this.eventtype = eventtype;
    }

    public Date getStartDateTime() {
	return startDateTime;
    }

    public void setStartDateTime(Date startDateTime) {
	this.startDateTime = startDateTime;
    }

    public Date getEndDateTime() {
	return endDateTime;
    }

    public void setEndDateTime(Date endDateTime) {
	this.endDateTime = endDateTime;
    }

    public Person getInstructor() {
	return instructor;
    }

    public void setInstructor(Person instructor) {
	this.instructor = instructor;
    }

    public Event getParent() {
	return parent;
    }

    public void setParent(Event parent) {
	this.parent = parent;
    }

    // public Collection<Event> getChildren() {
    // return children;
    // }
    //
    // public void setChildren(Collection<Event> children) {
    // this.children = children;
    // }

    public char getStatus() {
	return status;
    }

    public void setStatus(char status) {
	this.status = status;
    }

}
