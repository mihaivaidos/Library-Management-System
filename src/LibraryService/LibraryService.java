package LibraryService;

import Exceptions.BusinessLogicException;
import Exceptions.DatabaseException;
import Exceptions.EntityNotFoundException;
import LibraryModel.*;
import LibraryRepository.IRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * LibraryService provides various operations for managing the library system,
 * including handling books, members, loans, reservations, reviews, authors,
 * publishers, and staff. It interacts with in-memory repositories to perform
 * CRUD operations.
 */

public class LibraryService {

    private final IRepository<Book> bookRepo;
    private final IRepository<Loan> loanRepo;
    private final IRepository<Reservation> reservationRepo;
    private final IRepository<Category> categoryRepo;
    private final IRepository<Member> memberRepo;
    private final IRepository<Review> reviewRepo;
    private final IRepository<Author> authorRepo;
    private final IRepository<Publisher> publisherRepo;
    private final IRepository<Staff> staffRepo;

    private int newBookID;
    private int newMemberID;
    private int newLoanID;
    private int newReviewID;
    private int newAuthorID;
    private int newCategoryID;
    private int newPublisherID;
    private int newReservationID;
    private int newStaffID;

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

    public LibraryService(IRepository<Book> bookRepo, IRepository<Loan> loanRepo,
                          IRepository<Reservation> reservationRepo, IRepository<Category>
                                  categoryRepo, IRepository<Member> memberRepo, IRepository<Review> reviewRepo,
                          IRepository<Author> authorRepo, IRepository<Publisher> publisherRepo, IRepository<Staff> staffRepo) {
        this.bookRepo = bookRepo;
        this.loanRepo = loanRepo;
        this.reservationRepo = reservationRepo;
        this.categoryRepo = categoryRepo;
        this.memberRepo = memberRepo;
        this.reviewRepo = reviewRepo;
        this.authorRepo = authorRepo;
        this.publisherRepo = publisherRepo;
        this.staffRepo = staffRepo;

        this.newBookID = getMaxId(bookRepo);
        this.newMemberID = getMaxId(memberRepo);
        this.newLoanID = getMaxId(loanRepo);
        this.newReviewID = getMaxId(reviewRepo);
        this.newAuthorID = getMaxId(authorRepo);
        this.newCategoryID = getMaxId(categoryRepo);
        this.newPublisherID = getMaxId(publisherRepo);
        this.newReservationID = getMaxId(reservationRepo);
        this.newStaffID = getMaxId(staffRepo);
    }

    private <T extends HasID> int getMaxId(IRepository<T> repository) {
        try {
            return repository.getAll().stream()
                    .mapToInt(HasID::getID)
                    .max()
                    .orElse(1);
        } catch (DatabaseException e) {
            throw new RuntimeException("Unable to get ID", e);
        }
    }

    /**
     * Adds a review to a specific book if the member has borrowed it.
     *
     * @param memberID the ID of the member adding the review
     * @param bookID the ID of the book being reviewed
     * @param rating the rating given to the book
     * @param reviewText the text of the review
     */

    public void addReviewToBook(int memberID, int bookID, int rating, String reviewText) throws EntityNotFoundException, BusinessLogicException, DatabaseException {
        try {
            Member member = memberRepo.get(memberID);
            Book book = bookRepo.get(bookID);

            if (book == null) {
                throw new EntityNotFoundException("Book not found.");
            }
            if (member == null) {
                throw new EntityNotFoundException("Member not found.");
            }
            boolean hasBorrowed = member.getLoanHistory().stream()
                    .anyMatch(loan -> loan.getBook().getID() == bookID);
            if (!hasBorrowed) {
                throw new BusinessLogicException("Member must borrow the book before adding a review.");
            }

            Review review = new Review(++newReviewID, rating, reviewText, book, member);
            book.getReviews().add(review);
            reviewRepo.add(review);
            bookRepo.update(book);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding review to book.");
        }
    }

