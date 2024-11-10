package LibraryModel;

import java.util.ArrayList;
import java.util.List;

public class Book implements HasID{

    private int bookID;
    private String bookName;
    private Author author;
    private boolean isAvailable;
    //private int copiesAvailable;
    private Category category;
    private Publisher publisher;
    private List<Review> reviews;

    public Book(int bookID, String bookName, Author author, boolean isAvailable, Category category, Publisher publisher) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.isAvailable = isAvailable;
        //this.copiesAvailable = copiesAvailable;
        this.category = category;
        this.publisher = publisher;
        this.reviews = new ArrayList<>();
    }

    @Override
    public int getBookIDID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

//    public int getCopiesAvailable() {
//        return copiesAvailable;
//    }
//
//    public void setCopiesAvailable(int copiesAvailable) {
//        this.copiesAvailable = copiesAvailable;
//    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", author=" + author +
                ", isAvailable=" + isAvailable +
                ", category=" + category +
                ", publisher=" + publisher +
                '}';
    }
}
