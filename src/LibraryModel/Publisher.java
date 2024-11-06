package LibraryModel;

import java.util.ArrayList;
import java.util.List;

public class Publisher extends Person {

    private List<Book> publishedBooks;

    public Publisher(int ID, String name, String email, String phoneNumber) {
        super(ID, name, email, phoneNumber);
        this.publishedBooks = new ArrayList<>();
    }

    public List<Book> getPublishedBooks() {
        return publishedBooks;
    }

    public void setPublishedBooks(List<Book> publishedBooks) {
        this.publishedBooks = publishedBooks;
    }

    @Override
    public String toString() {
        return "Publisher{" +
                "publishedBooks=" + publishedBooks +
                '}';
    }
}
