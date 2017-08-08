package org.serik.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private EventType eventtype;

    @Column(name = "event_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date eventDate;

    @Column(name = "start_time", nullable = false)
    @Temporal(TemporalType.TIME)
    private Date startTime;

    @Column(name = "end_time")
    @Temporal(TemporalType.TIME)
    private Date endTime;

    @OneToOne
    private Person instructor;

    private Event parent;

    @Column(name = "status", nullable = false)
    private char status;

}
