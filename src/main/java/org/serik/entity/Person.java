package org.serik.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.serik.validator.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@SuppressWarnings("serial")
@Entity
@Table(name = "persons")
@NamedQueries({
	@NamedQuery(name = Person.QRY_FINDPERSON_BYUSER, query = "SELECT p FROM Person p WHERE p.userId=:userid"),
	@NamedQuery(name = Person.QRY_FINDALLPERSONS, query = "SELECT p FROM Person p ORDER BY p.userId") })
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Person implements Serializable {
    public static final String QRY_FINDPERSON_BYUSER = "Person.findByUser";
    public static final String QRY_FINDALLPERSONS = "Person.findAllUsers";

    @Transient
    private static final Logger logger = LoggerFactory.getLogger(Person.class);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "last_name")
    @NotEmpty
    private String lastName;

    @NotEmpty
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty
    @Size(min = 3, max = 3)
    @Column(name = "userid", unique = true)
    private String userId;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_phone")
    private String mobilePhone;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date modified;

    @PrePersist
    private void prePersist() {
	logger.info("Prepersist");
	created = new Date();
	modified = new Date();
    }

    @PreUpdate
    private void preUpdate() {
	logger.info("PreUpdate");
	modified = new Date();
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getMobilePhone() {
	return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
	this.mobilePhone = mobilePhone;
    }

    public Date getCreated() {
	return created;
    }

    public void setCreated(Date created) {
	this.created = created;
    }

    public Date getModified() {
	return modified;
    }

    public void setModified(Date modified) {
	this.modified = modified;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((created == null) ? 0 : created.hashCode());
	result = prime * result + ((email == null) ? 0 : email.hashCode());
	result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
	result = prime * result + ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
	result = prime * result + ((modified == null) ? 0 : modified.hashCode());
	result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
	Person other = (Person) obj;
	if (created == null) {
	    if (other.created != null)
		return false;
	} else if (!created.equals(other.created))
	    return false;
	if (email == null) {
	    if (other.email != null)
		return false;
	} else if (!email.equals(other.email))
	    return false;
	if (firstName == null) {
	    if (other.firstName != null)
		return false;
	} else if (!firstName.equals(other.firstName))
	    return false;
	if (id != other.id)
	    return false;
	if (lastName == null) {
	    if (other.lastName != null)
		return false;
	} else if (!lastName.equals(other.lastName))
	    return false;
	if (mobilePhone == null) {
	    if (other.mobilePhone != null)
		return false;
	} else if (!mobilePhone.equals(other.mobilePhone))
	    return false;
	if (modified == null) {
	    if (other.modified != null)
		return false;
	} else if (!modified.equals(other.modified))
	    return false;
	if (userId == null) {
	    if (other.userId != null)
		return false;
	} else if (!userId.equals(other.userId))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "Person [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", userId=" + userId
		+ ", email=" + email + ", mobilePhone=" + mobilePhone + ", created=" + created + ", modified="
		+ modified + "]";
    }

}
