package org.serik.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.serik.entity.Person;
import org.serik.interceptor.Auditable;
import org.serik.service.PersonService;
import org.slf4j.Logger;

@Path("persons")
public class PersonResource {
    @Inject
    private Logger logger;
    @Inject
    private PersonService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    @Auditable
    public Response getPersonByUserId(@PathParam("id") String userid) {
	Person p = service.findPersonByUserId(userid);
	return Response.ok(p).build();
    }

}
