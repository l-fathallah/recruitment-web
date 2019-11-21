package fr.d2factory.libraryapp.book;

import java.util.Objects;

public class ISBN {
    private long isbnCode;

    public ISBN() {
	}

	public ISBN(long isbnCode) {
        this.isbnCode = isbnCode;
    }

	public long getIsbnCode() {
		return isbnCode;
	}

	public void setIsbnCode(long isbnCode) {
		this.isbnCode = isbnCode;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(isbnCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof ISBN)) {
			return false;
		}
		ISBN other = (ISBN) obj;
		return isbnCode == other.isbnCode;
	}
	
	
	
}
