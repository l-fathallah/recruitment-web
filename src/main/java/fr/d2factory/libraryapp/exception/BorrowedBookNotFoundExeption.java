package fr.d2factory.libraryapp.exception;

import fr.d2factory.libraryapp.utils.Constants;

public class BorrowedBookNotFoundExeption extends Exception {

	private static final long serialVersionUID = 7869050475665418401L;

	@Override
	public String getMessage() {
		return Constants.BORROWED_BOOK_NOT_FOUND_EXCEPTION;
	}
	
	

}