    /**
     * Gets all the books a member has borrowed.
     *
     * @param memberID the ID of the member
     * @return a list of borrowed books by the member
     */

    public List<Book> getMemberBorrowedBooks(int memberID) throws EntityNotFoundException, DatabaseException {
        try {
            List<Book> books = new ArrayList<>();
            Member member = memberRepo.get(memberID);

            if (member == null) {
                throw new EntityNotFoundException("Member not found.");
            }

            for (Loan loan : member.getLoanHistory()) {
                books.add(loan.getBook());
            }
            return books;
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting member borrowed books.");
        }
    }

    /**
     * Deletes a review from a book.
     *
     * @param reviewID the ID of the review to be deleted
     */

    public void deleteReviewFromBook(int reviewID) throws EntityNotFoundException, DatabaseException {
        try {
            Review reviewToDelete = reviewRepo.get(reviewID);
            if (reviewToDelete == null) {
                throw new EntityNotFoundException("Review not found.");
            }
            Book book = reviewToDelete.getBook();
            //Member member = reviewToDelete.getMember();
//            List<Loan> memberLoans = member.getLoanHistory();
//
//            boolean memberHasBorrowedBook = memberLoans.stream()
//                    .anyMatch(loan -> loan.getBook().getID() == book.getID());
            book.getReviews().remove(reviewToDelete);
            bookRepo.update(book);
            reviewRepo.delete(reviewID);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error deleting review from book.");
        }
    }

    /**
     * Retrieves all reviews for a specific book.
     *
     * @param bookID the ID of the book
     * @return a list of reviews for the specified book
     */

    public List<Review> getAllReviewsOfBook(int bookID) throws EntityNotFoundException, DatabaseException {
        try {
            Book book = bookRepo.get(bookID);

            if (book == null) {
                throw new EntityNotFoundException("Book not found.");
            }

            return book.getReviews();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting reviews from book.");
        }
    }

    /**
     * Borrows a book for a specific member. If the book is not available,
     * it creates a reservation instead.
     *
     * @param memberID the ID of the member borrowing the book
     * @param bookID the ID of the book to be borrowed
     */

    public void borrowBook(int memberID, int bookID) throws EntityNotFoundException, BusinessLogicException, DatabaseException {
        try {
            Book book = bookRepo.get(bookID);
            Member member = memberRepo.get(memberID);

            if (book == null) {
                throw new EntityNotFoundException("Book not found.");
            }
            if (member == null) {
                throw new EntityNotFoundException("Member not found.");
            }
            if (checkMemberHasOverdueLoans(memberID)) {
                throw new BusinessLogicException("Cannot borrow books with overdue loans.");
            }

            int activeLoans = getActiveLoansForMember(memberID).size();
            if (activeLoans >= 3) {
                throw new BusinessLogicException("Loan limit reached. Return books before borrowing more.");
            }

            if (book.isAvailable()) {
                createLoan(book, member);
            } else {
                createReservation(book, member);
            }
        } catch (DatabaseException e) {
            throw new DatabaseException("Error borrowing book.");
        }
    }

    /**
     * Creates a new loan for a book that a member has made
     *
     * @param book the book that is borrowed
     * @param member the member that borrows the book
     */
    public void createLoan(Book book, Member member) throws DatabaseException {
        try {
            Loan loan = new Loan(++newLoanID, LocalDate.now(), calculateDueDate(), null, "ACTIVE", book, member);
            loanRepo.add(loan);
            book.setCopiesAvailable(book.getCopiesAvailable() - 1);
            if (book.getCopiesAvailable() < 1) {
                book.setAvailable(false);
            }
            member.getLoans().add(loan);
            member.getLoanHistory().add(loan);
            member = loan.getMember();
            bookRepo.update(book);
            memberRepo.update(member);
            loanRepo.update(loan);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error creating loan.");
        }
    }

