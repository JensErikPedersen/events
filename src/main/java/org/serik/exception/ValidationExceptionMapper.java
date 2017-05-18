package org.serik.exception;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

    @Inject
    private Logger logger;
    @Context
    private HttpHeaders headers;

    @SuppressWarnings("rawtypes")
    @Override
    public Response toResponse(ConstraintViolationException e) {
	logger.info("BeanValidation exception mapper called");
	AppExceptionEnvelope env = new AppExceptionEnvelope(10, "Validation Failed", "BeanValidation not passed");
	Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
	for (ConstraintViolation violation : violations) {
	    ValidationExceptionEnvelope vError = new ValidationExceptionEnvelope();
	    vError.setField(violation.getPropertyPath().toString());
	    vError.setMessage(violation.getRootBeanClass().getSimpleName() + "." + violation.getPropertyPath() + " "
		    + violation.getMessage());
	    // vError.setMessage(violation.getMessage());
	    env.addValidationError(vError);
	}

	return Response.status(Status.BAD_REQUEST).entity(env).type(headers.getMediaType()).build();
    }
}
