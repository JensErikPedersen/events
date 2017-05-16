package org.serik.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true)
public class MySqlSystemException extends RuntimeException {
    public MySqlSystemException(String msg, Exception cause) {
	super(msg, cause);
    }
}
