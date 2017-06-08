package org.serik.resource;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.serik.entity.Person;
import org.serik.interceptor.Auditable;
import org.serik.service.PersonService;
import org.slf4j.Logger;

@Path("persons")
@Auditable
public class PersonResource {
    @Inject
    private Logger logger;
    @Inject
    private PersonService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userid}")
    public Response getPersonByUserId(@PathParam("userid") String userid) {
	Person p = service.findPersonByUserId(userid);
	return Response.ok(p).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("id/{id}")
    public Response getPersonById(@PathParam("id") long id) {
	Person p = service.findPersonById(id);
	return Response.ok(p).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPersons(@QueryParam("maxresult") int maxResult) {
	List<Person> persons = service.findAllPersons(maxResult);
	if (persons.size() == 0) {
	    return Response.status(Status.NOT_FOUND).build();
	}

	return Response.ok(new GenericEntity<List<Person>>(persons) {
	}).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPerson(@Valid Person person) {
	Person p = service.create(person);
	return Response.status(Status.CREATED).entity(p).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePerson(@Valid Person person) {
	logger.info("Updates Persons: " + person);
	Person p = service.update(person);
	// logger.info("Person updated: " + person);
	return Response.ok(p).build();
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deletePerson(@Valid Person person) {
	service.delete(person);
	return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePerson(@PathParam("id") long id) {
	service.delete(id);
	return Response.ok().build();
    }
}
