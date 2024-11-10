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
}
