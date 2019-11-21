package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.book.service.BookRepository;
import fr.d2factory.libraryapp.exception.BookNotAvailableException;
import fr.d2factory.libraryapp.exception.BorrowedBookNotFoundExeption;
import fr.d2factory.libraryapp.exception.HasLateBooksException;
import fr.d2factory.libraryapp.member.AbstractMember;
import fr.d2factory.libraryapp.member.Member;

public class DefaultLibrabry implements Library {

	private BookRepository bookRepository;
	private List<AbstractMember> membersList;

	public DefaultLibrabry(BookRepository bookRepository, List<AbstractMember> membersList) {
		super();
		this.bookRepository = bookRepository;
		this.membersList = membersList;
	}

	@Override
	public Book borrowBook(ISBN isbn, Member member, LocalDate borrowDate)
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {

		if (getBookRepository().isBookAvailable(isbn)) {
			if (!member.hasLateBooks()) {
				return performBorrow(isbn, member, borrowDate);
			}
			throw new HasLateBooksException();
		}
		throw new BookNotAvailableException();
	}

	private Book performBorrow(ISBN isbn, Member member, LocalDate borrowDate) 
			throws BorrowedBookNotFoundExeption {

		Book bookToBorrow = getBookRepository().findBook(isbn);
		getBookRepository().saveBookBorrow(member, bookToBorrow, borrowDate);
		member.addBorrowedBook(bookToBorrow, borrowDate);

		return bookToBorrow;
	}

	@Override
	public void returnBook(Book borrowedBook, Member member) {
		
		// Charge the member
		member.payBook(borrowedBook);

		// Update the bookRepository
		getBookRepository().saveBookReturned(borrowedBook);
	}

	/**
	 * Getters and Setters
	 * 
	 */
	public BookRepository getBookRepository() {
		return bookRepository;
	}

	public void setBookRepository(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<AbstractMember> getMembersList() {
		return membersList;
	}

	public void setMembersList(List<AbstractMember> membersList) {
		this.membersList = membersList;
	}

}
