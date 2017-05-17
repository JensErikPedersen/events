package org.serik.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ValidationExceptionEnvelope {

	private int code;

	private String field;

	private String message;

	public ValidationExceptionEnvelope() {
	}

	public ValidationExceptionEnvelope(int code, String field, String message) {
		this.code = code;
		this.field = field;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
