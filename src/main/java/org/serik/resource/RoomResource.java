package org.serik.resource;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.serik.entity.Room;
import org.serik.interceptor.Auditable;
import org.serik.service.RoomService;
import org.slf4j.Logger;

@Path("rooms")
@Auditable
public class RoomResource {

    @Inject
    private Logger logger;

    @Inject
    private RoomService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAllRooms(@QueryParam("maxresults") int maxResults) {
	List<Room> rooms = service.findAllRooms(maxResults);
	if (rooms.size() == 0) {
	    return Response.status(Status.NOT_FOUND).build();
	}

	return Response.ok(new GenericEntity<List<Room>>(rooms) {
	}).build();
    }
}
