package LibraryController;

import Exceptions.BusinessLogicException;
import Exceptions.DatabaseException;
import Exceptions.EntityNotFoundException;
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
        try {
            libraryService.addReviewToBook(memberID, bookID, rating, comments);
            System.out.println("Review added successfully!");
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.err.println("Business logic error: " + e.getMessage());
        }
    }

    /**
     * Deletes a review from a book by its review ID.
     *
     * @param reviewID the ID of the review to be deleted
     */

    public void deleteReviewFromBook(int reviewID) {
        try {
            libraryService.deleteReviewFromBook(reviewID);
            System.out.println("Review deleted successfully!");
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all reviews for a specified book.
     *
     * @param bookID the ID of the book for which reviews are to be displayed
     */

    public void viewAllReviewsOfBook(int bookID) {
        try {
            List<Review> reviews = libraryService.getAllReviewsOfBook(bookID);
            System.out.println("Reviews for book: " + bookID);
            for (Review review : reviews) {
                System.out.println("ID: " + review.getID() + ", Book: " + review.getBook().getBookName() + ", Book author: " + review.getBook().getAuthor().getName()
                        + ", Rating: " + review.getRating() + ", Comments: " + review.getComments() + ", Member name: " + review.getMember().getName());
            }
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        }
    }

    public void viewAllReviews() {
        try {
            System.out.println("Reviews: ");
            List<Review> reviews = libraryService.getAllReviews();
            for (Review review : reviews) {
                System.out.println("ID: " + review.getID() + ", Book: " + review.getBook().getBookName() + ", Book author: " + review.getBook().getAuthor().getName()
                        + ", Rating: " + review.getRating() + ", Comments: " + review.getComments() + ", Member name: " + review.getMember().getName());
            }
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Allows a member to borrow or reserve a specified book.
     *
     * @param memberID the ID of the member borrowing the book
     * @param bookID the ID of the book to be borrowed
     */

    public void borrowBook(int memberID, int bookID) {
        try {
            libraryService.borrowBook(memberID, bookID);
            System.out.println("Book borrowed or reserved successfully for member ID: " + memberID);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.err.println("Business logical error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Returns a borrowed book by its loan ID.
     *
     * @param loanID the ID of the loan associated with the book being returned
     */

    public void returnBook(int loanID) {
        try {
            libraryService.returnBook(loanID);
            System.out.println("Book returned successfully with Loan ID: " + loanID);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.err.println("Business logical error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all active loans for a specified member, sorted from oldest to newest.
     *
     * @param memberID the ID of the member whose active loans are to be displayed
     */

    public void viewActiveLoans(int memberID) {
        try {
            List<Loan> loans = libraryService.getActiveLoansForMember(memberID);
            System.out.println("Active loans for member ID: " + memberID);

            if (loans.isEmpty()) {
                System.out.println("No active loans found for this member.");
            } else {
                for (Loan loan : loans) {
                    System.out.println("ID: " + loan.getID() +
                            ", Loan date: " + loan.getLoanDate() +
                            ", Due date: " + loan.getDueDate() +
                            ", Return date: " + loan.getReturnDate() +
                            ", Status: " + loan.getStatus() +
                            ", Book: " + loan.getBook().getBookName() +
                            ", Book author: " + loan.getBook().getAuthor().getName());
                }
            }
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (BusinessLogicException e) {
            System.err.println("Business logic error: " + e.getMessage());
        }
    }

    /**
     * Adds a book to a specified category.
     *
     * @param bookID the ID of the book to be added to the category
     * @param categoryID the ID of the category to which the book is to be added
     */

    public void addBookToCategory(int bookID, int categoryID) {
        try {
            Book book = libraryService.getAllBooks().get(bookID);
            libraryService.addBookToCategory(book, categoryID);
            System.out.println("Book added to category successfully.");
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all books in a specified category.
     *
     * @param categoryID the ID of the category whose books are to be displayed
     */

    public void viewAllBooksInCategory(int categoryID) {
        try {
            System.out.println("Available Books in category: " + categoryID);
            List<Book> books = libraryService.getAllBooksInCategory(categoryID);
            printBooks(books);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all categories available in the library.
     */

    public void viewAllCategories() {
        try {
            System.out.println("Categories:");
            List<Category> categories = libraryService.getAllCategories();
            for (Category category : categories) {
                System.out.println("ID: " + category.getID() + ", Name: " + category.getCategoryName()
                        + ", Description: " + category.getDescription());
            }
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all books available in the library.
     */

    public void viewAllBooks() {
        try {
            System.out.println("Available Books:");
            List<Book> books = libraryService.getAllBooks();
            printBooks(books);
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Prints the books borrowed by a member
     *
     * @param memberID the ID of the member
     */
    public void viewMemberBorrowedBooks(int memberID) {
        try {
            System.out.println("Member borrowed books: " + memberID);
            List<Book> books = libraryService.getMemberBorrowedBooks(memberID);
            printBooks(books);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all publishers registered in the library system.
     */

    public void viewAllPublishers() {
        try {
            System.out.println("Publishers:");
            List<Publisher> publishers = libraryService.getAllPublishers();
            for (Publisher publisher : publishers) {
                System.out.println("ID: " + publisher.getID() + ", Name: " + publisher.getName()
                        + ", Email: " + publisher.getEmail() + ", Phone: " + publisher.getPhoneNumber());
            }
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all authors registered in the library system.
     */

    public void viewAllAuthors() {
        try {
            System.out.println("Authors:");
            List<Author> authors = libraryService.getAllAuthors();
            for (Author author : authors) {
                System.out.println("ID: " + author.getID() + ", Name: " + author.getName()
                        + ", Email: " + author.getEmail() + ", Phone: " + author.getPhoneNumber());
            }
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all books published by a specified publisher.
     *
     * @param publisherID the ID of the publisher whose books are to be displayed
     */

    public void viewBooksByPublisher(int publisherID) {
        try {
            System.out.println("Books by publisher: " + publisherID);
            List<Book> publisherBooks = libraryService.getBooksByPublisher(publisherID);
            printBooks(publisherBooks);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all books written by a specified author.
     *
     * @param authorID the ID of the author whose books are to be displayed
     */

    public void viewBooksByAuthor(int authorID) {
        try {
            System.out.println("Books by author: " + authorID);
            List<Book> books = libraryService.getBooksByAuthor(authorID);
            printBooks(books);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays all active reservations for a specified member.
     *
     * @param memberID the ID of the member whose active reservations are to be displayed
     */

    public void viewActiveReservations(int memberID) {
        try {
            List<Reservation> reservations = libraryService.getActiveReservationsForMember(memberID);
            System.out.println("Active reservations for member ID: " + memberID);
            for (Reservation reservation : reservations) {
                System.out.println("ID: " + reservation.getID() + ", Date: " + reservation.getReservationDate()
                        + ", Book: " + reservation.getBook().getBookName() + ", Author: " + reservation.getBook().getAuthor().getName());
            }
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Displays the loan history for a specified member.
     *
     * @param memberID the ID of the member whose loan history is to be displayed
     */

    public void viewLoanHistoryForMember(int memberID) {
        try {
            List<Loan> loans = libraryService.getLoanHistoryForMember(memberID);
            System.out.println("Loan History for member ID: " + memberID);
            for (Loan loan : loans) {
                System.out.println("ID: " + loan.getID() + ", Loan date: " + loan.getLoanDate() + ", Due date: "
                        + loan.getDueDate() + ", Return date: " + loan.getReturnDate() + ", Status: " + loan.getStatus()
                        + ", Book: " + loan.getBook().getBookName() + ", Book author: " + loan.getBook().getAuthor().getName());
            }
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Adds a new book to the library with the specified details.
     *
     * @param title the title of the book to be added
     * @param authorID the ID of the author of the book
     * @param categoryID the ID of the category to which the book belongs
     * @param publisherID the ID of the publisher of the book
     * @param copies the number of copies of the book
     */

    public void addBook(String title, int authorID, int categoryID, int publisherID, int copies) {
        try {
            libraryService.addBook(title, authorID, categoryID, publisherID, copies);
            System.out.println("Book added successfully: " + title);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }

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
     * @param newCopies the new number of copies of the book
     */

    public void updateBook(int bookID, String newBookName, int newAuthorID, boolean newIsAvailable, int newCategoryID, int newPublisherID, int newCopies) {
        try {
            libraryService.updateBook(bookID, newBookName, newAuthorID, newIsAvailable, newCategoryID, newPublisherID, newCopies);
            System.out.println("Book updated successfully: " + newBookName);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Deletes a book from the library by its ID.
     *
     * @param bookID the ID of the book to be deleted
     */

    public void deleteBook(int bookID) {
        try {
            libraryService.deleteBook(bookID);
            System.out.println("Book deleted successfully: " + bookID);
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
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
        try {
            libraryService.addStaff(name, email, phoneNumber, position);
            System.out.println("Staff added successfully: " + name);
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Checks if the provided email belongs to a staff member.
     *
     * @param email the email to check
     * @return true if the email belongs to a staff member, false otherwise
     */

    public boolean isStaff(String email) {
        try {
            if (libraryService.isStaff(email)) {
                System.out.println("You are a staff!");
                return true;
            } else {
                System.out.println("You are not a staff!");
                return false;
            }
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return false;
    }

    /**
     * Adds a new member to the library.
     *
     * @param name the name of the member
     * @param email the email of the member
     * @param phoneNumber the phone number of the member
     */

    public void addMember(String name, String email, String phoneNumber) {
        try {
            libraryService.addMember(name, email, phoneNumber);
            System.out.println("Member added successfully: " + name);
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Adds a new author to the library.
     *
     * @param name the name of the author
     * @param email the email of the author
     * @param phoneNumber the phone number of the author
     */

    public void addAuthor(String name, String email, String phoneNumber) {
        try {
            libraryService.addAuthor(name, email, phoneNumber);
            System.out.println("Author added successfully: " + name);
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Adds a new publisher to the library.
     *
     * @param name the name of the publisher
     * @param email the email of the publisher
     * @param phoneNumber the phone number of the publisher
     */

    public void addPublisher(String name, String email, String phoneNumber) {
        try {
            libraryService.addPublisher(name, email, phoneNumber);
            System.out.println("Publisher added successfully: " + name);
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Gets the ID of a person (member or staff)
     *
     * @param email the email of the staff or member
     * @return the ID or -1
     */
    public int getIDbyEmail(String email)  {
        try {
            return libraryService.getIDbyEmail(email);
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        }
        return -1;
    }

    /**
     * Searches for books in the library by their title.
     * If the search term is empty, retrieves all books sorted by title.
     *
     * @param title the title or part of the title of the book to search for;
     *              if null or empty, all books sorted by title will be displayed
     * @return list of the books found
     */

    public List<Book> searchBook(String title) {
        try {
            System.out.println("Searching for books with title containing: " + (title != null ? title : "No search term provided"));
            List<Book> books = libraryService.searchBook(title);
            printBooks(books);
            return books;
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
        return List.of();
    }

    /**
     * Displays all books available in the library sorted by title.
     */

    public void viewAllBooksSortedByTitle() {
        try {
            System.out.println("Available Books (Sorted by Title):");
            List<Book> books = libraryService.getAllBooksSortedByTitle();
            printBooks(books);
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Prints the books in a list.
     *
     * @param books the list of books that needs to be printed
     */

    public void printBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("No books found.");
        }
        else {
            for (Book book : books) {
                String status = book.isAvailable() ? "Available" : "Borrowed";
                String categoryName = (book.getCategory() != null) ? book.getCategory().getCategoryName() : "No Category";
                String publisherName = (book.getPublisher() != null) ? book.getPublisher().getName() : "No Publisher";
                double averageRating = libraryService.calculateAverageRating(book);
                System.out.println("ID: " + book.getID() + ", Title: " + book.getBookName() + ", Author: " + book.getAuthor().getName()
                        + ", Publisher: " + publisherName + ", Category: " + categoryName + ", Status: " + status + ", Copies available: " + book.getCopiesAvailable()
                        +  ", Rating: " + averageRating);
            }
        }
    }

    /**
     * Prints the recommended books for a member
     *
     * @param memberID the ID of the member that wants to see the recommendations
     */
    public void recommendBooksForMember(int memberID) {
        try {
            List<Book> recommendedBooks = libraryService.recommendBooksForMember(memberID);
            if (recommendedBooks.isEmpty()) {
                System.out.println("No recommendations available. The member has not borrowed any books yet.");
            } else {
                System.out.println("Recommended books for " + memberID + ":");
                printBooks(recommendedBooks);
            }
        } catch (EntityNotFoundException e) {
            System.err.println("Entity not found error: " + e.getMessage());
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    /**
     * Prints the books sorted by their rating
     */
    public void sortBooksByAvgRating() {
        try {
            List<Book> sortedBooks = libraryService.sortBooksByAvgRating();
            System.out.println("Books sorted by average rating:");
            printBooks(sortedBooks);
        } catch (DatabaseException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

}
