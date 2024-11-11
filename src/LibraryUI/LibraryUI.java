package LibraryUI;
import LibraryController.LibraryController;
import LibraryService.LibraryService;
import LibraryRepository.InMemoryRepository;
import LibraryModel.*;
import java.util.Scanner;
import java.util.stream.IntStream;

public class LibraryUI {
    private final LibraryController libraryController;

    public LibraryUI(LibraryController libraryController) {
        this.libraryController = libraryController;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean continueLoop = true;
        while (continueLoop) {
            System.out.print("""
                    Select an option:
                    
                    1. Add Book
                    2. Borrow Book
                    3. Return Book
                    4. View All Books
                    5. View All Authors
                    6. View All Publishers
                    7. Add Review to Book
                    8. View All Reviews of Book
                    9. Delete Review from Book
                    10. View Active Loans
                    11. Add Book to Category
                    12. View All Books in Category
                    13. View All Categories
                    14. View Books by Publisher
                    15. View Books by Author
                    16. View Active Reservations
                    17. View Loan History for Member
                    18. Update Book
                    19. Delete Book
                    0. Exit
                    """);

            String option = scanner.nextLine();
            switch (option) {
                case "0":
                    continueLoop = false;
                    break;
                case "1":
                    private void addBook() {
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author ID: ");
                    int authorID = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter category ID: ");
                    int categoryID = Integer.parseInt(scanner.nextLine());
                    System.out.print("Enter publisher ID: ");
                    int publisherID = Integer.parseInt(scanner.nextLine());
                    libraryController.addBook(title, authorID, categoryID, publisherID);
                }
                    break;
                case "2":
                    private void borrowBook()
                    break;
                case "3":
                    private void returnBook()
                    break;
                case "4":
                    public void viewAllBooks();
                    break;
                case "5":
                    libraryController.viewAllAuthors();
                    break;
                case "6":
                    libraryController.viewAllPublishers();
                    break;
                case "7":
                    libraryController.addReviewToBook(scanner);
                    break;
                case "8":
                    libraryController.viewAllReviewsOfBook(scanner);
                    break;
                case "9":
                    libraryController.deleteReviewFromBook(scanner);
                    break;
                case "10":
                    libraryController.viewActiveLoans(scanner);
                    break;
                case "11":
                    libraryController.addBookToCategory(scanner);
                    break;
                case "12":
                    libraryController.viewAllBooksInCategory(scanner);
                    break;
                case "13":
                    libraryController.viewAllCategories();
                    break;
                case "14":
                    libraryController.viewBooksByPublisher(scanner);
                    break;
                case "15":
                    libraryController.viewBooksByAuthor(scanner);
                    break;
                case "16":
                    libraryController.viewActiveReservations(scanner);
                    break;
                case "17":
                    libraryController.viewLoanHistoryForMember(scanner);
                    break;
                case "18":
                    libraryController.updateBook(scanner);
                case "19":
                    libraryController.deleteBook(scanner);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
            scanner.close();
        }
    }

    private void addBook(Scanner scanner) {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author ID: ");
        int authorID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter publisher ID: ");
        int publisherID = Integer.parseInt(scanner.nextLine());
        libraryController.addBook(title, authorID, categoryID, publisherID);
    }

    private void borrowBook(Scanner scanner) {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        libraryController.borrowBook(memberID, bookID);
    }

    private void returnBook(Scanner scanner) {
        System.out.print("Enter loan ID: ");
        int loanID = Integer.parseInt(scanner.nextLine());
        libraryController.returnBook(loanID);
    }

    private void addReviewToBook(Scanner scanner) {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter rating (1-5): ");
        int rating = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter review text: ");
        String reviewText = scanner.nextLine();
        libraryController.addReviewToBook(memberID, bookID, rating, reviewText);
    }

    private void viewAllReviewsOfBook(Scanner scanner) {
        System.out.print("Enter book ID to view reviews: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        libraryController.viewAllReviewsOfBook(bookID);
    }

    private void deleteReview(Scanner scanner) {
        System.out.print("Enter review ID to delete: ");
        int reviewID = Integer.parseInt(scanner.nextLine());
        libraryController.deleteReviewFromBook(reviewID);
    }

    private void viewActiveLoans(Scanner scanner) {
        System.out.print("Enter member ID to view active loans: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        libraryController.viewActiveLoans(memberID);
    }

    private void addBookToCategory(Scanner scanner) {
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        libraryController.addBookToCategory(bookID, categoryID);
    }

    private void viewAllBooksInCategory(Scanner scanner) {
        System.out.print("Enter category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        libraryController.viewAllBooksInCategory(categoryID);
    }

    private void viewBooksByPublisher(Scanner scanner) {
        System.out.print("Enter publisher ID: ");
        int publisherID = Integer.parseInt(scanner.nextLine());
        libraryController.viewBooksByPublisher(publisherID);
    }
    private void viewBooksByAuthor(Scanner scanner) {
        System.out.print("Enter author ID: ");
        int authorID = Integer.parseInt(scanner.nextLine());
        libraryController.viewBooksByAuthor(authorID);
    }

    private void viewActiveReservations(Scanner scanner) {
        System.out.print("Enter member ID to view active reservations: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        libraryController.viewActiveReservations(memberID);
    }

    private void viewLoanHistoryForMember(Scanner scanner) {
        System.out.print("Enter member ID to view loan history: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        libraryController.viewLoanHistoryForMember(memberID);
    }

    private void updateBook(Scanner scanner) {
        System.out.print("Enter book ID to update: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new title (leave blank to keep current): ");
        String newTitle = scanner.nextLine();
        System.out.print("Enter new author ID (leave blank to keep current): ");
        String authorInput = scanner.nextLine();
        Integer newAuthorID = authorInput.isEmpty() ? null : Integer.parseInt(authorInput);
        System.out.print("Enter new category ID (leave blank to keep current): ");
        String categoryInput = scanner.nextLine();
        Integer newCategoryID = categoryInput.isEmpty() ? null : Integer.parseInt(categoryInput);
        System.out.print("Enter new publisher ID (leave blank to keep current): ");
        String publisherInput = scanner.nextLine();
        Integer newPublisherID = publisherInput.isEmpty() ? null : Integer.parseInt(publisherInput);
        libraryController.updateBook(bookID, newTitle, newAuthorID, newCategoryID, newPublisherID);
    }

    private void deleteBook(Scanner scanner) {
        System.out.print("Enter book ID to delete: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        libraryController.deleteBook(bookID);
    }

    private void viewAllBooks() {
        libraryController.viewAllBooks();
    }

    private void viewAllAuthors() {
        libraryController.viewAllAuthors();
    }

    private void viewAllPublishers() {
        libraryController.viewAllPublishers();
    }

    private void viewAllCategories() {
        libraryController.viewAllCategories();
    }
}

