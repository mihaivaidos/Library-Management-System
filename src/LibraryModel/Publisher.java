package LibraryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a publisher in the library system, with details about the books published.
 * Extends the Person class.
 */
public class Publisher extends Person {

    private List<Book> publishedBooks;

    /**
     * Constructs a new Publisher instance with the specified parameters.
     *
     * @param ID          the unique ID for the publisher
     * @param name        the name of the publisher
     * @param email       the email address of the publisher
     * @param phoneNumber the phone number of the publisher
     */
    public Publisher(int ID, String name, String email, String phoneNumber) {
        super(ID, name, email, phoneNumber);
        this.publishedBooks = new ArrayList<>();
    }

    /**
     * Gets the list of books published by this publisher.
     *
     * @return a list of published books
     */
    public List<Book> getPublishedBooks() {
        return publishedBooks;
    }

    /**
     * Sets the list of books published by this publisher.
     *
     * @param publishedBooks the new list of published books
     */
    public void setPublishedBooks(List<Book> publishedBooks) {
        this.publishedBooks = publishedBooks;
    }

    /**
     * Returns a string representation of the publisher, including the list of published books.
     *
     * @return a string with the publisher's details and published books
     */
    @Override
    public String toString() {
        return "Publisher{" +
                "publishedBooks=" + publishedBooks +
                '}';
    }
}
