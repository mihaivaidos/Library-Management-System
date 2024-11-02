package LibraryModel;

import java.util.List;

public class Publisher extends Person {

    private List<Book> publishedBooks;

    public Publisher(int ID, String name, String email, String phoneNumber, List<Book> publishedBooks) {
        super(ID, name, email, phoneNumber);
        this.publishedBooks = publishedBooks;
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