    /**
     * Creates a new reservation for a book and adds it to the member
     *
     * @param book the book that is reserved
     * @param member the member that makes the reservation
     */
    public void createReservation(Book book, Member member) throws DatabaseException {
        try {
            Reservation reservation = new Reservation(++newReservationID, LocalDate.now(), book, member);
            reservationRepo.add(reservation);
            member.getReservations().add(reservation);
            memberRepo.update(member);
            reservationRepo.update(reservation);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error creating reservation.");
        }
    }

    /**
     * Checks if a member has any overdue loans.
     *
     * @param memberID the ID of the member to check
     * @return true if the member has overdue loans, false otherwise
     */

    public boolean checkMemberHasOverdueLoans(int memberID) throws EntityNotFoundException, DatabaseException {
        try {
            Member member = memberRepo.get(memberID);

            if (member == null) {
                throw new EntityNotFoundException("Member not found.");
            }

            List<Loan> memberLoans = member.getLoans();

            LocalDate today = LocalDate.now();
            for (Loan loan : memberLoans) {
                if (loan.getDueDate().isBefore(today)) {
                    return true;
                }
            }
            return false;
        } catch (DatabaseException e) {
            throw new DatabaseException("Error checking member has overdue loans.");
        }
    }

    /**
     * Returns a borrowed book for a member and checks if there are any reservations
     * for that book. If there are, it fulfills the reservation.
     *
     * @param loanID the ID of the loan to be returned
     */

    public void returnBook(int loanID) throws EntityNotFoundException, BusinessLogicException, DatabaseException {
        try {
            Loan loan = loanRepo.get(loanID);

            if (loan == null) {
                throw new EntityNotFoundException("Loan not found.");
            }

            Book book = loan.getBook();
            if (!"ACTIVE".equals(loan.getStatus()))
                throw new BusinessLogicException("Loan is not active and cannot be returned.");

            removeLoan(loan);
            nextReservation(book);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error returning book.");
        }
    }

    /**
     * Removes an active loan for a member and updates the attributes for the book
     *
     * @param loan the loan of the book
     */
    public void removeLoan(Loan loan) throws DatabaseException {
        try {
            loan.setStatus("RETURNED");
            loan.setReturnDate(LocalDate.now());
            Book book = loan.getBook();

            book.setCopiesAvailable(book.getCopiesAvailable() + 1);
            if (book.getCopiesAvailable() > 0) {
                book.setAvailable(true);
            }

            Member member = loan.getMember();
            member.getLoans().remove(loan);

            memberRepo.update(member);
            bookRepo.update(book);
            loanRepo.update(loan);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error removing loan.");
        }
    }

    /**
     * Retrieves the next reservation for a specific book and makes it a new loan if a reservation exists
     *
     * @param book the book in the reservation
     */
    public void nextReservation(Book book) throws DatabaseException {
        try {
            Optional<Reservation> nextReservation = reservationRepo.getAll().stream()
                    .filter(reservation -> reservation.getBook().equals(book))
                    .findFirst();

            if (nextReservation.isPresent()) {
                Reservation reservation = nextReservation.get();
                Member memberRes = reservation.getMember();

                createLoan(book, memberRes);
                memberRes.getReservations().remove(reservation);
                reservationRepo.delete(reservation.getID()); // Remove the reservation
                memberRepo.update(memberRes);
            }
        } catch (DatabaseException e) {
            throw new DatabaseException("Error making next reservation.");
        }
    }


    /**
     * Retrieves active loans for a specific member, sorted from oldest to newest.
     *
     * @param memberID the ID of the member
     * @return a list of active loans for the specified member, sorted by loan date
     */

    public List<Loan> getActiveLoansForMember(int memberID) throws EntityNotFoundException, DatabaseException {
        try {
            Member member = memberRepo.get(memberID);

            if (member == null) {
                throw new EntityNotFoundException("Member not found.");
            }

            return member.getLoans();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting active loans.");
        }
    }

