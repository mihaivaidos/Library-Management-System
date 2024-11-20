package LibraryModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an author in the library system, extending the Person class.
 * Includes the author's details and a list of books written by the author.
 */
public class Author extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Book> books;

    /**
     * Constructs a new Author instance with the specified parameters.
     *
     * @param ID          the unique ID for the author
     * @param name        the name of the author
     * @param email       the email address of the author
     * @param phoneNumber the phone number of the author
     */
    public Author(int ID, String name, String email, String phoneNumber) {
        super(ID, name, email, phoneNumber);
        this.books = new ArrayList<>();
    }

    /**
     * Gets the list of books written by the author.
     *
     * @return a list of books written by the author
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of books written by the author.
     *
     * @param books the new list of books for the author
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Returns a string representation of the author, including the list of books.
     *
     * @return a string with the author's book list
     */
    @Override
    public String toString() {
        return "Author{" +
                "name='" + name + '\'' +
                '}';
    }
}
