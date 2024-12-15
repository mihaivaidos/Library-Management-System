package LibraryTests;
import Exceptions.DatabaseException;
import LibraryModel.*;
import LibraryRepository.IRepository;
import LibraryRepository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import LibraryService.LibraryService;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ApplicationTests {
    private LibraryService libraryService;
    private IRepository<Book> bookRepo;
    private IRepository<Member> memberRepo;
    private IRepository<Loan> loanRepo;
    private IRepository<Reservation> reservationRepo;
    private IRepository<Author> authorRepo;
    private IRepository<Publisher> publisherRepo;
    private IRepository<Category> categoryRepo;
    private IRepository<Review> reviewRepo;
    private IRepository<Staff> staffRepo;

    @BeforeEach
    public void setUp() {
        bookRepo = new InMemoryRepository<>();
        memberRepo = new InMemoryRepository<>();
        loanRepo = new InMemoryRepository<>();
        reservationRepo = new InMemoryRepository<>();
        authorRepo = new InMemoryRepository<>();
        publisherRepo = new InMemoryRepository<>();
        categoryRepo = new InMemoryRepository<>();
        reviewRepo = new InMemoryRepository<>();
        staffRepo = new InMemoryRepository<>();

        libraryService = new LibraryService(bookRepo, loanRepo, reservationRepo, categoryRepo, memberRepo, reviewRepo, authorRepo, publisherRepo, staffRepo);
    }

    @Test
    public void testAddUpdateDeleteBook() throws DatabaseException {
        Author author = new Author(15, "Ana Blandiana", "anabl@yahoo.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(6, "Poetry", "Poems");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(6, "Corint", "contact@corint.com", "1234565786");
        publisherRepo.add(publisher);

        libraryService.addBook("Cele 4 anotimpuri", author.getID(), category.getID(), publisher.getID(), 5);

        List<Book> books = bookRepo.getAll();
        assertEquals(1, books.size());
        assertEquals("Cele 4 anotimpuri", books.get(0).getBookName());

        Book addedBook = books.get(0);
        libraryService.updateBook(addedBook.getID(), "Updated Book Name", author.getID(), true, category.getID(), publisher.getID(), 10);

        Book updatedBook = bookRepo.get(addedBook.getID());
        assertEquals("Updated Book Name", updatedBook.getBookName());
        assertEquals(10, updatedBook.getCopiesAvailable());

        libraryService.deleteBook(updatedBook.getID());

        books = bookRepo.getAll();
        assertEquals(0, books.size());
    }

    @Test
    public void testAddReviewToBook_Valid() throws DatabaseException {
        Member member = new Member(1, "Member Name", "member@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 5);
        bookRepo.add(book);

        Loan loan = new Loan(1, new Date(), libraryService.calculateDueDate(), null, "ACTIVE", book, member);
        loanRepo.add(loan);
        member.getLoanHistory().add(loan);

        libraryService.addReviewToBook(member.getID(), book.getID(), 5, "Great book!");

        List<Review> reviews = book.getReviews();
        assertEquals(1, reviews.size());
        assertEquals("Great book!", reviews.get(0).getComments());
    }

    @Test
    public void testAddReviewToBook_InvalidBlankReviewText() throws DatabaseException {
        Member member = new Member(2, "Another Member", "another@example.com", "0987654321");
        memberRepo.add(member);
        Author author = new Author(2, "Another Author", "anotherauthor@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(2, "Non-Fiction", "Non-Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(2, "Another Publisher", "anotherpublisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(2, "Another Test Book", author, true, category, publisher, 5);
        bookRepo.add(book);

        Loan loan = new Loan(2, new Date(), libraryService.calculateDueDate(), null, "ACTIVE", book, member);
        loanRepo.add(loan);
        member.getLoanHistory().add(loan);

        libraryService.addReviewToBook(member.getID(), book.getID(), 4, ""); // Blank review text

        List<Review> reviews = book.getReviews();
        assertEquals(0, reviews.size());
    }

    @Test
    public void testGetMemberBorrowedBooks_WithLoans() throws DatabaseException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book1 = new Book(1, "Test Book 1", author, true, category, publisher, 5);
        Book book2 = new Book(2, "Test Book 2", author, true, category, publisher, 5);
        bookRepo.add(book1);
        bookRepo.add(book2);

        Loan loan1 = new Loan(1, new Date(), libraryService.calculateDueDate(), null, "ACTIVE", book1, member);
        Loan loan2 = new Loan(2, new Date(), libraryService.calculateDueDate(), null, "ACTIVE", book2, member);
        loanRepo.add(loan1);
        loanRepo.add(loan2);
        member.getLoanHistory().add(loan1);
        member.getLoanHistory().add(loan2);

        List<Book> borrowedBooks = libraryService.getMemberBorrowedBooks(member.getID());

        assertEquals(2, borrowedBooks.size());
        assertTrue(borrowedBooks.contains(book1));
        assertTrue(borrowedBooks.contains(book2));
    }

    @Test
    public void testGetMemberBorrowedBooks_WithoutLoans() throws DatabaseException {
        Member memberWithoutLoans = new Member(2, "Jane Doe", "jane@example.com", "0987654321");
        memberRepo.add(memberWithoutLoans);

        List<Book> borrowedBooksForNewMember = libraryService.getMemberBorrowedBooks(memberWithoutLoans.getID());

        assertEquals(0, borrowedBooksForNewMember.size());
    }

}
