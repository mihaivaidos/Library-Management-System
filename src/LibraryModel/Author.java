package LibraryModel;

import java.util.List;

public class Author extends Person {

    private List<Book> books;

    public Author(int ID, String name, String email, String phoneNumber, List<Book> books) {
        super(ID, name, email, phoneNumber);
        this.books = books;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "books=" + books +
                '}';
    }

}
