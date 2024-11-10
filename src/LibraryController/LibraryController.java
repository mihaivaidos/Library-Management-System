package LibraryController;

import LibraryModel.*;
import LibraryService.LibraryService;

import java.util.List;

public class LibraryController {
    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    public void addReviewToBook(int memberID, int bookID, int rating, String comments) {
        libraryService.addReviewToBook(memberID, bookID, rating, comments);
        System.out.println("Review added successfully!");
    }

    public void viewAllReviewsOfBook(int bookID) {
        List<Review> reviews = libraryService.getAllReviewsOfBook(bookID);
        System.out.println("Reviews for book: " + bookID);
        for (Review review : reviews) {
            System.out.println("Book: " + review.getBook().getBookName() + ", Book author: " + review.getBook().getAuthor().getName()
                    + ", Rating: " + review.getRating() + ", Comments: " + review.getComments());
        }
    }

    public void borrowBook(int memberID, int bookID) {
        libraryService.borrowBook(memberID, bookID);
        System.out.println("Book borrowed or reserved successfully for member ID: " + memberID);
    }

    public void returnBook(int loanID) {
        libraryService.returnBook(loanID);
        System.out.println("Book returned successfully with Loan ID: " + loanID);
    }

    public void viewActiveLoans(int memberID) {
        List<Loan> loans = libraryService.getActiveLoansForMember(memberID);
        System.out.println("Active loans for member ID: " + memberID);
        for(Loan loan : loans) {
            System.out.println("ID: " + loan.getID() + ", Loan date: " + loan.getLoanDate() + ", Due date: "
                    + loan.getDueDate() + ", Return date: " + loan.getReturnDate() + ", Status: " + loan.getStatus()
                    + ", Book: " + loan.getBook().getBookName() + ", Book author: " + loan.getBook().getAuthor().getName());
        }
    }

    public void addBookToCategory(int bookID, int categoryID) {
        libraryService.addBookToCategory(bookID, categoryID);
        System.out.println("Book added to category successfully.");
    }

    public void viewAllBooksInCategory(int categoryID) {
        System.out.println("Available Books in category: " + categoryID);
        for (Book book : libraryService.getAllBooksInCategory(categoryID)) {
            String status = book.isAvailable() ? "Available" : "Borrowed";
            System.out.println("ID: " + book.getID() + ", Title: " + book.getBookName() + ", Author: " + book.getAuthor().getName()
                    + ", Status: " + status + ", Publisher: " + book.getPublisher().getName() + ", Category: " + book.getCategory().getCategoryName());
        }
    }

    public void viewAllCategories() {
        System.out.println("Categories:");
        List<Category> categories = libraryService.getAllCategories();
        for (Category category : categories) {
            System.out.println("ID: " + category.getID() + ", Name: " + category.getCategoryName()
                    + ", Description: " + category.getDescription());
        }
    }

    public void viewAllBooks() {
        System.out.println("Available Books:");
        for (Book book : libraryService.getAllBooks()) {
            String status = book.isAvailable() ? "Available" : "Borrowed";
            System.out.println("ID: " + book.getID() + ", Title: " + book.getBookName() + ", Author: " + book.getAuthor().getName()
                    + ", Publisher: " + book.getPublisher().getName() + ", Category: " + book.getCategory().getCategoryName() + ", Status: " + status);
        }
    }

    public void viewAllPublishers() {
        System.out.println("Publishers:");
        List<Publisher> publishers = libraryService.getAllPublishers();
        for (Publisher publisher : publishers) {
            System.out.println("ID: " + publisher.getID() + ", Name: " + publisher.getName()
                    + ", Email: " + publisher.getEmail() + ", Phone: " + publisher.getPhoneNumber());
        }
    }

    public void viewAllAuthors() {
        System.out.println("Authors:");
        List<Author> authors = libraryService.getAllAuthors();
        for (Author author : authors) {
            System.out.println("ID: " + author.getID() + ", Name: " + author.getName()
                    + ", Email: " + author.getEmail() + ", Phone: " + author.getPhoneNumber());
        }
    }

    public void viewBooksByPublisher(int publisherID) {
        List<Book> books = libraryService.getBooksByPublisher(publisherID);
        books.forEach(System.out::println);
    }

    public void viewBooksByAuthor(int authorID) {
        List<Book> books = libraryService.getBooksByAuthor(authorID);
        books.forEach(System.out::println);
    }

    public void viewActiveReservations(int memberID) {
        List<Reservation> reservations = libraryService.getActiveReservationsForMember(memberID);
        System.out.println("Active reservations for member ID: " + memberID);
        for(Reservation reservation : reservations) {
            System.out.println("ID: " + reservation.getID() + ", Date: " + reservation.getReservationDate()
                    + ", Book: " + reservation.getBook().getBookName() + ", Author: " + reservation.getBook().getAuthor().getName());
        }
    }

    public void viewLoanHistoryForMember(int memberID) {
        List<Loan> loans = libraryService.getLoanHistoryForMember(memberID);
        System.out.println("Loan History for member ID: " + memberID);
        for(Loan loan : loans) {
            System.out.println("ID: " + loan.getID() + ", Loan date: " + loan.getLoanDate() + ", Due date: "
                    + loan.getDueDate() + ", Return date: " + loan.getReturnDate() + ", Status: " + loan.getStatus()
                    + ", Book: " + loan.getBook().getBookName() + ", Book author: " + loan.getBook().getAuthor().getName());
        }
    }

    public void addBook(String title, int authorID, int categoryID, int publisherID) {
        libraryService.addBook(title, authorID, categoryID, publisherID);
        System.out.println("Book added successfully: " + title);
    }

    public void updateBook(int bookID, String newBookName, int newAuthorID, boolean newIsAvailable, int newCategoryID, int newPublisherID) {
        libraryService.updateBook(bookID, newBookName, newAuthorID, newIsAvailable, newCategoryID, newPublisherID);
        System.out.println("Book updated successfully: " + newBookName);
    }

    public void deleteBook(int bookID) {
        libraryService.deleteBook(bookID);
        System.out.println("Book deleted successfully: " + bookID);
    }


}
