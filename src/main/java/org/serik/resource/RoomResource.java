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

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("name/{name}")
    public Response findRoomByName(@PathParam("name") String name) {
	Room room = service.findRoomByName(name);
	if (room == null) {
	    return Response.status(Status.NOT_FOUND).build();
	}

	return Response.ok(room).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{id}")
    public Response findRoomById(@PathParam("id") int id) {
	Room room = service.findRoomById(id);
	if (room == null) {
	    return Response.status(Status.NOT_FOUND).build();
	}

	return Response.ok(room).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRoom(@Valid Room room) {
	Room r = service.create(room);
	return Response.status(Status.CREATED).entity(r).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRoom(@Valid Room room) {
	Room r = service.update(room);
	return Response.status(Status.OK).entity(r).build();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteRoom(Room room) {
	service.delete(room);
	return Response.ok().build();
    }

}
