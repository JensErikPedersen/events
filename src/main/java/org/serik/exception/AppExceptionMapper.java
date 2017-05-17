package org.serik.exception;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class AppExceptionMapper implements ExceptionMapper<Exception> {

	private final static Logger logger = LoggerFactory.getLogger(AppExceptionMapper.class);

	@Context
	private HttpHeaders headers;

	@Override
	public Response toResponse(Exception e) {
		logger.info("AppExceptionMapper called");
		ExceptionDetails details = findStatus(e);
		return Response.status(details.getStatus())
				.entity(new AppExceptionEnvelope(details.getCode(), details.getMessage(), details.getDescription()))
				.type(headers.getMediaType())
				.build();
	}

	private ExceptionDetails findStatus(Exception e) {
		logger.info("findStatus called");
		ExceptionDetails details = new ExceptionDetails();
		if (e instanceof SqlException || e.getCause() != null) {
			String clzName = e.getCause().getClass().getName();
			details.setStatus(Status.BAD_REQUEST); // default value
			if ("com.ibm.db2.jcc.am.SqlIntegrityConstraintViolationException".equals(clzName)) {
				details.setCode(20)
						.setMessage("Constraint violation occured")
						.setDescription(e.getMessage());
			} else if ("javax.persistence.NoResultException".equals(clzName)) {
				details.setCode(30)
						.setMessage(e.getMessage())
						.setStatus(Status.NOT_FOUND);
			} else if ("javax.ws.rs.NotFoundException".equals(clzName)) {
				details.setCode(30)
						.setMessage(e.getMessage())
						.setStatus(Status.NOT_FOUND);
			} else if ("javax.persistence.QueryTimeoutException".equals(clzName)) {
				details.setCode(40)
						.setMessage(e.getMessage())
						.setStatus(Status.INTERNAL_SERVER_ERROR);
			} // defaults if no match is found above
			else {
				details.setCode(1)
						.setMessage("Internal Server Error")
						.setDescription(e.getMessage())
						.setStatus(Status.INTERNAL_SERVER_ERROR);
			}
		} else {
			// Exception is not an instance of SqlException or cause is null
			logger.info(e.getMessage(), e);
			details.setCode(1)
					.setMessage("Internal Server Error")
					.setDescription(e.getMessage())
					.setStatus(Status.INTERNAL_SERVER_ERROR);
		}

		return details;
	}

	private class ExceptionDetails {
		private int code;
		private String message;
		private String description;
		private Status status;

		public int getCode() {
			return code;
		}

		public ExceptionDetails setCode(int code) {
			this.code = code;
			return this;
		}

		public String getMessage() {
			return message;
		}

		public ExceptionDetails setMessage(String message) {
			this.message = message;
			return this;
		}

		public String getDescription() {
			return description;
		}

		public ExceptionDetails setDescription(String description) {
			this.description = description;
			return this;
		}

		public Status getStatus() {
			return status;
		}

		public ExceptionDetails setStatus(Status status) {
			this.status = status;
			return this;
		}

	}
}
