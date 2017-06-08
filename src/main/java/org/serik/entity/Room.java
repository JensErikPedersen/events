package org.serik.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.serik.validator.NotEmpty;

@Entity
@Table(name = "rooms")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    @Column(name = "name", length = 20)
    private String name;

    @NotEmpty
    @Column(name = "location", length = 20)
    private String location;

    @NotEmpty
    @Column(name = "max_participants")
    private int maxParticipants;

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

    public void setLocation(String location) {
	this.location = location;
    }

    public int getMaxParticipants() {
	return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
	this.maxParticipants = maxParticipants;
    }

}
