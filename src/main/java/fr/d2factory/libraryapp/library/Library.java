package fr.d2factory.libraryapp.library;

import java.time.LocalDate;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.ISBN;
import fr.d2factory.libraryapp.exception.BookNotAvailableException;
import fr.d2factory.libraryapp.exception.BorrowedBookNotFoundExeption;
import fr.d2factory.libraryapp.exception.HasLateBooksException;
import fr.d2factory.libraryapp.member.AbstractMember;
import fr.d2factory.libraryapp.member.Member;

/**
 * The library class is in charge of stocking the books and managing the return delays and members
 *
 * The books are available via the {@link fr.d2factory.libraryapp.book.service.DefaultBookRepository}
 */
public interface Library {

    /**
     * A member is borrowing a book from our library.
     *
     * @param isbn the isbn of the book
     * @param member the member who is borrowing the book
     * @param borrowDate the date the book was borrowed 
     *
     * @return the book the member wishes to obtain if found
     * @throws HasLateBooksException in case the member has books that are late
     * @throws BorrowedBookNotFoundExeption
     * @throws BookNotAvailableException 
     *
     * @see fr.d2factory.libraryapp.book.ISBN
     * @see AbstractMember
     */
    Book borrowBook(ISBN isbn, Member member, LocalDate borrowDate) throws HasLateBooksException, BorrowedBookNotFoundExeption, BookNotAvailableException;

    /**
     * A member returns a book to the library.
     * We should calculate the tariff and probably charge the member
     *
     * @param borrowedBook the {@link Book} they return
     *
     */
    void returnBook(Book borrowedBook, Member member);

}
