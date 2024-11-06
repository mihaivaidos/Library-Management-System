package LibraryModel;

import java.util.ArrayList;
import java.util.List;

public class Author extends Person {

    private final List<Book> books;

    public Author(int ID, String name, String email, String phoneNumber) {
        super(ID, name, email, phoneNumber);
        this.books = new ArrayList<>();
    }

    public List<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public String toString() {
        return "Author{" +
                "books=" + books +
                '}';
    }

}
