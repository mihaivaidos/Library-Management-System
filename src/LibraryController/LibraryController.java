package LibraryController;

import LibraryModel.*;
import LibraryService.LibraryService;

import java.util.List;

/**
 * Controller class for managing library operations, including adding, updating, and deleting books,
 * managing reviews, loans, reservations, and other library-related functionalities.
 */

public class LibraryController {
    private final LibraryService libraryService;

    /**
     * Constructs a LibraryController with the specified LibraryService.
     *
     * @param libraryService the LibraryService used for performing library operations
     */

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    /**
     * Adds a review to a specified book.
     *
     * @param memberID the ID of the member adding the review
     * @param bookID the ID of the book being reviewed
     * @param rating the rating given to the book
     * @param comments the comments accompanying the review
     */

    public void addReviewToBook(int memberID, int bookID, int rating, String comments) {
        libraryService.addReviewToBook(memberID, bookID, rating, comments);
        System.out.println("Review added successfully!");
    }

    /**
     * Deletes a review from a book by its review ID.
     *
     * @param reviewID the ID of the review to be deleted
     */

    public void deleteReviewFromBook(int reviewID) {
        libraryService.deleteReviewFromBook(reviewID);
        System.out.println("Review deleted successfully!");
    }

    /**
     * Displays all reviews for a specified book.
     *
     * @param bookID the ID of the book for which reviews are to be displayed
     * @return
     */

    public List<Review> viewAllReviewsOfBook(int bookID) {
        List<Review> reviews = libraryService.getAllReviewsOfBook(bookID);
        System.out.println("Reviews for book: " + bookID);
        for (Review review : reviews) {
            System.out.println("ID: " + review.getID() + ", Book: " + review.getBook().getBookName() + ", Book author: " + review.getBook().getAuthor().getName()
                    + ", Rating: " + review.getRating() + ", Comments: " + review.getComments() + ", Member name: " + review.getMember().getName());
        }
        return reviews;
    }

    /**
     * Allows a member to borrow or reserve a specified book.
     *
     * @param memberID the ID of the member borrowing the book
     * @param bookID the ID of the book to be borrowed
     */

    public void borrowBook(int memberID, int bookID) {
        libraryService.borrowBook(memberID, bookID);
        System.out.println("Book borrowed or reserved successfully for member ID: " + memberID);
    }

    /**
     * Returns a borrowed book by its loan ID.
     *
     * @param loanID the ID of the loan associated with the book being returned
     */

    public void returnBook(int loanID) {
        libraryService.returnBook(loanID);
        System.out.println("Book returned successfully with Loan ID: " + loanID);
    }

    /**
     * Displays all active loans for a specified member.
     *
     * @param memberID the ID of the member whose active loans are to be displayed
     */

    public void viewActiveLoans(int memberID) {
        List<Loan> loans = libraryService.getActiveLoansForMember(memberID);
        System.out.println("Active loans for member ID: " + memberID);
        for(Loan loan : loans) {
            System.out.println("ID: " + loan.getID() + ", Loan date: " + loan.getLoanDate() + ", Due date: "
                    + loan.getDueDate() + ", Return date: " + loan.getReturnDate() + ", Status: " + loan.getStatus()
                    + ", Book: " + loan.getBook().getBookName() + ", Book author: " + loan.getBook().getAuthor().getName());
        }
    }

    /**
     * Adds a book to a specified category.
     *
     * @param bookID the ID of the book to be added to the category
     * @param categoryID the ID of the category to which the book is to be added
     */

    public void addBookToCategory(int bookID, int categoryID) {
        libraryService.addBookToCategory(bookID, categoryID);
        System.out.println("Book added to category successfully.");
    }

    /**
     * Displays all books in a specified category.
     *
     * @param categoryID the ID of the category whose books are to be displayed
     */

    public void viewAllBooksInCategory(int categoryID) {
        System.out.println("Available Books in category: " + categoryID);
        List<Book> books = libraryService.getAllBooksInCategory(categoryID);
        printBooks(books);
    }

    /**
     * Displays all categories available in the library.
     */

    public void viewAllCategories() {
        System.out.println("Categories:");
        List<Category> categories = libraryService.getAllCategories();
        for (Category category : categories) {
            System.out.println("ID: " + category.getID() + ", Name: " + category.getCategoryName()
                    + ", Description: " + category.getDescription());
        }
    }

    /**
     * Displays all books available in the library.
     */

    public void viewAllBooks() {
        System.out.println("Available Books:");
        List<Book> books = libraryService.getAllBooks();
        printBooks(books);
    }

    public void viewMemberBorrowedBooks(int memberID) {
        System.out.println("Member borrowed books: " + memberID);
        List<Book> books = libraryService.getMemberBorrowedBooks(memberID);
        printBooks(books);
    }

    /**
     * Displays all publishers registered in the library system.
     */

    public void viewAllPublishers() {
        System.out.println("Publishers:");
        List<Publisher> publishers = libraryService.getAllPublishers();
        for (Publisher publisher : publishers) {
            System.out.println("ID: " + publisher.getID() + ", Name: " + publisher.getName()
                    + ", Email: " + publisher.getEmail() + ", Phone: " + publisher.getPhoneNumber());
        }
    }

    /**
     * Displays all authors registered in the library system.
     */

    public void viewAllAuthors() {
        System.out.println("Authors:");
        List<Author> authors = libraryService.getAllAuthors();
        for (Author author : authors) {
            System.out.println("ID: " + author.getID() + ", Name: " + author.getName()
                    + ", Email: " + author.getEmail() + ", Phone: " + author.getPhoneNumber());
        }
    }

    /**
     * Displays all books published by a specified publisher.
     *
     * @param publisherID the ID of the publisher whose books are to be displayed
     */

