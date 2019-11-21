package fr.d2factory.libraryapp.book.service;

import java.time.LocalDate;
import java.util.List;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.exception.BorrowedBookNotFoundExeption;
import fr.d2factory.libraryapp.member.Member;

public interface BookRepository {

	void addBooks(List<Book> books);

	/**
	 * finds a book in the whole repository, borrowed or available
	 * @param isbnObj the {@link ISBN} of the book to find
	 * @return the book if it was found, empty otherwise
	 */
	Book findBook(ISBN isbnObj) throws BorrowedBookNotFoundExeption;

	void saveBookBorrow(Member member, Book book, LocalDate borrowDate);

	void saveBookReturned(Book borrowedBook);

	LocalDate findBorrowedBookDate(Book book) throws BorrowedBookNotFoundExeption;

	/**
	 * Checks if {@link Book is available}
	 * @param isbn
	 * @return true if the book is available, false otherwise
	 */
	boolean isBookAvailable(ISBN isbn);

}