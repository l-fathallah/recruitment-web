package fr.d2factory.libraryapp.book.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.exception.BorrowedBookNotFoundExeption;
import fr.d2factory.libraryapp.member.Member;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class DefaultBookRepository implements BookRepository {
    private Map<ISBN, Book> availableBooks;
    private Map<Book, LocalDate> borrowedBooks;

	public DefaultBookRepository(Map<ISBN, Book> availableBooks, Map<Book, LocalDate> borrowedBooks) {
		super();
		this.availableBooks = availableBooks;
		this.borrowedBooks = borrowedBooks;
	}

	@Override
	public void addBooks(List<Book> books){
    	for (Book book : books) {
    		getAvailableBooks().put(book.getISBN(), book);
    	}
    }

	@Override
	public Book findBook(ISBN isbnObj) 
			throws BorrowedBookNotFoundExeption {
		
		if (isBookAvailable(isbnObj)) {
			return getAvailableBooks().get(isbnObj);
		} else {
			return findBorrowedBook(isbnObj);
		}
    }

	@Override
	public void saveBookBorrow(Member member, Book book, LocalDate borrowDate){
    	getBorrowedBooks().put(book, borrowDate);
    	getAvailableBooks().remove(book.getISBN());
    }
    
	@Override
	public void saveBookReturned(Book borrowedBook) {
		borrowedBooks.remove(borrowedBook);
    	availableBooks.put(borrowedBook.getISBN(), borrowedBook);
	}

	@Override
	public LocalDate findBorrowedBookDate(Book book) 
			throws BorrowedBookNotFoundExeption{
		
		Optional<LocalDate> localDate = getBorrowedBooks()
										.entrySet()
										.stream()
										.filter(e -> e.getKey().equals(book))
										.map(Map.Entry::getValue)
										.findFirst();
		if(localDate.isPresent()) {
			return localDate.get();
		}

		throw new BorrowedBookNotFoundExeption();
    }
	
	private Book findBorrowedBook(ISBN isbnObj) 
			throws BorrowedBookNotFoundExeption{
		
		Optional<Book> borrowedBook = getBorrowedBooks()
												.entrySet()
												.stream()
												.filter(e -> e.getKey().getISBN().equals(isbnObj))
												.map(Map.Entry::getKey)
												.findFirst();
		if(borrowedBook.isPresent()) {
			return borrowedBook.get();
		}
		
		throw new BorrowedBookNotFoundExeption();
	}

	@Override
	public boolean isBookAvailable(ISBN isbn) {
		return getAvailableBooks().containsKey(isbn);
	}
	
	/**
	 * Getters and Setters
	 * @return
	 */
	public Map<ISBN, Book> getAvailableBooks() {
		return availableBooks;
	}

	public void setAvailableBooks(Map<ISBN, Book> availableBooks) {
		this.availableBooks = availableBooks;
	}
	
	public void setBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	public Map<Book, LocalDate> getBorrowedBooks() {
		return borrowedBooks;
	}

}