package fr.d2factory.libraryapp.exception;

import fr.d2factory.libraryapp.utils.Constants;

public class BookNotAvailableException extends Exception {

	private static final long serialVersionUID = -1896292315748222469L;

	public BookNotAvailableException(String message) {
		super(message);
	}
	
	public BookNotAvailableException() {
		super(Constants.BORROWED_BOOK_NOT_AVAILABLE_EXCEPTION);
	}
	
	@Override
	public String getMessage() {
		return Constants.BORROWED_BOOK_NOT_AVAILABLE_EXCEPTION_MESSAGE;
	}

}
