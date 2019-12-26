package fr.d2factory.libraryapp.library;

import java.time.LocalDate;
import java.util.List;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.book.service.IBookRepository;
import fr.d2factory.libraryapp.exception.BookNotAvailableException;
import fr.d2factory.libraryapp.exception.BorrowedBookNotFoundExeption;
import fr.d2factory.libraryapp.exception.HasLateBooksException;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.utils.Constants;

public class DefaultLibrabry implements Library {

	private IBookRepository bookRepository;
	private List<Member> membersList;

	public DefaultLibrabry(IBookRepository bookRepository, List<Member> membersList) {
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
			throw new HasLateBooksException(Constants.HAS_LATE_BOOKS_EXCEPTION);
		}
		throw new BookNotAvailableException(Constants.BORROWED_BOOK_NOT_AVAILABLE_EXCEPTION);
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
	public IBookRepository getBookRepository() {
		return bookRepository;
	}

	public void setBookRepository(IBookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<Member> getMembersList() {
		return membersList;
	}

	public void setMembersList(List<Member> membersList) {
		this.membersList = membersList;
	}

}
