package LibraryTests;
import Exceptions.BusinessLogicException;
import Exceptions.DatabaseException;
import Exceptions.EntityNotFoundException;
import LibraryModel.*;
import LibraryRepository.IRepository;
import LibraryRepository.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import LibraryService.LibraryService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class ApplicationTests {
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
    public void testAddUpdateDeleteBook() throws DatabaseException, EntityNotFoundException {
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
    public void testAddReviewToBook_Success() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
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

        Loan loan = new Loan(1, LocalDate.now(), libraryService.calculateDueDate(), null, "ACTIVE", book, member);
        loanRepo.add(loan);
        member.getLoanHistory().add(loan);

        libraryService.addReviewToBook(member.getID(), book.getID(), 5, "Great book!");

        List<Review> reviews = book.getReviews();
        assertEquals(1, reviews.size());
        assertEquals("Great book!", reviews.get(0).getComments());
    }

    @Test
    public void testAddReviewToBook_MemberNotBorrowed() throws DatabaseException, EntityNotFoundException {
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

        assertThrows(BusinessLogicException.class, () -> {
            libraryService.addReviewToBook(member.getID(), book.getID(), 5, "Great book!");
        });
    }

    @Test
    public void testBorrowBook_Success() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 5);
        bookRepo.add(book);

        libraryService.borrowBook(member.getID(), book.getID());

        List<Loan> loans = loanRepo.getAll();
        assertEquals(1, loans.size());
        assertEquals(book, loans.get(0).getBook());
        assertEquals(member, loans.get(0).getMember());
    }

    @Test
    public void testBorrowBook_ThrowsEntityNotFoundException() throws DatabaseException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);

        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.borrowBook(member.getID(), 999);
        });
    }

    @Test
    public void testReturnBook_Success() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 5);
        bookRepo.add(book);

        libraryService.borrowBook(member.getID(), book.getID());
        List<Loan> loans = loanRepo.getAll();
        assertEquals(1, loans.size());
        assertEquals(4, book.getCopiesAvailable());

        libraryService.returnBook(loans.get(0).getID());

        assertEquals("RETURNED",loanRepo.getAll().getFirst().getStatus());
        assertEquals(5, book.getCopiesAvailable());
    }

    @Test
    public void testReturnBook_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.returnBook(999);
        });
    }

    @Test
    public void testGetMemberBorrowedBooks_Success() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 5);
        bookRepo.add(book);

        libraryService.borrowBook(member.getID(), book.getID());

        List<Book> borrowedBooks = libraryService.getMemberBorrowedBooks(member.getID());
        assertEquals(1, borrowedBooks.size());
        assertEquals(book, borrowedBooks.get(0));
    }

    @Test
    public void testGetMemberBorrowedBooks_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.getMemberBorrowedBooks(999);
        });
    }

    @Test
    public void testDeleteReviewFromBook_Success() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
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

        libraryService.borrowBook(member.getID(), book.getID());

        libraryService.addReviewToBook(member.getID(), book.getID(), 5, "Great book!");

        List<Review> reviews = book.getReviews();
        assertEquals(1, reviews.size(), "There should be one review after adding.");

        libraryService.deleteReviewFromBook(reviews.get(0).getID());

        assertEquals(0, book.getReviews().size(), "There should be no reviews after deletion.");
    }

    @Test
    public void testDeleteReviewFromBook_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.deleteReviewFromBook(999);
        });
    }

    @Test
    public void testGetAllReviewsOfBook_Success() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
        Member member1 = new Member(1, "Member One", "member1@example.com", "1234567890");
        memberRepo.add(member1);
        Member member2 = new Member(2, "Member Two", "member2@example.com", "0987654321");
        memberRepo.add(member2);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 5);
        bookRepo.add(book);

        libraryService.borrowBook(member1.getID(), book.getID());
        libraryService.borrowBook(member2.getID(), book.getID());

        libraryService.addReviewToBook(member1.getID(), book.getID(), 5, "Great book!");
        libraryService.addReviewToBook(member2.getID(), book.getID(), 4, "Good read!");

        List<Review> reviews = libraryService.getAllReviewsOfBook(book.getID());

        assertEquals(2, reviews.size(), "There should be two reviews for the book.");

        assertEquals("Great book!", reviews.get(0).getComments());
        assertEquals("Good read!", reviews.get(1).getComments());
    }

    @Test
    public void testGetAllReviewsOfBook_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.getAllReviewsOfBook(999); // Non-existent book ID
        });
    }

    @Test
    public void testCheckMemberHasOverdueLoans_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 5);
        bookRepo.add(book);

        Loan loan = new Loan(1, LocalDate.now().minusDays(1), LocalDate.now().minusDays(1), null, "ACTIVE", book, member);
        loanRepo.add(loan);
        member.getLoans().add(loan);

        assertTrue(libraryService.checkMemberHasOverdueLoans(member.getID()));
    }

    @Test
    public void testCheckMemberHasOverdueLoans_NoOverdueLoans() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 5);
        bookRepo.add(book);

        Loan loan = new Loan(1, LocalDate.now(), libraryService.calculateDueDate(), null, "ACTIVE", book, member);
        loanRepo.add(loan);
        member.getLoans().add(loan);

        assertFalse(libraryService.checkMemberHasOverdueLoans(member.getID()));
    }

    @Test
    public void testRecommendBooksForMember_Success() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book1 = new Book(1, "Fiction Book 1", author, true, category, publisher, 5);
        Book book2 = new Book(2, "Fiction Book 2", author, true, category, publisher, 5);
        Book book3 = new Book(3, "Non-Fiction Book", author, true, category, publisher, 5);
        bookRepo.add(book1);
        bookRepo.add(book2);
        bookRepo.add(book3);

        libraryService.borrowBook(member.getID(), book1.getID());
        libraryService.borrowBook(member.getID(), book2.getID());

        List<Book> recommendedBooks = libraryService.recommendBooksForMember(member.getID());

        assertEquals(1, recommendedBooks.size());
        assertEquals(book3, recommendedBooks.get(0)); // Non-Fiction Book should be recommended
    }

    @Test
    public void testRecommendBooksForMember_NoRecommendations() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book1 = new Book(1, "Fiction Book 1", author, true, category, publisher, 5);
        bookRepo.add(book1);

        List<Book> recommendedBooks = libraryService.recommendBooksForMember(member.getID());
        assertTrue(recommendedBooks.isEmpty());
    }

    @Test
    public void testRecommendBooksForMember_ThrowsEntityNotFoundException() {
        int nonExistentMemberID = 999;

        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.recommendBooksForMember(nonExistentMemberID);
        });
    }

    @Test
    public void testSortBooksByAvgRating_Success() throws DatabaseException, EntityNotFoundException, BusinessLogicException {
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book1 = new Book(1, "Book 1", author, true, category, publisher, 5);
        Book book2 = new Book(2, "Book 2", author, true, category, publisher, 5);
        bookRepo.add(book1);
        bookRepo.add(book2);
        Member member = new Member(1, "Member Name", "member@example.com", "1234567890");
        memberRepo.add(member);

        libraryService.borrowBook(member.getID(), book1.getID());
        libraryService.borrowBook(member.getID(), book2.getID());

        libraryService.addReviewToBook(member.getID(), book1.getID(), 5, "Excellent!");
        libraryService.addReviewToBook(member.getID(), book1.getID(), 4, "Very good!");
        libraryService.addReviewToBook(member.getID(), book2.getID(), 3, "Average.");

        List<Book> sortedBooks = libraryService.sortBooksByAvgRating();

        assertEquals(2, sortedBooks.size());
        assertEquals(book1, sortedBooks.get(0)); // Book 1 should be first due to higher average rating
        assertEquals(book2, sortedBooks.get(1)); // Book 2 should be second
    }

    @Test
    public void testSortBooksByAvgRating_EmptyList() throws DatabaseException {
        List<Book> sortedBooks = libraryService.sortBooksByAvgRating();
        assertTrue(sortedBooks.isEmpty()); // Should return an empty list
    }

    @Test
    public void testGetAllBooks_Success() throws DatabaseException, EntityNotFoundException {
        Author author = new Author(1, "Author Name", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher Name", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        libraryService.addBook("Test Book 1", author.getID(), category.getID(), publisher.getID(), 5);
        libraryService.addBook("Test Book 2", author.getID(), category.getID(), publisher.getID(), 3);

        List<Book> allBooks = libraryService.getAllBooks();
        assertEquals(2, allBooks.size());
        assertEquals("Test Book 1", allBooks.get(0).getBookName());
        assertEquals("Test Book 2", allBooks.get(1).getBookName());
    }

    @Test
    public void testAddMember_Success() throws DatabaseException {
        libraryService.addMember("John Doe", "john@example.com", "1234567890");

        assertEquals(1, memberRepo.getAll().size());
        Member addedMember = memberRepo.getAll().get(0);
        assertEquals("John Doe", addedMember.getName());
        assertEquals("john@example.com", addedMember.getEmail());
    }

    @Test
    public void testGetAllMembers_Success() throws DatabaseException {
        libraryService.addMember("John Doe", "john@example.com", "1234567890");
        libraryService.addMember("Jane Doe", "jane@example.com", "0987654321");

        List<Member> allMembers = libraryService.getAllMembers();
        assertEquals(2, allMembers.size());
        assertEquals("John Doe", allMembers.get(0).getName());
        assertEquals("Jane Doe", allMembers.get(1).getName());
    }

    @Test
    public void testAddCategory_Success() throws DatabaseException {
        libraryService.addCategory("Fiction", "Fictional books");

        assertEquals(1, categoryRepo.getAll().size());
        Category addedCategory = categoryRepo.getAll().get(0);
        assertEquals("Fiction", addedCategory.getCategoryName());
        assertEquals("Fictional books", addedCategory.getDescription());
    }

    @Test
    public void testCreateLoan_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Book book = new Book(1, "Test Book", new Author(1, "Author", "author@example.com", "1234567890"), true, new Category(1, "Fiction", "Fictional books"), new Publisher(1, "Publisher", "publisher@example.com", "0987654321"), 5);
        bookRepo.add(book);

        libraryService.createLoan(book, member);

        assertEquals(1, loanRepo.getAll().size());
        assertEquals("ACTIVE", loanRepo.getAll().get(0).getStatus());
    }

    @Test
    public void testCreateLoan_BookNotAvailable_ThrowsDatabaseException() throws DatabaseException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Book book = new Book(1, "Test Book", new Author(1, "Author", "author@example.com", "1234567890"), false, new Category(1, "Fiction", "Fictional books"), new Publisher(1, "Publisher", "publisher@example.com", "0987654321"), 0);
        bookRepo.add(book);

        assertThrows(DatabaseException.class, () -> {
            libraryService.createLoan(book, member);
        });
    }

    @Test
    public void testCreateReservation_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Book book = new Book(1, "Test Book", new Author(1, "Author", "author@example.com", "1234567890"), false, new Category(1, "Fiction", "Fictional books"), new Publisher(1, "Publisher", "publisher@example.com", "0987654321"), 0);
        bookRepo.add(book);

        libraryService.createReservation(book, member);

        assertEquals(1, reservationRepo.getAll().size());
        assertEquals(book, reservationRepo.getAll().get(0).getBook());
    }

    @Test
    public void testCreateReservation_BookNotFound_ThrowsEntityNotFoundException() throws EntityNotFoundException, DatabaseException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Book book = new Book(1, "Test Book", new Author(1, "Author", "author@example.com", "1234567890"), false, new Category(1, "Fiction", "Fictional books"), new Publisher(1, "Publisher", "publisher@example.com", "0987654321"), 0);
        bookRepo.add(book);

        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.createReservation(null, member);
        });
    }

    @Test
    public void testRemoveLoan_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Book book = new Book(1, "Test Book", new Author(1, "Author", "author@example.com", "1234567890"), true, new Category(1, "Fiction", "Fictional books"), new Publisher(1, "Publisher", "publisher@example.com", "0987654321"), 5);
        bookRepo.add(book);
        Loan loan = new Loan(1, LocalDate.now(), libraryService.calculateDueDate(), null, "ACTIVE", book, member);
        loanRepo.add(loan);
        member.getLoans().add(loan);

        libraryService.removeLoan(loan);

        assertEquals(1, loanRepo.getAll().size());
        assertEquals(6, book.getCopiesAvailable());
    }

    @Test
    public void testNextReservation_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        Category category = new Category(1, "Fiction", "Fictional books");
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);

        Reservation reservation = new Reservation(1, LocalDate.now(), book, member);
        reservationRepo.add(reservation);

        member.getReservations().add(reservation);

        libraryService.nextReservation(book);

        assertEquals(0, reservationRepo.getAll().size()); // Reservation should be removed
        assertEquals(1, loanRepo.getAll().size()); // A loan should be created
        assertEquals(0, book.getCopiesAvailable()); // Copies should be reduced
        assertFalse(book.isAvailable()); // Book should no longer be available
    }

    @Test
    public void testNextReservation_BookNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.nextReservation(null);
        });
    }

    @Test
    public void testGetActiveLoansForMember_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Book book = new Book(1, "Test Book", new Author(1, "Author", "author@example.com", "1234567890"), true, new Category(1, "Fiction", "Fictional books"), new Publisher(1, "Publisher", "publisher@example.com", "0987654321"), 5);
        bookRepo.add(book);
        Loan loan = new Loan(1, LocalDate.now(), libraryService.calculateDueDate(), null, "ACTIVE", book, member);
        loanRepo.add(loan);
        member.getLoans().add(loan);

        List<Loan> activeLoans = libraryService.getActiveLoansForMember(member.getID());

        assertEquals(1, activeLoans.size());
        assertEquals(loan, activeLoans.get(0));
    }

    @Test
    public void testGetActiveLoansForMember_MemberNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.getActiveLoansForMember(999);
        });
    }

    @Test
    public void testCalculateDueDate_Success() {
        LocalDate dueDate = libraryService.calculateDueDate();
        assertEquals(LocalDate.now().plusDays(14), dueDate);
    }

    @Test
    public void testAddStaff_Success() throws DatabaseException {
        libraryService.addStaff("Jane Doe", "jane@example.com", "0987654321", "Librarian");

        assertEquals(1, staffRepo.getAll().size());
        Staff addedStaff = staffRepo.getAll().get(0);
        assertEquals("Jane Doe", addedStaff.getName());
    }

    @Test
    public void testAddStaff_ThrowsDatabaseException() {
        assertThrows(DatabaseException.class, () -> {
            libraryService.addStaff("", "jane@example.com", "0987654321", "Librarian"); // Invalid name
        });
    }

    @Test
    public void testIsStaff_Success() throws DatabaseException {
        libraryService.addStaff("Jane Doe", "jane@example.com", "0987654321", "Librarian");

        boolean result = libraryService.isStaff("jane@example.com");

        assertTrue(result);
    }

    @Test
    public void testIsStaff_NotFound() throws DatabaseException {
        boolean result = libraryService.isStaff("notfound@example.com");

        assertFalse(result);
    }

    @Test
    public void testUpdateBook_Success() throws DatabaseException, EntityNotFoundException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Old Title", author, true, category, publisher, 5);
        bookRepo.add(book);

        libraryService.updateBook(book.getID(), "New Title", author.getID(), true, 1, 1, 10);

        assertEquals("New Title", bookRepo.get(book.getID()).getBookName());
    }

    @Test
    public void testGetBooksByPublisher_Success() throws DatabaseException, EntityNotFoundException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);

        List<Book> books = libraryService.getBooksByPublisher(publisher.getID());

        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    public void testGetBooksByPublisher_PublisherNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.getBooksByPublisher(999);
        });
    }

    @Test
    public void testGetBooksByAuthor_Success() throws DatabaseException, EntityNotFoundException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);

        List<Book> books = libraryService.getBooksByAuthor(author.getID());

        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    public void testGetBooksByAuthor_AuthorNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.getBooksByAuthor(999);
        });
    }

    @Test
    public void testGetActiveReservationsForMember_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);
        Reservation reservation = new Reservation(1, LocalDate.now(), book, member);
        reservationRepo.add(reservation);
        member.getReservations().add(reservation);

        List<Reservation> reservations = libraryService.getActiveReservationsForMember(member.getID());

        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    public void testGetActiveReservationsForMember_MemberNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.getActiveReservationsForMember(999);
        });
    }

    @Test
    public void testGetLoanHistoryForMember_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);
        Loan loan = new Loan(1, LocalDate.now(), libraryService.calculateDueDate(), null, "ACTIVE", book, member);
        loanRepo.add(loan);
        member.getLoanHistory().add(loan);

        List<Loan> loanHistory = libraryService.getLoanHistoryForMember(member.getID());

        assertEquals(1, loanHistory.size());
        assertEquals(loan, loanHistory.get(0));
    }

    @Test
    public void testGetLoanHistoryForMember_MemberNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.getLoanHistoryForMember(999);
        });
    }

    @Test
    public void testAddBookToCategory_Success() throws DatabaseException, EntityNotFoundException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);

        libraryService.addBookToCategory(book, category.getID());

        assertEquals(category, book.getCategory());
        assertTrue(category.getBooks().contains(book));
    }

    @Test
    public void testAddBookToCategory_BookNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.addBookToCategory(null, 1);
        });
    }

    @Test
    public void testAddBookToAuthor_Success() throws DatabaseException, EntityNotFoundException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);

        libraryService.addBookToAuthor(book, author.getID());

        assertEquals(author, book.getAuthor());
        assertTrue(author.getBooks().contains(book));
    }

    @Test
    public void testAddBookToAuthor_BookNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.addBookToAuthor(null, 1);
        });
    }

    @Test
    public void testAddBookToPublisher_Success() throws DatabaseException, EntityNotFoundException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);

        libraryService.addBookToPublisher(book, publisher.getID());

        assertEquals(publisher, book.getPublisher());
        assertTrue(publisher.getPublishedBooks().contains(book));
    }

    @Test
    public void testAddBookToPublisher_BookNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.addBookToPublisher(null, 1);
        });
    }

    @Test
    public void testGetAllBooksInCategory_Success() throws DatabaseException, EntityNotFoundException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);
        category.getBooks().add(book);

        List<Book> books = libraryService.getAllBooksInCategory(category.getID());

        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    public void testGetAllBooksInCategory_CategoryNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class,() -> {
            libraryService.getAllBooksInCategory(999);
        });
    }

    @Test
    public void testGetAllPublishers_Success() throws DatabaseException {
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);

        List<Publisher> publishers = libraryService.getAllPublishers();

        assertEquals(1, publishers.size());
        assertEquals(publisher, publishers.get(0));
    }

    @Test
    public void testGetAllAuthors_Success() throws DatabaseException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);

        List<Author> authors = libraryService.getAllAuthors();

        assertEquals(1, authors.size());
        assertEquals(author, authors.get(0));
    }

    @Test
    public void testGetAllCategories_Success() throws DatabaseException {
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);

        List<Category> categories = libraryService.getAllCategories();

        assertEquals(1, categories.size());
        assertEquals(category, categories.get(0));
    }

    @Test
    public void testGetAllReservations_Success() throws DatabaseException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);
        Reservation reservation = new Reservation(1, LocalDate.now(), book, member);
        reservationRepo.add(reservation);

        List<Reservation> reservations = libraryService.getAllReservations();

        assertEquals(1, reservations.size());
        assertEquals(reservation, reservations.get(0));
    }

    @Test
    public void testGetAllLoans_Success() throws DatabaseException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);
        Loan loan = new Loan(1, LocalDate.now(), libraryService.calculateDueDate(), null, "ACTIVE", book, member);
        loanRepo.add(loan);

        List<Loan> loans = libraryService.getAllLoans();

        assertEquals(1, loans.size());
        assertEquals(loan, loans.get(0));
    }

    @Test
    public void testGetAllReviews_Success() throws DatabaseException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);
        Review review = new Review(1, 5, "Great book!", book, member);
        reviewRepo.add(review);

         List<Review> reviews = libraryService.getAllReviews();

         assertEquals(1, reviews.size());
         assertEquals(review, reviews.get(0));
    }

    @Test
    public void testAddAuthor_Success() throws DatabaseException {
        libraryService.addAuthor("New Author", "newauthor@example.com", "1234567890");

        assertEquals(1, authorRepo.getAll().size());
        Author addedAuthor = authorRepo.getAll().get(0);
        assertEquals("New Author", addedAuthor.getName());
    }

    @Test
    public void testAddAuthor_ThrowsDatabaseException() {
        assertThrows(DatabaseException.class, () -> {
            libraryService.addAuthor("", "newauthor@example.com", "1234567890"); // Invalid name
        });
    }

    @Test
    public void testAddPublisher_Success() throws DatabaseException {
        libraryService.addPublisher("New Publisher", "newpublisher@example.com", "0987654321");

        assertEquals(1, publisherRepo.getAll().size());
        Publisher addedPublisher = publisherRepo.getAll().get(0);
        assertEquals("New Publisher", addedPublisher.getName());
    }

    @Test
    public void testAddPublisher_ThrowsDatabaseException() {
        assertThrows(DatabaseException.class, () -> {
            libraryService.addPublisher(null, "newpublisher@example.com", "0987654321");
        });
    }

    @Test
    public void testGetIDbyEmail_Success() throws DatabaseException, EntityNotFoundException {
        Member member = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member);

        int memberId = libraryService.getIDbyEmail("john@example.com");

        assertEquals(member.getID(), memberId);
    }

    @Test
    public void testGetIDbyEmail_NotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.getIDbyEmail("notfound@example.com");
        });
    }

    @Test
    public void testSearchBook_Success() throws DatabaseException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);

        List<Book> foundBooks = libraryService.searchBook("Test Book");

        assertEquals(1, foundBooks.size());
        assertEquals(book, foundBooks.get(0));
    }

    @Test
    public void testSearchBook_NotFound() throws DatabaseException {
        List<Book> foundBooks = libraryService.searchBook("Non-existent Book");

        assertTrue(foundBooks.isEmpty());
    }

    @Test
    public void testGetAllBooksSortedByTitle_Success() throws DatabaseException {
        Author author1 = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author1);
        Author author2 = new Author(2, "Another Author", "anotherauthor@example.com", "0987654321");
        authorRepo.add(author2);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book1 = new Book(1, "B Book", author1, true, category, publisher, 5);
        bookRepo.add(book1);
        Book book2 = new Book(2, "A Book", author2, true, category, publisher, 5);
        bookRepo.add(book2);

        List<Book> sortedBooks = libraryService.getAllBooksSortedByTitle();

        assertEquals(2, sortedBooks.size());
        assertEquals("A Book", sortedBooks.get(0).getBookName());
        assertEquals("B Book", sortedBooks.get(1).getBookName());
    }

    @Test
    public void testCalculateAverageRating_Success() throws DatabaseException {
        Author author = new Author(1, "Author", "author@example.com", "1234567890");
        authorRepo.add(author);
        Category category = new Category(1, "Fiction", "Fictional books");
        categoryRepo.add(category);
        Publisher publisher = new Publisher(1, "Publisher", "publisher@example.com", "0987654321");
        publisherRepo.add(publisher);
        Book book = new Book(1, "Test Book", author, true, category, publisher, 1);
        bookRepo.add(book);

        Member member1 = new Member(1, "John Doe", "john@example.com", "1234567890");
        memberRepo.add(member1);
        Member member2 = new Member(2, "Jane Doe", "jane@example.com", "0987654321");
        memberRepo.add(member2);

        Review review1 = new Review(1, 5, "Great book!", book, member1);
        reviewRepo.add(review1);
        Review review2 = new Review(2, 3, "Not bad", book, member2);
        reviewRepo.add(review2);

        book.getReviews().add(review1);
        book.getReviews().add(review2);

        double averageRating = libraryService.calculateAverageRating(book);

        assertEquals(4.0, averageRating, 0.01);
    }

    @Test
    public void testCheckMemberHasOverdueLoans_MemberNotFound_ThrowsEntityNotFoundException() {
        assertThrows(EntityNotFoundException.class, () -> {
            libraryService.checkMemberHasOverdueLoans(999);
        });
    }





}
