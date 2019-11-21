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
public abstract class AbstractMember implements Member {

	private String firstName;
	private String lastname;
    private float wallet;
    private int initialDelay;
    private float initialTariff;
    private float initialIncreasedTariff;
    private Map<Book, LocalDate> borrowedBooks;
    
    public AbstractMember(String firstName, String lastname, float wallet, Map<Book, LocalDate> borrowedBooks) {
    	this.wallet = wallet;
		this.firstName = firstName;
		this.lastname = lastname;
		this.initialTariff = Constants.TARRIF;
		this.borrowedBooks = borrowedBooks;
	}

	/**
	 * @see fr.d2factory.libraryapp.member.Member#payBook(int)
	 */
    @Override
	public void payBook(Book book) {
    	
    	int numberOfDays = getNumberOfBorrowDays(book);
    	
    	if (numberOfDays > initialDelay) {
    		setWallet( (initialDelay * initialTariff) + ((numberOfDays - initialDelay) * initialIncreasedTariff));
    	} else {
    		setWallet(numberOfDays * initialTariff);
    	}
    }
    
    @Override
	public void addBorrowedBook(Book book, LocalDate borrowDate) {
		getBorrowedBooks().put(book, borrowDate);
		
	}

	@Override
	public boolean hasLateBooks() {
		return borrowedBooks.entrySet().stream()
								.filter(e -> getNumberOfBorrowDays(e.getKey()) > getInitialDelay())
								.findAny()
								.isPresent();
	}
	
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

	public int getInitialDelay() {
		return initialDelay;
	}

	public void setInitialDelay(int delay) {
		this.initialDelay = delay;
	}

	public float getInitialIncreasedTariff() {
		return initialIncreasedTariff;
	}

	public void setInitialIncreasedTariff(float increasedTariff) {
		this.initialIncreasedTariff = increasedTariff;
	}
	
	public float getTariff() {
		return initialTariff;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public float getInitialTariff() {
		return initialTariff;
	}

	public void setInitialTariff(float initialTariff) {
		this.initialTariff = initialTariff;
	}

	public Map<Book, LocalDate> getBorrowedBooks() {
		return borrowedBooks;
	}

	public void setBorrowedBooks(Map<Book, LocalDate> borrowedBooks) {
		this.borrowedBooks = borrowedBooks;
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof AbstractMember)) {
			return false;
		}
		AbstractMember other = (AbstractMember) obj;
		return Objects.equals(firstName, other.firstName) && Objects.equals(lastname, other.lastname);
	}

}
