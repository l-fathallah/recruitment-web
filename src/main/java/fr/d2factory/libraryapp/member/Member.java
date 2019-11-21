package fr.d2factory.libraryapp.member;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Objects;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.utils.Constants;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member  {

	private String email;
    private float wallet;
    private Map<Book, LocalDate> borrowedBooks;
    
    public Member(String email, float wallet, Map<Book, LocalDate> borrowedBooks) {
    	this.wallet = wallet;
		this.email = email;
		this.borrowedBooks = borrowedBooks;
	}

	public abstract void payBook(Book book);
    
	public void addBorrowedBook(Book book, LocalDate borrowDate) {
		getBorrowedBooks().put(book, borrowDate);
		
	}

	public abstract boolean hasLateBooks();
	
	protected int getNumberOfBorrowDays(Book book) {
		LocalDate borrowDate = getBorrowedBooks().get(book);
		return (int) ChronoUnit.DAYS.between(borrowDate, LocalDate.now());
	}

	public float getWallet() {
        return wallet;
    }

    public void setWallet(float wallet) {
        this.wallet = wallet;
    }

	public Map<Book, LocalDate> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Member)) {
			return false;
		}
		Member other = (Member) obj;
		return Objects.equals(email, other.email);
	}

}
