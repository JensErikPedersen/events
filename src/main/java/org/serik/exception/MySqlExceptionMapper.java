package org.serik.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class MySqlExceptionMapper implements ExceptionMapper<MySqlException> {
    private static final Logger logger = LoggerFactory.getLogger(MySqlExceptionMapper.class);

    @Override
    public Response toResponse(MySqlException ex) {
	Status status = Status.INTERNAL_SERVER_ERROR;
	Error error = new Error();
	logger.info("toResponse called for Exception: " + ex.getCause().getClass().getSimpleName() + " - "
		+ ex.getMessage());

	String exClass = ex.getCause().getClass().getSimpleName();
	if (exClass.equals("NoResultException")) {
	    error = new Error(10, ex.getMessage());
	    status = Status.NOT_FOUND;
	}
	return Response.status(status).entity(error).build();
    }

}