    /**
     * Calculates the due date for a new loan, which is 14 days from the current date.
     *
     * @return the calculated due date
     */

    public LocalDate calculateDueDate() {
        // Default loan period is 14 days
        int loanPeriod = 14;
        return LocalDate.now().plusDays(loanPeriod);
    }

    /**
     * Adds a new book to the library.
     *
     * @param bookName the name of the book
     * @param authorID the ID of the author of the book
     * @param categoryID the ID of the category of the book
     * @param publisherID the ID of the publisher of the book
     * @param copiesAvailable the number of copies of the book
     */

    public void addBook(String bookName, int authorID, int categoryID, int publisherID, int copiesAvailable) throws EntityNotFoundException, DatabaseException {
        try {
            Author author = authorRepo.get(authorID);
            Category category = categoryRepo.get(categoryID);
            Publisher publisher = publisherRepo.get(publisherID);
            Book book = new Book(++newBookID, bookName, author, true, category, publisher, copiesAvailable);
            addBookToAuthor(book, authorID);
            addBookToCategory(book, categoryID);
            addBookToPublisher(book, publisherID);
            bookRepo.add(book);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error creating book.");
        }
    }

    /**
     * Adds a new staff member to the library.
     *
     * @param name the name of the staff member
     * @param email the email of the staff member
     * @param phoneNumber the phone number of the staff member
     * @param position the position of the staff member
     */

    public void addStaff(String name, String email, String phoneNumber, String position) throws DatabaseException {
        try {
            Staff staff = new Staff(++newStaffID, name, email, phoneNumber, position);
            staffRepo.add(staff);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error creating staff.");
        }
    }

    /**
     * Checks if a user with the given email is a staff member.
     *
     * @param email the email of the user
     * @return true if the user is a staff member, false otherwise
     */

