package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import java.util.Map;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.utils.Constants;

public class Resident extends AbstractMember {

	public Resident(String firstName, String lastname, float wallet, Map<Book, LocalDate> borrowedBooks) {
		super(firstName, lastname, wallet, borrowedBooks);
		setInitialIncreasedTariff(Constants.RESIDENT_INCREASED_TARIFF);
		setInitialDelay(Constants.RESIDENT_DELAY);
	}
	
}
