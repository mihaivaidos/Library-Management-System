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

}