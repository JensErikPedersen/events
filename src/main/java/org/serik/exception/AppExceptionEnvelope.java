package org.serik.exception;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Used to carry exception data in json format to clients
 * 
 * @author eky
 *
 */
@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AppExceptionEnvelope implements Serializable {

	private final int code;

	private final String message;

	private final String description;

	private List<ValidationExceptionEnvelope> errors;

	public AppExceptionEnvelope(int code, String message, String description, List<ValidationExceptionEnvelope> errors) {
		this.code = code;
		this.message = message;
		this.description = description;
		this.errors = errors;
	}

	public AppExceptionEnvelope(int code, String message, String description) {
		this(code, message, description, null);
	}

	public AppExceptionEnvelope(int code, String message) {
		this(code, message, null, null);
	}

	public void addValidationError(ValidationExceptionEnvelope error) {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		getErrors().add(error);
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

	public List<ValidationExceptionEnvelope> getErrors() {
		return errors;
	}
}
