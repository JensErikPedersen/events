package org.serik.resource;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.serik.entity.Event;
import org.serik.facade.EventFacade;
import org.serik.interceptor.Auditable;
import org.slf4j.Logger;

@Path("events")
@Auditable
public class EventResource {
    @Inject
    private Logger logger;

    @Inject
    private EventFacade facade;

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEventById(@PathParam("id") long id) {
	Event evt = facade.findById(id);
	return Response.ok(evt).build();
    }
}
