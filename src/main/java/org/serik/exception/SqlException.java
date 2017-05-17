package org.serik.exception;

import javax.ejb.ApplicationException;

@ApplicationException(rollback = true) // Effectively, force
				       // PersistenceExceptions
				       // (RuntimeException) and subclasses to
				       // trigger a transaction rollback and
				       // make EJB container to treat them as a
				       // checked exception not wrapping them
				       // into an EJBException
public class SqlException extends RuntimeException {
    private static final long serialVersionUID = -4431145651052718343L;

    public SqlException(String msg, Exception cause) {
	super(msg, cause);
    }
}
