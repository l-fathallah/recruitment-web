package fr.d2factory.libraryapp.exception;

import fr.d2factory.libraryapp.utils.Constants;

/**
 * This exception is thrown when a member who owns late books tries to borrow another book
 */
public class HasLateBooksException extends Exception {

	private static final long serialVersionUID = 3066150474535414066L;

	public HasLateBooksException(String message) {
		super(message);
	}

	public HasLateBooksException() {
		super(Constants.HAS_LATE_BOOKS_EXCEPTION);
	}
	
	@Override
	public String getMessage() {
		return Constants.HAS_LATE_BOOKS_EXCEPTION_MESSAGE;
	}
}
