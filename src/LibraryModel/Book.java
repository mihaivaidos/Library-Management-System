package LibraryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a book in a library with attributes such as ID, name, author, availability status,
 * category, publisher, and reviews.
 */
public class Book implements HasID {

    private int bookID;
    private String bookName;
    private Author author;
    private boolean isAvailable;
    private Category category;
    private Publisher publisher;
    private List<Review> reviews;

    /**
     * Constructs a new Book instance with the specified parameters.
     *
     * @param bookID      the unique identifier for the book
     * @param bookName    the name of the book
     * @param author      the author of the book
     * @param isAvailable the availability status of the book
     * @param category    the category of the book
     * @param publisher   the publisher of the book
     */
    public Book(int bookID, String bookName, Author author, boolean isAvailable, Category category, Publisher publisher) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.isAvailable = isAvailable;
        this.category = category;
        this.publisher = publisher;
        this.reviews = new ArrayList<>();
    }

    /**
     * Gets the unique ID of the book.
     *
     * @return the book ID
     */
    @Override
    public int getID() {
        return bookID;
    }

    /**
     * Sets the unique ID for the book.
     *
     * @param bookID the new ID for the book
     */
    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    /**
     * Gets the name of the book.
     *
     * @return the book name
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * Sets the name of the book.
     *
     * @param bookName the new name for the book
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * Gets the author of the book.
     *
     * @return the author of the book
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the author of the book.
     *
     * @param author the new author for the book
     */
    public void setAuthor(Author author) {
        this.author = author;
    }

    /**
     * Checks if the book is currently available.
     *
     * @return true if the book is available, false otherwise
     */
    public boolean isAvailable() {
        return isAvailable;
    }

    /**
     * Sets the availability status of the book.
     *
     * @param available the new availability status of the book
     */
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    /**
     * Gets the category of the book.
     *
     * @return the category of the book
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Sets the category of the book.
     *
     * @param category the new category for the book
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Gets the publisher of the book.
     *
     * @return the publisher of the book
     */
    public Publisher getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher of the book.
     *
     * @param publisher the new publisher for the book
     */
    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    /**
     * Gets the list of reviews for the book.
     *
     * @return a list of reviews for the book
     */
    public List<Review> getReviews() {
        return reviews;
    }

    /**
     * Sets the list of reviews for the book.
     *
     * @param reviews the new list of reviews for the book
     */
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    /**
     * Returns a string representation of the book.
     *
     * @return a string with the book's ID, name, author, availability, category, publisher and reviews
     */
    @Override

    public String toString() {
        return "Book ID: " + this.bookID + ", Title: " + this.bookName; // Avoid referencing Category directly
    }
//    public String toString() {
//        return "Book{" +
//                "bookID=" + bookID +
//                ", bookName='" + bookName + '\'' +
//                ", authorName='" + author.getName() + '\'' + // Use author's name instead of the whole object
//                ", isAvailable=" + isAvailable +
//                ", category=" + category +
//                ", publisher=" + publisher +
//                '}';
//    }

}
