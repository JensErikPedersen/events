package org.serik.exception;

import javax.ejb.ApplicationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationException(rollback = true) // Effectively, force
				       // PersistenceExceptions
				       // (RuntimeException) and subclasses to
				       // trigger a transaction rollback and
				       // make EJB container to treat them as a
				       // checked exception not wrapping them
				       // into an EJBException
public class SqlException extends RuntimeException {
    private static final long serialVersionUID = -4431145651052718343L;

    private static final Logger logger = LoggerFactory.getLogger(SqlException.class);

    public SqlException(String msg, Exception cause) {
	super(msg, findRootCause(cause));
    }

    private static Throwable findRootCause(Throwable c) {
	if (c != null) {
	    while (c.getCause() != null) {
		c = c.getCause();
	    }
	}

	return c;
    }
}
