package org.serik.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MySqlExceptionMapper implements ExceptionMapper<MySqlException> {

    @Override
    public Response toResponse(MySqlException e) {

	return null;
    }

}
