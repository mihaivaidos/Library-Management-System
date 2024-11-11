package LibraryService;

import LibraryModel.*;
import LibraryRepository.InMemoryRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
    Add book
    Update book
    Delete book
            Get all books
            View books by a publisher
            View books by an author
        Add review to a book
        View all reviews to a book
        Borrow a book (reserve one if not available)
        Return a book
        View active loans
                View active reservation
                View loan history for a member
Calculate due date for new loan
                            Add book to a category
                            View all books in a category
Delete review
 */

public class LibraryService {

    private final InMemoryRepository<Book> bookRepo;
    private final InMemoryRepository<Loan> loanRepo;
    private final InMemoryRepository<Reservation> reservationRepo;
    private final InMemoryRepository<Category> categoryRepo;
    private final InMemoryRepository<Member> memberRepo;
    private final InMemoryRepository<Review> reviewRepo;
    private final InMemoryRepository<Author> authorRepo;
    private final InMemoryRepository<Publisher> publisherRepo;
    private final InMemoryRepository<Staff> staffRepo;

    private int newBookID = 9;
    private int newMemberID = 9;
    private int newLoanID = 9;
    private int newReviewID = 9;
    private int newAuthorID = 9;
    private int newCategoryID = 9;
    private int newPublisherID = 9;
    private int newReservationID = 9;
    private int newStaffID = 9;

    public LibraryService(InMemoryRepository<Book> bookRepo, InMemoryRepository<Loan> loanRepo,
                          InMemoryRepository<Reservation> reservationRepo, InMemoryRepository<Category>
                                  categoryRepo, InMemoryRepository<Member> memberRepo, InMemoryRepository<Review> reviewRepo,
                          InMemoryRepository<Author> authorRepo, InMemoryRepository<Publisher> publisherRepo, InMemoryRepository<Staff> staffRepo) {
        this.bookRepo = bookRepo;
        this.loanRepo = loanRepo;
        this.reservationRepo = reservationRepo;
        this.categoryRepo = categoryRepo;
        this.memberRepo = memberRepo;
        this.reviewRepo = reviewRepo;
        this.authorRepo = authorRepo;
        this.publisherRepo = publisherRepo;
        this.staffRepo = staffRepo;
    }

    public void addReviewToBook(int memberID, int bookID, int rating, String reviewText) {
        Member member = memberRepo.get(memberID);
        Book book = bookRepo.get(bookID);
        if (book != null && member != null) {
            for (Loan loan : member.getLoanHistory()) {
                if (loan.getBook().getID() == bookID) {
                    Review review = new Review(++newReviewID, rating, reviewText, book, member);
                    book.getReviews().add(review);
                    reviewRepo.add(review);
                }
            }
        }
    }

    public void deleteReviewFromBook(int reviewID) {
        Review reviewToDelete = reviewRepo.get(reviewID);
        if (reviewToDelete != null) {
            Book book = reviewToDelete.getBook();
            book.getReviews().remove(reviewToDelete);
            reviewRepo.delete(reviewID);
        }
    }

    public List<Review> getAllReviewsOfBook(int bookID) {
        Book book = bookRepo.get(bookID);
        return book != null ? book.getReviews() : new ArrayList<>();
    }

    // Loan and Reservation Management

    public void borrowBook(int memberID, int bookID) {
        Book book = bookRepo.get(bookID);
        Member member = memberRepo.get(memberID);

        if (book != null && member != null) {
            if (!checkMemberHasOverdueLoans(memberID)) {
                int activeLoans = getActiveLoansForMember(memberID).size();
                if (activeLoans <= 2) {
                    if (book.isAvailable()) {
                        // Book is available, proceed to loan
                        Loan loan = new Loan(++newLoanID, new Date(), calculateDueDate(), null, "ACTIVE", book, member);
                        loanRepo.add(loan);
                        book.setAvailable(false);  // Mark book as unavailable
                        member.getLoans().add(loan);
                        member.getLoanHistory().add(loan);
                    }
                    else {
                        // Book is not available, create a reservation
                        Reservation reservation = new Reservation(++newReservationID, new Date(), book, member);
                        reservationRepo.add(reservation);
                        member.getReservations().add(reservation);
                    }
                }
            }
        }
    }

    public boolean checkMemberHasOverdueLoans(int memberID) {
        Member member = memberRepo.get(memberID);
        List<Loan> memberLoans = member.getLoans();

        Date today = new Date();
        for (Loan loan : memberLoans) {
            if (loan.getDueDate().before(today)) {
                return true;
            }
        }
        return false;
    }

