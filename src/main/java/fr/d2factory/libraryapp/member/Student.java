package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.utils.Constants;

public class Student extends AbstractMember {
	
	private boolean isFirstGradeStudent;

	public Student (String firstName, String lastname, float wallet, boolean isFirstGradeStudent, Map<Book, LocalDate> borrowedBooks) {
		super(firstName, lastname, wallet, borrowedBooks);
		setInitialIncreasedTariff(Constants.STUDENT_INCREASED_TARIFF);
		setInitialDelay(Constants.STUDENT_DELAY);
		setIsFirstGradeStudent(isFirstGradeStudent);
	}

	public boolean isFirstGradeStudent() {
		return isFirstGradeStudent;
	}

	public void setIsFirstGradeStudent(boolean isFirstGradeStudent) {
		this.isFirstGradeStudent = isFirstGradeStudent;
	}

	@Override
	public void payBook(Book book) {
		if (isFirstGradeStudent) {
			firstGradeStudentPayBook(book);
		} else {
			super.payBook(book);
		}
		
	}

	private void firstGradeStudentPayBook(Book book) {
		int numberOfDays = getNumberOfBorrowDays(book);
		if (numberOfDays > Constants.STUDENT_GRACE_DELAY) {
			if (numberOfDays > getInitialDelay()) {
				setWallet((getInitialDelay() - Constants.STUDENT_GRACE_DELAY) * getTariff() 
							+ 
						  (numberOfDays - getInitialDelay()) * getInitialIncreasedTariff());
			} else {
				setWallet((numberOfDays - Constants.STUDENT_GRACE_DELAY) * getTariff());
			}
		}
	}
}
