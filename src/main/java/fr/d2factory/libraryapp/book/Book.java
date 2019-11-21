package fr.d2factory.libraryapp.book;

import java.util.Objects;

/**
 * A simple representation of a book
 */
public class Book {
	
    private String title;
    private String author;
    private ISBN isbn;
    
    public Book() {
	}

	public Book(String title, String author, ISBN isbn) {
		super();
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public ISBN getISBN() {
		return isbn;
	}

	public void setISBN(ISBN isbn) {
		this.isbn = isbn;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Book)) {
			return false;
		}
		Book other = (Book) obj;
		return Objects.equals(isbn, other.isbn);
	}
    
    
}
