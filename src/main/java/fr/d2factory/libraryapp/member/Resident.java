package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.utils.Constants;

public class Resident extends Member {

	public Resident(String email, float wallet, Map<Book, LocalDate> borrowedBooks) {
		super(email, wallet, borrowedBooks);
	}

	@Override
	public void payBook(Book book) {
		int numberOfDays = getNumberOfBorrowDays(book);
    	
    	if (numberOfDays > Constants.RESIDENT_DELAY) {
    		setWallet( (Constants.RESIDENT_DELAY * Constants.TARRIF) + ((numberOfDays - Constants.RESIDENT_DELAY) * Constants.RESIDENT_INCREASED_TARIFF));
    	} else {
    		setWallet(numberOfDays * Constants.TARRIF);
    	}
		
	}

	@Override
	public boolean hasLateBooks() {
		return getBorrowedBooks().entrySet().stream()
				.filter(e -> getNumberOfBorrowDays(e.getKey()) > Constants.RESIDENT_DELAY)
				.findAny()
				.isPresent();
	}
	
}