    public void returnBook(int loanID) {
        Loan loan = loanRepo.get(loanID);
        if (loan != null && "ACTIVE".equals(loan.getStatus())) {
            loan.setStatus("RETURNED");
            loan.setReturnDate(new Date());
            Book book = loan.getBook();
            book.setAvailable(true);
            Member member = loan.getMember();
            member.getLoans().remove(loan);

            // Check if there are any reservations for this book
            Optional<Reservation> nextReservation = reservationRepo.getAll().stream()
                    .filter(reservation -> reservation.getBook().equals(book))
                    .findFirst();

            if (nextReservation.isPresent()) {
                Reservation reservation = nextReservation.get();
                Member memberRes = reservation.getMember();
                // Create a new loan for the member with reservation
                Loan newLoan = new Loan(++newLoanID, new Date(), calculateDueDate(), null, "ACTIVE", book, memberRes);
                loanRepo.add(newLoan);
                memberRes.getLoans().add(newLoan);
                memberRes.getLoanHistory().add(newLoan);
                memberRes.getReservations().remove(reservation);
                reservationRepo.delete(reservation.getID());  // Remove the reservation
                book.setAvailable(false);
            }
        }
    }

    public List<Loan> getActiveLoansForMember(int memberID) {
        Member member = memberRepo.get(memberID);
        return member != null ? member.getLoans() : new ArrayList<>();
    }

    public Date calculateDueDate() {
        // Default loan period is 14 days
        long loanPeriod = 14L * 24 * 60 * 60 * 1000;  // 14 days in milliseconds
        return new Date(System.currentTimeMillis() + loanPeriod);
    }

    public void addBook(String bookName, int authorID, int categoryID, int publisherID) {
        Author author = authorRepo.get(authorID);
        Category category = categoryRepo.get(categoryID);
        Publisher publisher = publisherRepo.get(publisherID);
        Book book = new Book(++newBookID, bookName, author, true, category, publisher);
        bookRepo.add(book);
    }

    public void addStaff(String name, String email, String phoneNumber, String position) {
        Staff staff = new Staff(++newStaffID, name, email, phoneNumber, position);
        staffRepo.add(staff);
    }

    public boolean isStaff(String email) {
        List<Staff> staffs = staffRepo.getAll();
        for(Staff staff : staffs ) {
            if(staff.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void updateBook(int bookID, String newBookName, int newAuthorID, boolean newIsAvailable, int newCategoryID, int newPublisherID) {
        Book book = bookRepo.get(bookID);
        Author author = authorRepo.get(newAuthorID);
        Category category = categoryRepo.get(newCategoryID);
        Publisher publisher = publisherRepo.get(newPublisherID);
        if (book != null) {
            if (newBookName != null) {
                book.setBookName(newBookName);
            }
            if (author != null) {
                book.setAuthor(author);
            }
            book.setAvailable(newIsAvailable);
            if (category != null) {
                book.setCategory(category);
            }
            if (publisher != null) {
                book.setPublisher(publisher);
            }
            bookRepo.update(book);
        }
    }

    public void deleteBook(int bookID) {
        Book book = bookRepo.get(bookID);
        if (book != null) {
            bookRepo.delete(bookID);
        }
    }

    public List<Book> getBooksByPublisher(int publisherID) {
        return bookRepo.getAll().stream()
            .filter(book -> book.getPublisher().getID() == publisherID)
            .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(int authorID) {
        return bookRepo.getAll().stream()
                .filter(book -> book.getAuthor().getID() == authorID)
                .collect(Collectors.toList());
    }

    public List<Reservation> getActiveReservationsForMember(int memberID) {
        Member member = memberRepo.get(memberID);
        return member.getReservations();
    }

    public List<Loan> getLoanHistoryForMember(int memberID) {
        Member member = memberRepo.get(memberID);
        if (member != null) {
            return member.getLoanHistory();
        }
        else {
            return new ArrayList<>();
        }
    }

    public void addBookToCategory(int bookID, int categoryID) {
        Book book = bookRepo.get(bookID);
        Category category = categoryRepo.get(categoryID);

        if (book != null && category != null) {
            book.setCategory(category);
            category.getBooks().add(book);
        }
    }

    public List<Book> getAllBooksInCategory(int categoryID) {
        return bookRepo.getAll().stream()
                .filter(book -> book.getCategory().getID() == categoryID)
                .collect(Collectors.toList());
    }

    public List<Publisher> getAllPublishers() {
        return publisherRepo.getAll();
    }

    public List<Author> getAllAuthors() {
        return authorRepo.getAll();
    }

    public List<Category> getAllCategories() {
        return categoryRepo.getAll();
    }

    public List<Reservation> getAllReservations() {
        return reservationRepo.getAll();
    }

    public List<Loan> getAllLoans() {
        return loanRepo.getAll();
    }

    public List<Member> getAllMembers() {
        return memberRepo.getAll();
    }

    public List<Review> getAllReviews() {
        return reviewRepo.getAll();
    }

    public List<Book> getAllBooks() {
        return bookRepo.getAll();
    }
}
