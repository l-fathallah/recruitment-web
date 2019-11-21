package fr.d2factory.libraryapp.library;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.book.service.BookRepository;
import fr.d2factory.libraryapp.book.service.DefaultBookRepository;
import fr.d2factory.libraryapp.exception.BookNotAvailableException;
import fr.d2factory.libraryapp.exception.BorrowedBookNotFoundExeption;
import fr.d2factory.libraryapp.exception.HasLateBooksException;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;

public class LibraryTest {
	private Library library;
	private BookRepository bookRepository;
	private long isbnCode = 46578964513L;
	private long isbnCode2 = 3326456467846L;
	private ISBN isbnObj;
	private ISBN otherISBN;
	private Member residentMember;
	private Member studentMember;
	private Member firstGradeStudentMember;
	private List<Member> membersList;
	private Book aBook;

	@Before
	public void setup() {
		bookRepository = new DefaultBookRepository(new HashMap<>(), new HashMap<>());
		isbnObj = new ISBN(isbnCode);
		otherISBN = new ISBN(isbnCode2);
		aBook = new Book("Harry Potter", "J.K. Rowling", isbnObj);
		residentMember = new Resident("johndoe@gmail.com", 0, new HashMap<>());
		studentMember = new Student("denzelwashington@gmail.com", 0, false, new HashMap<>());
		firstGradeStudentMember = new Student("willsmith@gmail.com", 0, true, new HashMap<>()  );
		membersList = new ArrayList<>();
		membersList.addAll(Arrays.asList(residentMember, studentMember, firstGradeStudentMember));
		library = new DefaultLibrabry(bookRepository, membersList);

		// Extract books from JSON
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			List<Book> books = objectMapper.readValue(new File(classLoader.getResource("books.json").getFile()),
					new TypeReference<List<Book>>() {
					});
			bookRepository.addBooks(books);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void member_can_borrow_a_book_if_book_is_available()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {
		assertEquals(library.borrowBook(isbnObj, residentMember, LocalDate.now()), aBook);
	}

	@Test
	public void borrowed_book_is_no_longer_available()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {
		library.borrowBook(isbnObj, residentMember, LocalDate.now());
		assertFalse(bookRepository.isBookAvailable(isbnObj));
	}

	@Test
	public void residents_are_taxed_10cents_for_each_day_they_keep_a_book()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {

		LocalDate yesterday = LocalDate.now().minus(Period.ofDays(1));

		Book borrowedBook = library.borrowBook(isbnObj, residentMember, yesterday);

		library.returnBook(borrowedBook, residentMember);

		// only one day to pay
		assertThat(residentMember.getWallet(), is(10f));
	}

	@Test
	public void students_pay_10_cents_the_first_30days()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {

		LocalDate yesterday = LocalDate.now().minus(Period.ofDays(1));

		Book borrowedBook = library.borrowBook(isbnObj, studentMember, yesterday);

		library.returnBook(borrowedBook, studentMember);

		// only one day to pay
		assertThat(studentMember.getWallet(), is(10f));
	}

	@Test
	public void students_in_1st_year_are_not_taxed_for_the_first_15days()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {

		LocalDate yesterday = LocalDate.now().minus(Period.ofDays(1));

		Book borrowedBook = library.borrowBook(isbnObj, firstGradeStudentMember, yesterday);

		library.returnBook(borrowedBook, firstGradeStudentMember);

		assertThat(firstGradeStudentMember.getWallet(), is(0f));
	}

	@Test
	public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {

		LocalDate borrowDate = LocalDate.now().minus(Period.ofDays(31));

		Book borrowedBook = library.borrowBook(isbnObj, studentMember, borrowDate);
		
		library.returnBook(borrowedBook, studentMember);

		// expected 10 * 30 + 15 ==> 315
		assertThat(studentMember.getWallet(), is(315f));
	}

	@Test
	public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {

		LocalDate borrowDate = LocalDate.now().minus(Period.ofDays(61));

		Book borrowedBook = library.borrowBook(isbnObj, residentMember, borrowDate);

		library.returnBook(borrowedBook, residentMember);

		// expected 10 * 60 + 20 ==> 620
		assertThat(residentMember.getWallet(), is(620f));
	}

	@Test
	public void students_in_1st_year__pay_10cents_for_the_second_15_days()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {

		LocalDate borrowDate = LocalDate.now().minus(Period.ofDays(16));

		Book borrowedBook = library.borrowBook(isbnObj, firstGradeStudentMember, borrowDate);

		library.returnBook(borrowedBook, firstGradeStudentMember);

		// only one day to pay
		assertThat(firstGradeStudentMember.getWallet(), is(10f));
	}

	@Test
	public void students_in_1st_year__pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {

		LocalDate borrowDate = LocalDate.now().minus(Period.ofDays(31));
		
		Book borrowedBook = library.borrowBook(isbnObj, firstGradeStudentMember, borrowDate);

		library.returnBook(borrowedBook, firstGradeStudentMember);

		// expected 15 * 10 + 15 ==> 165
		assertThat(firstGradeStudentMember.getWallet(), is(165f));
	}

	@Test(expected = HasLateBooksException.class)
	public void members_cannot_borrow_book_if_they_have_late_books()
			throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException {
		
		LocalDate borrowDate = LocalDate.now().minus(Period.ofDays(31));
		library.borrowBook(isbnObj, studentMember, borrowDate);
		library.borrowBook(otherISBN, studentMember, LocalDate.now());
	}

}
