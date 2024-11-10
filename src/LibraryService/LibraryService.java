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

    private int newBookID = 0;
    private int newMemberID = 0;
    private int newLoanID = 0;
    private int newReviewID = 0;
    private int newAuthorID = 0;
    private int newCategoryID = 0;
    private int newPublisherID = 0;
    private int newReservationID = 0;

    public LibraryService(InMemoryRepository<Book> bookRepo, InMemoryRepository<Loan> loanRepo,
                          InMemoryRepository<Reservation> reservationRepo, InMemoryRepository<Category>
                                  categoryRepo, InMemoryRepository<Member> memberRepo, InMemoryRepository<Review> reviewRepo,
                          InMemoryRepository<Author> authorRepo, InMemoryRepository<Publisher> publisherRepo) {
        this.bookRepo = bookRepo;
        this.loanRepo = loanRepo;
        this.reservationRepo = reservationRepo;
        this.categoryRepo = categoryRepo;
        this.memberRepo = memberRepo;
        this.reviewRepo = reviewRepo;
        this.authorRepo = authorRepo;
        this.publisherRepo = publisherRepo;
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
            book.setAvailable(true); // Make book available again
            Member member = loan.getMember();
            member.getLoans().remove(loan);

            // Check if there are any reservations for this book
            Optional<Reservation> nextReservation = reservationRepo.getAll().stream()
                    .filter(reservation -> reservation.getBook().equals(book))
                    .findFirst();

            if (nextReservation.isPresent()) {
                Reservation reservation = nextReservation.get();
                // Create a new loan for the member with reservation
                Loan newLoan = new Loan(++newLoanID, new Date(), calculateDueDate(), null, "ACTIVE", book, member);
                loanRepo.add(newLoan);
                member.getLoanHistory().add(newLoan);
                reservationRepo.delete(reservation.getID());  // Remove the reservation
                book.setAvailable(false);  // Mark book as unavailable again
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

    public void addBook(String bookName, Author author, boolean isAvailable, Category category, Publisher publisher) {
        // Generate a unique ID
        bookRepo.add(new Book(++newBookID, bookName, author, isAvailable, category, publisher));
    }

    public void updateBook(int bookID, String newBookName, Author newAuthor, boolean newIsAvailable, Category newCategory, Publisher newPublisher) {
        Book book = bookRepo.get(bookID);
        if (book != null) {
            if (newBookName != null) {
                book.setBookName(newBookName);
            }
            if (newAuthor != null) {
                book.setAuthor(newAuthor);
            }
            book.setAvailable(newIsAvailable);
            if (newCategory != null) {
                book.setCategory(newCategory);
            }
            if (newPublisher != null) {
                book.setPublisher(newPublisher);
            }
            bookRepo.update(book);
        }
        else {
            // Book is not found
            System.out.println("Book with ID " + bookID + " not found.");
        }
    }

    public void deleteBook(int bookID) {
        Book book = bookRepo.get(bookID);
        if (book != null) {
            bookRepo.delete(bookID);
        } else {
            System.out.println("Book with ID " + bookID + " not found.");
        }
    }

    public List<Book> getAllBooks() {
        return (List<Book>) bookRepo.getAll();
    }

//    public List<Book> getBooksByPublisher(Publisher publisher) {
//        List<Book> booksByPublisher = new ArrayList<>();
//        for (Book book : bookRepo.getAll()) {
//            if (book.getPublisher().equals(publisher)) {
//                booksByPublisher.add(book);
//            }
//        }
//        return booksByPublisher;
//    }
    public List<Book> getBooksByPublisher(Publisher publisher) {
        return bookRepo.getAll().stream()
            .filter(book -> book.getPublisher().equals(publisher))
            .collect(Collectors.toList());
    }

    public List<Book> getBooksByAuthor(Author author) {
        return bookRepo.getAll().stream()
                .filter(book -> book.getAuthor().equals(author))
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


}
