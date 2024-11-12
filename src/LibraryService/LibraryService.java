package LibraryService;

import LibraryModel.*;
import LibraryRepository.InMemoryRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * LibraryService provides various operations for managing the library system,
 * including handling books, members, loans, reservations, reviews, authors,
 * publishers, and staff. It interacts with in-memory repositories to perform
 * CRUD operations.
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

    /**
     * Constructs a LibraryService with the specified repositories for managing
     * books, loans, reservations, categories, members, reviews, authors,
     * publishers, and staff.
     *
     * @param bookRepo the repository for managing books
     * @param loanRepo the repository for managing loans
     * @param reservationRepo the repository for managing reservations
     * @param categoryRepo the repository for managing categories
     * @param memberRepo the repository for managing members
     * @param reviewRepo the repository for managing reviews
     * @param authorRepo the repository for managing authors
     * @param publisherRepo the repository for managing publishers
     * @param staffRepo the repository for managing staff
     */

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

    /**
     * Adds a review to a specific book if the member has borrowed it.
     *
     * @param memberID the ID of the member adding the review
     * @param bookID the ID of the book being reviewed
     * @param rating the rating given to the book
     * @param reviewText the text of the review
     */

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

    /**
     * Deletes a review from a book.
     *
     * @param reviewID the ID of the review to be deleted
     */

    public void deleteReviewFromBook(int reviewID) {
        Review reviewToDelete = reviewRepo.get(reviewID);
        if (reviewToDelete != null) {
            Book book = reviewToDelete.getBook();
            book.getReviews().remove(reviewToDelete);
            reviewRepo.delete(reviewID);
        }
    }

    /**
     * Retrieves all reviews for a specific book.
     *
     * @param bookID the ID of the book
     * @return a list of reviews for the specified book
     */

    public List<Review> getAllReviewsOfBook(int bookID) {
        Book book = bookRepo.get(bookID);
        return book != null ? book.getReviews() : new ArrayList<>();
    }

    /**
     * Borrows a book for a specific member. If the book is not available,
     * it creates a reservation instead.
     *
     * @param memberID the ID of the member borrowing the book
     * @param bookID the ID of the book to be borrowed
     */

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

    /**
     * Checks if a member has any overdue loans.
     *
     * @param memberID the ID of the member to check
     * @return true if the member has overdue loans, false otherwise
     */

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

    /**
     * Returns a borrowed book for a member and checks if there are any reservations
     * for that book. If there are, it fulfills the reservation.
     *
     * @param loanID the ID of the loan to be returned
     */

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

    /**
     * Retrieves active loans for a specific member.
     *
     * @param memberID the ID of the member
     * @return a list of active loans for the specified member
     */

    public List<Loan> getActiveLoansForMember(int memberID) {
        Member member = memberRepo.get(memberID);
        return member != null ? member.getLoans() : new ArrayList<>();
    }

    /**
     * Calculates the due date for a new loan, which is 14 days from the current date.
     *
     * @return the calculated due date
     */

    public Date calculateDueDate() {
        // Default loan period is 14 days
        long loanPeriod = 14L * 24 * 60 * 60 * 1000;  // 14 days in milliseconds
        return new Date(System.currentTimeMillis() + loanPeriod);
    }

    /**
     * Adds a new book to the library.
     *
     * @param bookName the name of the book
     * @param authorID the ID of the author of the book
     * @param categoryID the ID of the category of the book
     * @param publisherID the ID of the publisher of the book
     */

    public void addBook(String bookName, int authorID, int categoryID, int publisherID) {
        Author author = authorRepo.get(authorID);
        Category category = categoryRepo.get(categoryID);
        Publisher publisher = publisherRepo.get(publisherID);
        Book book = new Book(++newBookID, bookName, author, true, category, publisher);
        bookRepo.add(book);
    }

    /**
     * Adds a new staff member to the library.
     *
     * @param name the name of the staff member
     * @param email the email of the staff member
     * @param phoneNumber the phone number of the staff member
     * @param position the position of the staff member
     */

    public void addStaff(String name, String email, String phoneNumber, String position) {
        Staff staff = new Staff(++newStaffID, name, email, phoneNumber, position);
        staffRepo.add(staff);
    }

    /**
     * Checks if a user with the given email is a staff member.
     *
     * @param email the email of the user
     * @return true if the user is a staff member, false otherwise
     */

    public boolean isStaff(String email) {
        List<Staff> staffs = staffRepo.getAll();
        for(Staff staff : staffs ) {
            if(staff.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Updates the details of an existing book.
     *
     * @param bookID the ID of the book to be updated
     * @param newBookName the new name of the book
     * @param newAuthorID the new author ID
     * @param newIsAvailable the new availability status of the book
     * @param newCategoryID the new category ID
     * @param newPublisherID the new publisher ID
     */

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

    /**
     * Deletes a book from the library.
     *
     * @param bookID the ID of the book to be deleted
     */

    public void deleteBook(int bookID) {
        Book book = bookRepo.get(bookID);
        if (book != null) {
            bookRepo.delete(bookID);
        }
    }

    /**
     * Retrieves all books published by a specific publisher.
     *
     * @param publisherID the ID of the publisher
     * @return a list of books published by the specified publisher
     */

    public List<Book> getBooksByPublisher(int publisherID) {
        return bookRepo.getAll().stream()
            .filter(book -> book.getPublisher().getID() == publisherID)
            .collect(Collectors.toList());
    }

    /**
     * Retrieves all books written by a specific author.
     *
     * @param authorID the ID of the author
     * @return a list of books written by the specified author
     */

    public List<Book> getBooksByAuthor(int authorID) {
        return bookRepo.getAll().stream()
                .filter(book -> book.getAuthor().getID() == authorID)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all active reservations for a specific member.
     *
     * @param memberID the ID of the member
     * @return a list of active reservations for the specified member
     */

    public List<Reservation> getActiveReservationsForMember(int memberID) {
        Member member = memberRepo.get(memberID);
        return member.getReservations();
    }

    /**
     * Retrieves the loan history for a specific member.
     *
     * @param memberID the ID of the member
     * @return a list of loans that the specified member has previously borrowed
     */

    public List<Loan> getLoanHistoryForMember(int memberID) {
        Member member = memberRepo.get(memberID);
        if (member != null) {
            return member.getLoanHistory();
        }
        else {
            return new ArrayList<>();
        }
    }

    /**
     * Adds a book to a specific category.
     *
     * @param bookID the ID of the book to be added to the category
     * @param categoryID the ID of the category to which the book will be added
     */

    public void addBookToCategory(int bookID, int categoryID) {
        Book book = bookRepo.get(bookID);
        Category category = categoryRepo.get(categoryID);

        if (book != null && category != null) {
            book.setCategory(category);
            category.getBooks().add(book);
        }
    }

    /**
     * Retrieves all books that belong to a specific category.
     *
     * @param categoryID the ID of the category
     * @return a list of books in the specified category
     */

    public List<Book> getAllBooksInCategory(int categoryID) {
        return bookRepo.getAll().stream()
                .filter(book -> book.getCategory().getID() == categoryID)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all publishers in the library.
     *
     * @return a list of all publishers
     */

    public List<Publisher> getAllPublishers() {
        return publisherRepo.getAll();
    }

    /**
     * Retrieves all authors in the library.
     *
     * @return a list of all authors
     */

    public List<Author> getAllAuthors() {
        return authorRepo.getAll();
    }

    /**
     * Retrieves all categories in the library.
     *
     * @return a list of all categories
     */

    public List<Category> getAllCategories() {
        return categoryRepo.getAll();
    }

    /**
     * Retrieves all reservations in the library.
     *
     * @return a list of all reservations
     */

    public List<Reservation> getAllReservations() {
        return reservationRepo.getAll();
    }

    /**
     * Retrieves all loans in the library.
     *
     * @return a list of all loans
     */

    public List<Loan> getAllLoans() {
        return loanRepo.getAll();
    }

    /**
     * Retrieves all members in the library.
     *
     * @return a list of all members
     */

    public List<Member> getAllMembers() {
        return memberRepo.getAll();
    }

    /**
     * Retrieves all reviews in the library.
     *
     * @return a list of all reviews
     */

    public List<Review> getAllReviews() {
        return reviewRepo.getAll();
    }

    /**
     * Retrieves all books in the library.
     *
     * @return a list of all books
     */

    public List<Book> getAllBooks() {
        return bookRepo.getAll();
    }

    /**
     * Adds a new member to the library.
     *
     * @param name the name of the member
     * @param email the email of the member
     * @param phoneNumber the phone number of the member
     */

    public void addMember(String name, String email, String phoneNumber) {
        Member member = new Member(++newMemberID, name, email, phoneNumber);
        memberRepo.add(member);
    }

    /**
     * Adds a new author to the library.
     *
     * This method creates a new Author instance with the provided name, email, and phone number.
     * It initializes an empty list of books associated with the author.
     *
     * @param name the name of the author
     * @param email the email of the author
     * @param phoneNumber the phone number of the author
     */

    public void addAuthor(String name, String email, String phoneNumber) {
        Author author = new Author(++newAuthorID, name, email, phoneNumber);
        List<Book> books = new ArrayList<>();
        authorRepo.add(author);
    }

    /**
     * Adds a new publisher to the library.
     *
     * This method creates a new Publisher instance with the provided name, email, and phone number.
     * It initializes an empty list of published books associated with the publisher.
     *
     * @param name the name of the publisher
     * @param email the email of the publisher
     * @param phoneNumber the phone number of the publisher
     */

    public void addPublisher(String name, String email, String phoneNumber) {
        Publisher publisher = new Publisher(++newPublisherID, name, email, phoneNumber);
        List<Book> publishedBooks = new ArrayList<>();
        publisherRepo.add(publisher);
    }
}
