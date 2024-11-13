package LibraryUI;

import LibraryController.LibraryController;
import LibraryModel.Review;

import java.util.List;
import java.util.Scanner;

/**
 * LibraryUI is a user interface class for managing a library system.
 * It interacts with the LibraryController to perform various operations
 * based on user input, allowing both staff and members to manage library resources
 */

public class LibraryUI {
    private final LibraryController controller;
    private final Scanner scanner;
    boolean continueLoop = true;

    /**
     * Constructs a LibraryUI instance with the specified LibraryController.
     *
     * @param controller the LibraryController used to perform library operations
     */


    public LibraryUI(LibraryController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the library management system.
     *
     * This method continuously prompts the user for their email address and displays
     * the appropriate menu (staff or member) based on the user's role. It allows
     * the user to continue using the system or exit as desired.
     */

    public void start() {

        while (continueLoop) {
            System.out.println("\nLibrary Management System");
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            boolean isStaff = controller.isStaff(email);
            int getID = controller.getIDbyEmail(email);

            if (isStaff) {
                viewID(email);
                staffMenu();
            } else {
                if(getID == -1) {
                    System.out.println("You are not registered!");
                    register();
                    if(continueLoop) {
                        memberMenu();
                    }
                } else {
                    viewID(email);
                    memberMenu();
                }
            }

            if(continueLoop) {
                System.out.print("Do you want to continue? (yes/no): ");
                String choice = scanner.nextLine().toLowerCase();
                continueLoop = choice.equals("yes");
            }
        }

        System.out.println("Thank you for using the Library Management System!");
    }

    /**
     * Displays the member menu and handles member-specific operations.
     */

    private void memberMenu() {
        while (true) {
            System.out.println("\nMember Menu:");
            System.out.println("1. View All Books");
            System.out.println("2. View Books by Publisher");
            System.out.println("3. View Books by Author");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. View All Books in Category");
            System.out.println("7. View All Categories");
            System.out.println("8. Add Review to Book");
            System.out.println("9. View Reviews of Book");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> viewAllBooks();
                case 2 -> viewBooksByPublisher();
                case 3 -> viewBooksByAuthor();
                case 4 -> borrowBook();
                case 5 -> returnBook();
                case 6 -> viewAllBooksInCategory();
                case 7 -> viewAllCategories();
                case 8 -> addReviewToBook();
                case 0 -> {
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    private void deleteReview() {
    }

    /**
     * Displays the staff menu and handles staff-specific operations.
     */

    private void staffMenu() {
        while (true) {
            System.out.println("\nStaff Menu:");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Delete Book");
            System.out.println("4. View Active Loans");
            System.out.println("5. View Loan History for Member");
            System.out.println("6. View Active Reservations");
            System.out.println("7. View All Books");
            System.out.println("8. View Books by Publisher");
            System.out.println("9. View Books by Author");
            System.out.println("10. Add Book to Category");
            System.out.println("11. View All Books in Category");
            System.out.println("12. View All Categories");
            System.out.println("13. Add member");
            System.out.println("14. Add Author");
            System.out.println("15. Add Publisher");
            System.out.println("16. View Reviews of Book");
            System.out.println("17. Delete Review");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");

            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1 -> addBook();
                case 2 -> updateBook();
                case 3 -> deleteBook();
                case 4 -> viewActiveLoans();
                case 5 -> viewLoanHistoryForMember();
                case 6 -> viewActiveReservations();
                case 7 -> viewAllBooks();
                case 8 -> viewBooksByPublisher();
                case 9 -> viewBooksByAuthor();
                case 10 -> addBookToCategory();
                case 11 -> viewAllBooksInCategory();
                case 12 -> viewAllCategories();
                case 13 -> addMember();
                case 14 -> addAuthor();
                case 15 -> addPublisher();
                case 16 -> viewAllReviewsOfBook();
                case 17 -> deleteReview();
                case 0 -> {
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    /**
     * Adds a new book to the library.
     * Prompts the user for the book's title, author ID, publisher ID, and category ID.
     */

    private void addBook() {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        viewAllAuthors();
        System.out.println("Enter author ID: ");
        int authorID = Integer.parseInt(scanner.nextLine());
        viewAllPublishers();
        System.out.println("Enter publisher ID: ");
        int publisherID = Integer.parseInt(scanner.nextLine());
        viewAllCategories();
        System.out.println("Enter category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        controller.addBook(title, authorID, publisherID, categoryID);
    }

    /**
     * Updates an existing book in the library.
     * Prompts the user for the book ID, new title, new author ID, new availability status,
     * new category ID, and new publisher ID.
     */

    private void updateBook() {
        viewAllBooks();
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new book title: ");
        String title = scanner.nextLine();
        System.out.println("Enter new author ID: ");
        int authorID = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter new availability status: ");
        boolean newIsAvailable = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Enter new category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new publisher ID: ");
        int publisherID = Integer.parseInt(scanner.nextLine());
        controller.updateBook(bookID, title, authorID, newIsAvailable,categoryID, publisherID);
    }

    /**
     * Deletes a book from the library.
     * Prompts the user for the book ID to be deleted.
     */


    private void deleteBook() {
        viewAllBooks();
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        controller.deleteBook(bookID);
    }

    /**
     * Views books by a specific publisher.
     * Prompts the user for the publisher ID and retrieves the corresponding books.
     */

    private void viewBooksByPublisher() {
        viewAllPublishers();
        System.out.println("Enter publisher ID: ");
        int publisherID = Integer.parseInt(scanner.nextLine());
        controller.viewBooksByPublisher(publisherID);
    }

    /**
     * Views books by a specific author.
     * Prompts the user for the author ID and retrieves the corresponding books.
     */

    private void viewBooksByAuthor() {
        viewAllAuthors();
        System.out.println("Enter author ID: ");
        int authorID = Integer.parseInt(scanner.nextLine());
        controller.viewBooksByAuthor(authorID);
    }

    /**
     * Displays all publishers available in the library.
     */

    private void viewAllPublishers() {
        controller.viewAllPublishers();
    }

    /**
     * Displays all authors available in the library.
     */

    private void viewAllAuthors() {
        controller.viewAllAuthors();
    }

    /**
     * Adds a review to a specific book.
     * Prompts the user for member ID, book ID, rating, and comments.
     */

    private void addReviewToBook() {
        System.out.println("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter rating: ");
        int rating = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter comments: ");
        String comments = scanner.nextLine();
        controller.addReviewToBook(memberID, bookID, rating, comments);
    }

    /**
     * Views all reviews for a specific book.
     * Prompts the user for the book ID to retrieve its reviews.
     */

    private void viewAllReviewsOfBook() {
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        controller.viewAllReviewsOfBook(bookID);
    }

    /**
     * Borrows a book for a member.
     * Prompts the user for member ID and book ID to process the borrowing.
     */

    private void borrowBook() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        controller.borrowBook(memberID, bookID);
    }

    /**
     * Returns a borrowed book for a member.
     * Prompts the user for member ID and loan ID to process the return.
     */

    private void returnBook() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        controller.viewActiveLoans(memberID);
        System.out.print("Enter loan ID: ");
        int loanID = Integer.parseInt(scanner.nextLine());
        controller.returnBook(loanID);
    }

    /**
     * Views the loan history for a specific member.
     * Prompts the user for the member ID to retrieve their loan history.
     */

    private void viewLoanHistoryForMember() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        controller.viewLoanHistoryForMember(memberID);
    }

    /**
     * Adds a book to a specific category.
     * Prompts the user for the book ID and category ID to associate the book with the category.
     */

    private void addBookToCategory() {
        viewAllBooks();
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        viewAllCategories();
        System.out.print("Enter category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        controller.addBookToCategory(bookID, categoryID);
    }

    /**
     * Views all books within a specific category.
     * Prompts the user for the category ID to retrieve the books in that category.
     */

    private void viewAllBooksInCategory() {
        viewAllCategories();
        System.out.print("Enter category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        controller.viewAllBooksInCategory(categoryID);
    }

    /**
     * Displays all categories available in the library.
     */

    private void viewAllCategories() {
        controller.viewAllCategories();
    }

    /**
     * Views active loans for a specific member.
     * Prompts the user for the member ID to retrieve their active loans.
     */

    private void viewActiveLoans() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        controller.viewActiveLoans(memberID);
    }

    /**
     * Views active reservations for a specific member.
     * Prompts the user for the member ID to retrieve their active reservations.
     */

    private void viewActiveReservations() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        controller.viewActiveReservations(memberID);
    }

    /**
     * Views all books available in the library.
     */

    private void viewAllBooks() {
        controller.viewAllBooks();
    }

    /**
     * Checks if a user is staff based on their email.
     * Prompts the user for their email and checks their staff status.
     */

    private void isStaff() {
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        controller.isStaff(email);
    }

    /**
     * Adds a new member to the library system.
     * Prompts the user for the member's name, email, and phone number.
     */

    private void addMember() {
        System.out.print("Enter member name: ");
        String name = scanner.nextLine();
        System.out.print("Enter member email: ");
        String email = scanner.nextLine();
        System.out.print("Enter member phone number: ");
        String phoneNumber = scanner.nextLine();
        controller.addMember(name, email, phoneNumber);
    }

    /**
     * Adds a new author to the library system.
     * Prompts the user for the author's name, email, and phone number.
     */

    private void addAuthor() {
        System.out.print("Enter author name: ");
        String name = scanner.nextLine();
        System.out.print("Enter author email: ");
        String email = scanner.nextLine();
        System.out.print("Enter author phone number: ");
        String phoneNumber = scanner.nextLine();
        controller.addAuthor(name, email, phoneNumber);
    }

    /**
     * Adds a new publisher to the library system.
     * Prompts the user for the publisher's name, email, and phone number.
     */

    private void addPublisher() {
        System.out.print("Enter publisher name: ");
        String name = scanner.nextLine();
        System.out.print("Enter publisher email: ");
        String email = scanner.nextLine();
        System.out.print("Enter member phone number: ");
        String phoneNumber = scanner.nextLine();
        controller.addPublisher(name, email, phoneNumber);
    }

    /**
     * Shows the ID with the help of the email
     *
     * @param email the email provided by the user
     */
    private void viewID(String email) {
        System.out.println("Your ID is: " + controller.getIDbyEmail(email));
    }

    /**
     * Prompts the user for the registration option and if the choice is 'yes' it adds a new member
     * If the choice is 'no', the loop will stop
     */
    private void register() {
        System.out.println("Do you want to register? (yes/no): ");
        String choice = scanner.nextLine().toLowerCase();
        if(choice.equals("yes")) {
            addMember();
            System.out.println("Enter your email for confirmation: ");
            String email = scanner.nextLine();
            viewID(email);
        } else {
            continueLoop = false;
        }
    }

}