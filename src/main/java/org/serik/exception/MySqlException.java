package org.serik.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true) // default so basicly not necesary
public class MySqlException extends Exception {
	private static final long serialVersionUID = -4431145651052718343L;

	public MySqlException(String msg, Exception cause) {
		super(msg, cause);
	}
}
