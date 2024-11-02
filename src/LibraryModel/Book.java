package LibraryModel;

public class Book {

    private int bookID;
    private String bookName;
    private Author author;
    private boolean isAvailable;
    private int copiesAvailable;
    private Category category;
    private Publisher publisher;

    public Book(int bookID, String bookName, Author author, boolean isAvailable, int copiesAvailable, Category category, Publisher publisher) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.isAvailable = isAvailable;
        this.copiesAvailable = copiesAvailable;
        this.category = category;
        this.publisher = publisher;
    }

    public int getBookID() {
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

    public int getCopiesAvailable() {
        return copiesAvailable;
    }

    public void setCopiesAvailable(int copiesAvailable) {
        this.copiesAvailable = copiesAvailable;
    }

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

    @Override
    public String toString() {
        return "Book{" +
                "bookID=" + bookID +
                ", bookName='" + bookName + '\'' +
                ", author=" + author +
                ", isAvailable=" + isAvailable +
                ", copiesAvailable=" + copiesAvailable +
                ", category=" + category +
                ", publisher=" + publisher +
                '}';
    }
}
