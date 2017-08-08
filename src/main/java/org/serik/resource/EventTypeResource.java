package org.serik.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.serik.entity.EventType;
import org.serik.service.EventTypeService;
import org.slf4j.Logger;

@Path("eventtypes")
public class EventTypeResource {
    @Inject
    private Logger logger;

    @Inject
    private EventTypeService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllEventTypes(@QueryParam("maxResults") int maxResults) {
	List<EventType> list = service.findAllEventTypes(maxResults);
	if (list.size() == 0) {
	    return Response.status(Status.NOT_FOUND).build();
	}
	logger.info("Found " + list.size() + " EventTypes");

	return Response.ok(new GenericEntity<List<EventType>>(list) {
	}).build();
    }

    @GET
    @Path("name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEventTypeByName(@PathParam("name") String name) {
	EventType evtype = service.findEventTypeByName(name);
	return Response.ok(evtype).build();
    }

    @GET
    @Path("prefix/{prefix}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findEventTypeByPrefix(@PathParam("prefix") String prefix) {
	EventType evtype = service.findEventTypeByPrefix(prefix);
	return Response.ok(evtype).build();
    }
}