    public void viewBooksByPublisher(int publisherID) {
        System.out.println("Books by publisher: " + publisherID);
        List<Book> publisherBooks = libraryService.getBooksByPublisher(publisherID);
        printBooks(publisherBooks);
    }

    public void printBooks(List<Book> books) {
        for (Book book : books) {
            String status = book.isAvailable() ? "Available" : "Borrowed";
            String categoryName = (book.getCategory() != null) ? book.getCategory().getCategoryName() : "No Category";
            String publisherName = (book.getPublisher() != null) ? book.getPublisher().getName() : "No Publisher";
            System.out.println("ID: " + book.getID() + ", Title: " + book.getBookName() + ", Author: " + book.getAuthor().getName()
                    + ", Publisher: " + publisherName + ", Category: " + categoryName + ", Status: " + status);
        }
    }

    /**
     * Displays all books written by a specified author.
     *
     * @param authorID the ID of the author whose books are to be displayed
     */

    public void viewBooksByAuthor(int authorID) {
        System.out.println("Books by author: " + authorID);
        List<Book> books = libraryService.getBooksByAuthor(authorID);
        printBooks(books);
    }

    /**
     * Displays all active reservations for a specified member.
     *
     * @param memberID the ID of the member whose active reservations are to be displayed
     */

    public void viewActiveReservations(int memberID) {
        List<Reservation> reservations = libraryService.getActiveReservationsForMember(memberID);
        System.out.println("Active reservations for member ID: " + memberID);
        for(Reservation reservation : reservations) {
            System.out.println("ID: " + reservation.getID() + ", Date: " + reservation.getReservationDate()
                    + ", Book: " + reservation.getBook().getBookName() + ", Author: " + reservation.getBook().getAuthor().getName());
        }
    }

    /**
     * Displays the loan history for a specified member.
     *
     * @param memberID the ID of the member whose loan history is to be displayed
     */

    public void viewLoanHistoryForMember(int memberID) {
        List<Loan> loans = libraryService.getLoanHistoryForMember(memberID);
        System.out.println("Loan History for member ID: " + memberID);
        for(Loan loan : loans) {
            System.out.println("ID: " + loan.getID() + ", Loan date: " + loan.getLoanDate() + ", Due date: "
                    + loan.getDueDate() + ", Return date: " + loan.getReturnDate() + ", Status: " + loan.getStatus()
                    + ", Book: " + loan.getBook().getBookName() + ", Book author: " + loan.getBook().getAuthor().getName());
        }
    }

    /**
     * Adds a new book to the library with the specified details.
     *
     * @param title the title of the book to be added
     * @param authorID the ID of the author of the book
     * @param categoryID the ID of the category to which the book belongs
     * @param publisherID the ID of the publisher of the book
     */

    public void addBook(String title, int authorID, int categoryID, int publisherID) {
        libraryService.addBook(title, authorID, categoryID, publisherID);
        System.out.println("Book added successfully: " + title);
    }

    /**
     * Updates the details of an existing book.
     *
     * @param bookID the ID of the book to be updated
     * @param newBookName the new title of the book
     * @param newAuthorID the new ID of the author of the book
     * @param newIsAvailable the new availability status of the book
     * @param newCategoryID the new ID of the category to which the book belongs
     * @param newPublisherID the new ID of the publisher of the book
     */

    public void updateBook(int bookID, String newBookName, int newAuthorID, boolean newIsAvailable, int newCategoryID, int newPublisherID) {
        libraryService.updateBook(bookID, newBookName, newAuthorID, newIsAvailable, newCategoryID, newPublisherID);
        System.out.println("Book updated successfully: " + newBookName);
    }

    /**
     * Deletes a book from the library by its ID.
     *
     * @param bookID the ID of the book to be deleted
     */

    public void deleteBook(int bookID) {
        libraryService.deleteBook(bookID);
        System.out.println("Book deleted successfully: " + bookID);
    }

    /**
     * Adds a new staff member to the library.
     *
     * @param name the name of the staff member
     * @param email the email of the staff member
     * @param phoneNumber the phone number of the staff member
     * @param position the position of the staff member in the library
     */

    public void addStaff(String name, String email, String phoneNumber, String position) {
        libraryService.addStaff(name, email, phoneNumber, position);
        System.out.println("Staff added successfully: " + name);
    }

    /**
     * Checks if the provided email belongs to a staff member.
     *
     * @param email the email to check
     * @return true if the email belongs to a staff member, false otherwise
     */

    public boolean isStaff(String email) {
        if(libraryService.isStaff(email)) {
            System.out.println("You are a staff!");
            return true;
        } else {
            System.out.println("You are not a staff!");
            return false;
        }
    }

    /**
     * Adds a new member to the library.
     *
     * @param name the name of the member
     * @param email the email of the member
     * @param phoneNumber the phone number of the member
     */

    public void addMember(String name, String email, String phoneNumber) {
        libraryService.addMember(name, email, phoneNumber);
        System.out.println("Member added successfully: " + name);
    }

    /**
     * Adds a new author to the library.
     *
     * @param name the name of the author
     * @param email the email of the author
     * @param phoneNumber the phone number of the author
     */

    public void addAuthor(String name, String email, String phoneNumber) {
        libraryService.addAuthor(name, email, phoneNumber);
        System.out.println("Author added successfully: " + name);
    }

    /**
     * Adds a new publisher to the library.
     *
     * @param name the name of the publisher
     * @param email the email of the publisher
     * @param phoneNumber the phone number of the publisher
     */

    public void addPublisher(String name, String email, String phoneNumber) {
        libraryService.addPublisher(name, email, phoneNumber);
        System.out.println("Publisher added successfully: " + name);
    }
}
