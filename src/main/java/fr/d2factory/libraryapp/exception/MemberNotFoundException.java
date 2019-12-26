package fr.d2factory.libraryapp.exception;

import fr.d2factory.libraryapp.utils.Constants;

public class MemberNotFoundException extends Exception {

	private static final long serialVersionUID = 8861978745957099534L;

	public MemberNotFoundException(String message) {
		super(message);
	}
	
	public MemberNotFoundException() {
		super(Constants.MEMBER_NOT_FOUND_EXCEPTION);
	}
	
	@Override
	public String getMessage() {
		return Constants.MEMBER_NOT_FOUND_EXCEPTION_MESSAGE;
	}

}