    public boolean isStaff(String email) throws DatabaseException {
        try {
            List<Staff> staffs = staffRepo.getAll();
            for (Staff staff : staffs) {
                if (staff.getEmail().equals(email)) {
                    return true;
                }
            }
            return false;
        } catch (DatabaseException e) {
            throw new DatabaseException("Error verifying staff.");
        }
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

    public void updateBook(int bookID, String newBookName, int newAuthorID, boolean newIsAvailable, int newCategoryID, int newPublisherID, int newCopies) throws EntityNotFoundException, DatabaseException {
        try {
            Book book = bookRepo.get(bookID);

            if (book == null) {
                throw new EntityNotFoundException("Book not found.");
            }

            Author author = authorRepo.get(newAuthorID);

            if (author == null) {
                throw new EntityNotFoundException("Author not found.");
            }

            Category category = categoryRepo.get(newCategoryID);

            if (category == null) {
                throw new EntityNotFoundException("Category not found.");
            }

            Publisher publisher = publisherRepo.get(newPublisherID);

            if (publisher == null) {
                throw new EntityNotFoundException("Publisher not found.");
            }

            if (newBookName != null) {
                book.setBookName(newBookName);
            }
            book.setAuthor(author);
            book.setAvailable(newIsAvailable);
            book.setCategory(category);
            book.setPublisher(publisher);
            if (newCopies != -1) {
                book.setCopiesAvailable(newCopies);
            }
            bookRepo.update(book);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error updating book.");
        }
    }

    /**
     * Deletes a book from the library.
     *
     * @param bookID the ID of the book to be deleted
     */

    public void deleteBook(int bookID) throws EntityNotFoundException, DatabaseException {
        try {
            Book book = bookRepo.get(bookID);

            if (book == null) {
                throw new EntityNotFoundException("Book not found.");
            }

            bookRepo.delete(bookID);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error deleting book.");
        }
    }

    /**
     * Retrieves all books published by a specific publisher.
     *
     * @param publisherID the ID of the publisher
     * @return a list of books published by the specified publisher
     */

    public List<Book> getBooksByPublisher(int publisherID) throws EntityNotFoundException, DatabaseException {
        try {
            Publisher publisher = publisherRepo.get(publisherID);
            if (publisher == null) {
                throw new EntityNotFoundException("Publisher not found.");
            }
            return bookRepo.getAll().stream()
                    .filter(book -> book.getPublisher().getID() == publisherID)
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting books by publisher.");
        }
    }

    /**
     * Retrieves all books written by a specific author.
     *
     * @param authorID the ID of the author
     * @return a list of books written by the specified author
     */

    public List<Book> getBooksByAuthor(int authorID) throws EntityNotFoundException, DatabaseException {
        try {
            Author author = authorRepo.get(authorID);
            if (author == null) {
                throw new EntityNotFoundException("Author not found.");
            }
            return bookRepo.getAll().stream()
                    .filter(book -> book.getAuthor().getID() == authorID)
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting books by author.");
        }
    }

    /**
     * Retrieves all active reservations for a specific member.
     *
     * @param memberID the ID of the member
     * @return a list of active reservations for the specified member
     */

    public List<Reservation> getActiveReservationsForMember(int memberID) throws EntityNotFoundException, DatabaseException {
        try {
            Member member = memberRepo.get(memberID);

            if (member == null) {
                throw new EntityNotFoundException("Member not found.");
            }

            return member.getReservations();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting active reservations.");
        }
    }

    /**
     * Retrieves the loan history for a specific member.
     *
     * @param memberID the ID of the member
     * @return a list of loans that the specified member has previously borrowed
     */

    public List<Loan> getLoanHistoryForMember(int memberID) throws EntityNotFoundException, DatabaseException {
        try {
            Member member = memberRepo.get(memberID);

            if (member == null) {
                throw new EntityNotFoundException("Member not found.");
            }

            return member.getLoanHistory();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting loan history.");
        }
    }

    /**
     * Adds a book to a specific category.
     *
     * @param book the ID of the book to be added to the category
     * @param categoryID the ID of the category to which the book will be added
     */

    public void addBookToCategory(Book book, int categoryID) throws EntityNotFoundException, DatabaseException {
        try {
            //Book book = bookRepo.get(bookID);
            Category category = categoryRepo.get(categoryID);

            if (book == null) {
                throw new EntityNotFoundException("Book not found.");
            }
            if (category == null) {
                throw new EntityNotFoundException("Category not found.");
            }

            book.setCategory(category);
            category.getBooks().add(book);
            bookRepo.update(book);
            categoryRepo.update(category);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding book to category.");
        }
    }

    public void addBookToAuthor(Book book, int authorID) throws EntityNotFoundException, DatabaseException {
        try {
            //Book book = bookRepo.get(bookID);
            Author author = authorRepo.get(authorID);

            if (book == null) {
                throw new EntityNotFoundException("Book not found.");
            }
            if (author == null) {
                throw new EntityNotFoundException("Author not found.");
            }

            book.setAuthor(author);
            author.getBooks().add(book);

            bookRepo.update(book);
            authorRepo.update(author);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding book to author.");
        }
    }

    public void addBookToPublisher(Book book, int publisherID) throws EntityNotFoundException, DatabaseException {
        try {
            //Book book = bookRepo.get(bookID);
            Publisher publisher = publisherRepo.get(publisherID);

            if (book == null) {
                throw new EntityNotFoundException("Book not found.");
            }
            if (publisher == null) {
                throw new EntityNotFoundException("Publisher not found.");
            }

            book.setPublisher(publisher);
            publisher.getPublishedBooks().add(book);

            bookRepo.update(book);
            publisherRepo.update(publisher);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding book to publisher.");
        }
    }


    /**
     * Retrieves all books that belong to a specific category.
     *
     * @param categoryID the ID of the category
     * @return a list of books in the specified category
     */

    public List<Book> getAllBooksInCategory(int categoryID) throws EntityNotFoundException, DatabaseException {
        try {
            Category category = categoryRepo.get(categoryID);

            if (category == null) {
                throw new EntityNotFoundException("Category not found.");
            }

            return bookRepo.getAll().stream()
                    .filter(book -> book.getCategory().getID() == categoryID)
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all books in category.");
        }
    }

    /**
     * Retrieves all publishers in the library.
     *
     * @return a list of all publishers
     */

    public List<Publisher> getAllPublishers() throws DatabaseException {
        try {
            return publisherRepo.getAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all publishers.");
        }
    }

    /**
     * Retrieves all authors in the library.
     *
     * @return a list of all authors
     */

    public List<Author> getAllAuthors() throws DatabaseException {
        try {
            return authorRepo.getAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all authors.");
        }
    }

    /**
     * Retrieves all categories in the library.
     *
     * @return a list of all categories
     */

    public List<Category> getAllCategories() throws DatabaseException {
        try {
            return categoryRepo.getAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all categories.");
        }
    }

    /**
     * Retrieves all reservations in the library.
     *
     * @return a list of all reservations
     */

    public List<Reservation> getAllReservations() throws DatabaseException {
        try {
            return reservationRepo.getAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all reservations.");
        }
    }

    /**
     * Retrieves all loans in the library.
     *
     * @return a list of all loans
     */

    public List<Loan> getAllLoans() throws DatabaseException {
        try {
            return loanRepo.getAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all loans.");
        }
    }

    /**
     * Retrieves all members in the library.
     *
     * @return a list of all members
     */

    public List<Member> getAllMembers() throws DatabaseException {
        try {
            return memberRepo.getAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all members.");
        }
    }

    /**
     * Retrieves all reviews in the library.
     *
     * @return a list of all reviews
     */

    public List<Review> getAllReviews() throws DatabaseException {
        try {
            return reviewRepo.getAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all reviews.");
        }
    }

    /**
     * Retrieves all books in the library.
     *
     * @return a list of all books
     */

    public List<Book> getAllBooks() throws DatabaseException {
        try {
            return bookRepo.getAll();
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all books.");
        }
    }

    /**
     * Adds a new member to the library.
     *
     * @param name the name of the member
     * @param email the email of the member
     * @param phoneNumber the phone number of the member
     */

    public void addMember(String name, String email, String phoneNumber) throws DatabaseException {
        try {
            Member member = new Member(++newMemberID, name, email, phoneNumber);
            memberRepo.add(member);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding member.");
        }
    }

    /**
     * Adds a new author to the library.
     * <p>
     * This method creates a new Author instance with the provided name, email, and phone number.
     * It initializes an empty list of books associated with the author.
     *
     * @param name the name of the author
     * @param email the email of the author
     * @param phoneNumber the phone number of the author
     */

    public void addAuthor(String name, String email, String phoneNumber) throws DatabaseException {
        try {
            Author author = new Author(++newAuthorID, name, email, phoneNumber);
            authorRepo.add(author);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding author.");
        }
    }

    /**
     * Adds a new publisher to the library.
     * <p>
     * This method creates a new Publisher instance with the provided name, email, and phone number.
     * It initializes an empty list of published books associated with the publisher.
     *
     * @param name the name of the publisher
     * @param email the email of the publisher
     * @param phoneNumber the phone number of the publisher
     */

    public void addPublisher(String name, String email, String phoneNumber) throws DatabaseException {
        try {
            Publisher publisher = new Publisher(++newPublisherID, name, email, phoneNumber);
            publisherRepo.add(publisher);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding publisher.");
        }
    }

    /**
     * Gets the ID of a member or a staff by the email that is provided.
     *
     * @param email the email of the person
     * @return the ID of the person or -1 if they don't exist
     */

    public int getIDbyEmail(String email) throws EntityNotFoundException, DatabaseException {
        try {
            List<Member> members = memberRepo.getAll();
            List<Staff> staffs = staffRepo.getAll();

            for (Member member : members) {
                if (member.getEmail().equals(email)) {
                    return member.getID();
                }
            }
            for (Staff staff : staffs) {
                if (staff.getEmail().equals(email)) {
                    return staff.getID();
                }
            }

            throw new EntityNotFoundException("No entity found with the provided email.");
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting ID by email.");
        }
    }

    /**
     * Searches for books in the library by their title.
     * If the search term is empty, retrieves all books sorted by their title.
     *
     * @param title the title or part of the title of the book to search for
     * @return a list of Book objects whose titles contain the specified search term;
     *         returns a sorted list of all books if the search term is empty
     */

    public List<Book> searchBook(String title) throws DatabaseException {
        try {
            if (title == null || title.trim().isEmpty()) {
                return getAllBooksSortedByTitle();
            } else {
                return bookRepo.getAll().stream()
                        .filter(book -> book.getBookName().toLowerCase().contains(title.toLowerCase()))
                        .collect(Collectors.toList());
            }
        } catch (DatabaseException e) {
            throw new DatabaseException("Error searching book.");
        }
    }

    /**
     * Retrieves all books in the library sorted by their title.
     *
     * @return a list of all books sorted by title
     */

    public List<Book> getAllBooksSortedByTitle() throws DatabaseException {
        try {
            return bookRepo.getAll().stream()
                    .sorted(Comparator.comparing(Book::getBookName))
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error getting all books sorted by title.");
        }
    }

    /**
     * Recommends books for a member based on the categories of the books he has previously borrowed
     *
     * @param memberID the ID of the member
     * @return a list of all recommended books for that member
     */
    public List<Book> recommendBooksForMember(int memberID) throws EntityNotFoundException, DatabaseException {
        try {
            Member member = memberRepo.get(memberID);

            if (member == null) {
                throw new EntityNotFoundException("Member not found.");
            }

            Set<Category> borrowedCategories = member.getLoanHistory().stream()
                    .map(Loan::getBook)
                    .map(Book::getCategory)
                    .collect(Collectors.toSet());

            if (borrowedCategories.isEmpty()) {
                return Collections.emptyList();
            }

            return bookRepo.getAll().stream()
                    .filter(book -> borrowedCategories.contains(book.getCategory()))
                    .filter(book -> book.getCopiesAvailable() > 0)
                    .filter(book -> member.getLoanHistory().stream()
                            .noneMatch(loan -> loan.getBook().getID() == book.getID()))
                    .collect(Collectors.toList());
        } catch (DatabaseException e) {
            throw new DatabaseException("Error recommending books for member");
        }
    }

    /**
     * Sorts the books by their average rating
     *
     * @return list of sorted books
     */
    public List<Book> sortBooksByAvgRating() throws DatabaseException {
        try {
            List<Book> books = new ArrayList<>(bookRepo.getAll());
            books.sort((b1, b2) -> Double.compare(calculateAverageRating(b2), calculateAverageRating(b1)));
            return books;
        } catch (DatabaseException e) {
            throw new DatabaseException("Error sorting books by average rating.");
        }
    }

    /**
     * Calculates the average rating for a specific book
     * @param book the book
     * @return the average rating of the book
     */
    public double calculateAverageRating(Book book) {
        List<Review> reviews = book.getReviews();
        if(reviews.isEmpty()) {
            return 0.0;
        }
        double totalRating = reviews.stream().mapToDouble(Review::getRating).sum();
        return totalRating / reviews.size();
    }

    /**
     * Adds a category to the repository
     *
     * @param name the name of the category
     * @param description the description of the category
     * @throws DatabaseException throws DatabaseException
     */
    public void addCategory(String name, String description) throws DatabaseException {
        try {
            Category category = new Category(++newCategoryID, name, description);
            categoryRepo.add(category);
        } catch (DatabaseException e) {
            throw new DatabaseException("Error adding category.");
        }
    }

}
