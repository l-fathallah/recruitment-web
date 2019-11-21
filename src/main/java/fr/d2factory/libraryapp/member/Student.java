package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.utils.Constants;

public class Student extends Member {
	
	private boolean isFirstGradeStudent;

	public Student (String email, float wallet, boolean isFirstGradeStudent, Map<Book, LocalDate> borrowedBooks) {
		super(email, wallet, borrowedBooks);
		setIsFirstGradeStudent(isFirstGradeStudent);
	}
	
	@Override
	public boolean hasLateBooks() {
		return getBorrowedBooks().entrySet().stream()
				.filter(e -> getNumberOfBorrowDays(e.getKey()) > Constants.STUDENT_DELAY)
				.findAny()
				.isPresent();
	}

	@Override
	public void payBook(Book book) {
		int numberOfDays = getNumberOfBorrowDays(book);
		
		if (isFirstGradeStudent) {
			
			firstGradeStudentPayBook(numberOfDays);
			
		} else {
	    	if (numberOfDays > Constants.STUDENT_DELAY) {
	    		setWallet( (Constants.STUDENT_DELAY * Constants.TARRIF) 
	    				+ ((numberOfDays - Constants.STUDENT_DELAY) * Constants.STUDENT_INCREASED_TARIFF));
	    	} else {
	    		setWallet(numberOfDays * Constants.TARRIF);
	    	}
		}
	}

	private void firstGradeStudentPayBook(int numberOfDays) {
		if (numberOfDays > Constants.STUDENT_GRACE_DELAY) {
			if (numberOfDays > Constants.STUDENT_DELAY) {
				setWallet((Constants.STUDENT_DELAY - Constants.STUDENT_GRACE_DELAY) * Constants.TARRIF 
							+ 
						  (numberOfDays - Constants.STUDENT_DELAY) * Constants.STUDENT_INCREASED_TARIFF);
			} else {
				setWallet((numberOfDays - Constants.STUDENT_GRACE_DELAY) * Constants.TARRIF);
			}
		}
	}
	
	public boolean isFirstGradeStudent() {
		return isFirstGradeStudent;
	}

	public void setIsFirstGradeStudent(boolean isFirstGradeStudent) {
		this.isFirstGradeStudent = isFirstGradeStudent;
	}

}
