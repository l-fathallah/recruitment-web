package fr.d2factory.libraryapp.member;

import java.time.LocalDate;

import fr.d2factory.libraryapp.book.Book;

public interface Member {

	/**
	 * The member should pay their books when they are returned to the library
	 *
	 * @param numberOfDays the number of days they kept the book
	 */
	void payBook(Book book);
	
	boolean hasLateBooks();

	void addBorrowedBook(Book book, LocalDate borrowDate);

}