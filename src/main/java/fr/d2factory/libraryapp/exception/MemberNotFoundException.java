package fr.d2factory.libraryapp.exception;

import fr.d2factory.libraryapp.utils.Constants;

public class MemberNotFoundException extends Exception {

	private static final long serialVersionUID = 8861978745957099534L;

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return Constants.MEMBER_NOT_FOUND_EXCEPTION;
	}
	
	

}
