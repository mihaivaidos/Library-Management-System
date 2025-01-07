package LibraryUI;

import Exceptions.ValidationException;
import LibraryController.LibraryController;
import LibraryModel.Book;

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
    private int userID;

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
     * <p>
     * This method continuously prompts the user for their email address and displays
     * the appropriate menu (staff or member) based on the user's role. It allows
     * the user to continue using the system or exit as desired.
     */

    public void start() {

        while (continueLoop) {
            System.out.println("\nLibrary Management System");
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            try {
                boolean isStaff = controller.isStaff(email);
                userID = controller.getIDbyEmail(email);

                if (isStaff) {
                    viewID(email);
                    staffMenu();
                } else {
                    if (userID == -1) {
                        System.out.println("You are not registered!");
                        register();
                        if (continueLoop) {
                            memberMenu();
                        }
                    } else {
                        viewID(email);
                        memberMenu();
                    }
                }

                if (continueLoop) {
                    System.out.print("Do you want to continue? (yes/no): ");
                    String choice = scanner.nextLine().toLowerCase();
                    continueLoop = choice.equals("yes");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            }
        }

        System.out.println("Thank you for using the Library Management System!");
    }

    /**
     * Displays the member menu and handles member-specific operations.
     */

    private void memberMenu() {
        while (true) {
            printMemberMenu();
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1 -> bookMenu();
                    case 2 -> borrowBook();
                    case 3 -> returnBook();
                    case 4 -> viewAllCategories();
                    case 5 -> addReviewToBook();
                    case 6 -> viewAllReviewsOfBook();
                    case 7 -> viewActiveLoans();
                    case 8 -> viewActiveReservations();
                    case 9 -> viewLoanHistoryForMember();
                    case 0 -> {
                        System.out.println("Thank you!");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            }
        }
    }

    /**
     * Displays the member menu
     */
    private void printMemberMenu() {
        System.out.println("\nMember Menu:");
        System.out.println("1. Book Menu");
        System.out.println("2. Borrow Book");
        System.out.println("3. Return Book");
        System.out.println("4. View All Categories");
        System.out.println("5. Add Review to Book");
        System.out.println("6. View Reviews of Book");
        System.out.println("7. View Active Loans for a Member");
        System.out.println("8. View Active Reservations");
        System.out.println("9. View Loan History");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    /**
     * Displays the staff menu and handles staff-specific operations.
     */

    private void staffMenu() {
        while (true) {
            printStaffMenu();
            try {
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
                    case 17 -> deleteReviewFromBook();
                    case 0 -> {
                        System.out.println("Thank you!");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            }
        }
    }

    /**
     * Displays the staff menu
     */
    private void printStaffMenu() {
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
    }

    /**
     * Displays the book menu and handles book-specific operations
     */
    private void bookMenu() {
        while(true) {
            printBookMenu();

            int choice = Integer.parseInt(scanner.nextLine());
            try {
                switch (choice) {
                    case 1 -> viewAllBooks();
                    case 2 -> viewBooksByPublisher();
                    case 3 -> viewAllBooksInCategory();
                    case 4 -> viewBooksByAuthor();
                    case 5 -> viewSortedBooksByAvgRating();
                    case 6 -> recommendBooks();
                    case 0 -> {
                        System.out.println("Thank you!");
                        return;
                    }
                    default -> System.out.println("Invalid choice! Please try again.");
                }
            } catch (Exception e) {
                System.err.println("Error occurred: " + e.getMessage());
            }
        }
    }

    /**
     * Displays the book menu
     */
    private void printBookMenu() {
        System.out.println("\nBook Menu:");
        System.out.println("1. All books by default");
        System.out.println("2. All books by a publisher");
        System.out.println("3. All books by a category");
        System.out.println("4. All books by an author");
        System.out.println("5. All books sorted by rating");
        System.out.println("6. View book recommendations");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    /**
     * Adds a new book to the library.
     * Prompts the user for the book's title, author ID, publisher ID, and category ID.
     */

    private void addBook() {
        while(true) {
            try {
                System.out.print("Enter book title: ");
                String title = scanner.nextLine();
                if (title.isEmpty()) {
                    throw new ValidationException("Title cannot be empty.");
                }

                viewAllAuthors();
                System.out.println("Enter author ID (0 if you want to add a new author): ");
                int authorID = Integer.parseInt(scanner.nextLine());
                if (authorID < 0) {
                    throw new ValidationException("Author ID must be a positive integer.");
                }
                if(authorID == 0) {
                    addAuthor();
                    viewAllAuthors();
                    System.out.println("Enter author ID: ");
                    authorID = Integer.parseInt(scanner.nextLine());
                }

                viewAllPublishers();
                System.out.println("Enter publisher ID (0 if you want to add a new publisher): ");
                int publisherID = Integer.parseInt(scanner.nextLine());
                if (publisherID < 0) {
                    throw new ValidationException("Publisher ID must be a positive integer.");
                }
                if(publisherID == 0) {
                    addPublisher();
                    viewAllPublishers();
                    System.out.println("Enter publisher ID: ");
                    publisherID = Integer.parseInt(scanner.nextLine());
                }

                viewAllCategories();
                System.out.println("Enter category ID (0 if you want to add a new category): ");
                int categoryID = Integer.parseInt(scanner.nextLine());
                if (categoryID < 0) {
                    throw new ValidationException("Category ID must be a positive integer.");
                }
                if(categoryID == 0) {
                    addCategory();
                    viewAllCategories();
                    System.out.println("Enter category ID: ");
                    categoryID = Integer.parseInt(scanner.nextLine());
                }

                System.out.println("Enter the number of copies of the book: ");
                int copies = Integer.parseInt(scanner.nextLine());
                if (copies <= 0) {
                    throw new ValidationException("Number of copies must be a positive integer.");
                }
                controller.addBook(title, authorID, categoryID, publisherID, copies);
                break;
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            }
        }
    }

    /**
     * Updates an existing book in the library.
     * Prompts the user for the book ID, new title, new author ID, new availability status,
     * new category ID, and new publisher ID.
     */

    private void updateBook() {
        while(true) {
            try {
                viewAllBooks();
                System.out.print("Enter book ID: ");
                int bookID = Integer.parseInt(scanner.nextLine());
                if (bookID <= 0) {
                    throw new ValidationException("Book ID must be a positive integer.");
                }

                System.out.print("Enter new book title: ");
                String title = scanner.nextLine();
                if (title.isEmpty()) {
                    throw new ValidationException("Title cannot be empty.");
                }

                viewAllAuthors();
                System.out.println("Enter new author ID: ");
                int authorID = Integer.parseInt(scanner.nextLine());
                if (authorID <= 0) {
                    throw new ValidationException("Author ID must be a positive integer.");
                }

                System.out.println("Enter new availability status (true / false): ");
                String input = scanner.nextLine();
                if (!input.equalsIgnoreCase("true") && !input.equalsIgnoreCase("false")) {
                    throw new ValidationException("Availability status must be 'true' or 'false'.");
                }
                boolean newIsAvailable = Boolean.parseBoolean(input);

                viewAllCategories();
                System.out.print("Enter new category ID: ");
                int categoryID = Integer.parseInt(scanner.nextLine());
                if (categoryID <= 0) {
                    throw new ValidationException("Category ID must be a positive integer.");
                }

                viewAllPublishers();
                System.out.print("Enter new publisher ID: ");
                int publisherID = Integer.parseInt(scanner.nextLine());
                if (publisherID <= 0) {
                    throw new ValidationException("Publisher ID must be a positive integer.");
                }

                System.out.println("Enter new number of copies available: ");
                int copiesAvailable = Integer.parseInt(scanner.nextLine());
                if (copiesAvailable <= 0) {
                    throw new ValidationException("Number of copies available must be a positive integer.");
                }

                controller.updateBook(bookID, title, authorID, newIsAvailable, categoryID, publisherID, copiesAvailable);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Deletes a book from the library.
     * Prompts the user for the book ID to be deleted.
     */
    private void deleteBook() {
        while(true) {
            try {
                viewAllBooks();
                System.out.print("Enter book ID: ");
                int bookID = Integer.parseInt(scanner.nextLine());
                if (bookID <= 0) {
                    throw new ValidationException("Book ID must be a positive integer.");
                }
                controller.deleteBook(bookID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Views books by a specific publisher.
     * Prompts the user for the publisher ID and retrieves the corresponding books.
     */

    private void viewBooksByPublisher() {
        while(true) {
            try {
                viewAllPublishers();
                System.out.println("Enter publisher ID: ");
                int publisherID = Integer.parseInt(scanner.nextLine());
                if (publisherID <= 0) {
                    throw new ValidationException("Publisher ID must be a positive integer.");
                }
                controller.viewBooksByPublisher(publisherID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Views books by a specific author.
     * Prompts the user for the author ID and retrieves the corresponding books.
     */

    private void viewBooksByAuthor() {
        while(true) {
            try {
                viewAllAuthors();
                System.out.println("Enter author ID: ");
                int authorID = Integer.parseInt(scanner.nextLine());
                if (authorID <= 0) {
                    throw new ValidationException("Author ID must be a positive integer.");
                }
                controller.viewBooksByAuthor(authorID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
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
        while(true) {
            try {
                System.out.println("Enter member ID: ");
                int memberID = Integer.parseInt(scanner.nextLine());
                if (memberID <= 0) {
                    throw new ValidationException("Member ID must be a positive integer.");
                }
                if (userID != memberID) {
                    throw new ValidationException("Member IDs must be the same.");
                }
                controller.viewMemberBorrowedBooks(memberID);
                System.out.print("Enter book ID: ");
                int bookID = Integer.parseInt(scanner.nextLine());
                if (bookID <= 0) {
                    throw new ValidationException("Book ID must be a positive integer.");
                }
                System.out.print("Enter rating: ");
                int rating = Integer.parseInt(scanner.nextLine());
                if (rating > 5 || rating < 1) {
                    throw new ValidationException("Rating must be a integer between 1 and 5.");
                }
                System.out.print("Enter comments: ");
                String comments = scanner.nextLine();
                if (comments.isEmpty()) {
                    throw new ValidationException("Comments cannot be empty.");
                }
                controller.addReviewToBook(memberID, bookID, rating, comments);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Shows all reviews first and then prompts the user for a reviewID
     */
    private void deleteReviewFromBook() {
        while(true) {
            try {
                viewAllReviews();
                System.out.println("Enter review ID: ");
                int reviewID = Integer.parseInt(scanner.nextLine());
                if (reviewID <= 0) {
                    throw new ValidationException("Review ID must be a positive integer.");
                }
                controller.deleteReviewFromBook(reviewID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Views all reviews for a specific book.
     * Prompts the user for the book ID to retrieve its reviews.
     */

    private void viewAllReviewsOfBook() {
        while(true) {
            try {
                viewAllBooks();
                System.out.print("Enter book ID: ");
                int bookID = Integer.parseInt(scanner.nextLine());
                if (bookID <= 0) {
                    throw new ValidationException("Book ID must be a positive integer.");
                }
                controller.viewAllReviewsOfBook(bookID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    private void viewAllReviews() {
        controller.viewAllReviews();
    }

    /**
     * Allows a member to borrow a book from the library.
     * This method prompts the user for their member ID and a book title to search for.
     * If the title is left blank, it retrieves and displays all available books.
     * The user can then select a book by its ID to borrow or cancel the operation
     * by entering '0'. Appropriate messages are displayed if no books are found
     * or if an invalid ID is selected.
     */

    private void borrowBook() {
        while(true) {
            try {
                System.out.print("Enter member ID: ");
                int memberID = Integer.parseInt(scanner.nextLine());
                if (memberID <= 0) {
                    throw new ValidationException("Member ID must be a positive integer.");
                }
                if (userID != memberID) {
                    throw new ValidationException("Member IDs must be the same.");
                }
                System.out.println("Do you want to see the recommended books for you? (yes/no): ");
                String rec = scanner.nextLine().toLowerCase();
                if (rec.equals("yes")) {
                    controller.recommendBooksForMember(memberID);
                }


                System.out.print("Enter book title to search (or leave blank to view all): ");
                String searchTerm = scanner.nextLine();

                List<Book> books = controller.searchBook(searchTerm);

                if (books.isEmpty()) {
                    System.out.println("No books found matching your search criteria.");
                    return;
                }

                System.out.println("\nAvailable Books:");
                for (Book book : books) {
                    System.out.println("Book ID: " + book.getID() + ", Title: " + book.getBookName());
                }

                System.out.print("Enter book ID to borrow (or 0 to cancel): ");
                int choice = Integer.parseInt(scanner.nextLine());

                if (choice < 0) {
                    throw new ValidationException("Book ID must be a positive integer.");
                }

                if (choice == 0) {
                    System.out.println("Borrowing cancelled.");
                    return;
                }

                boolean bookFound = false;
                for (Book book : books) {
                    if (book.getID() == choice) {
                        controller.borrowBook(memberID, choice);
                        bookFound = true;
                        System.out.println("You have successfully borrowed: " + book.getBookName());
                        break;
                    }
                }

                if (!bookFound) {
                    System.out.println("Invalid selection. Borrowing cancelled.");
                }
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }
//    private void borrowBook() {
//        System.out.print("Enter member ID: ");
//        int memberID = Integer.parseInt(scanner.nextLine());
//        viewAllBooks();
//        System.out.print("Enter book ID: ");
//        int bookID = Integer.parseInt(scanner.nextLine());
//        controller.borrowBook(memberID, bookID);
//    }

    /**
     * Returns a borrowed book for a member.
     * Prompts the user for member ID and loan ID to process the return.
     */

    private void returnBook() {
        while(true) {
            try {
                System.out.print("Enter member ID: ");
                int memberID = Integer.parseInt(scanner.nextLine());
                if (memberID <= 0) {
                    throw new ValidationException("Member ID must be a positive integer.");
                }
                if (userID != memberID) {
                    throw new ValidationException("Member IDs must be the same.");
                }
                controller.viewActiveLoans(memberID);
                System.out.print("Enter loan ID: ");
                int loanID = Integer.parseInt(scanner.nextLine());
                if (loanID <= 0) {
                    throw new ValidationException("Loan ID must be a positive integer.");
                }
                controller.returnBook(loanID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Views the loan history for a specific member.
     * Prompts the user for the member ID to retrieve their loan history.
     */

    private void viewLoanHistoryForMember() {
        while(true) {
            try {
                System.out.print("Enter member ID: ");
                int memberID = Integer.parseInt(scanner.nextLine());
                if (memberID <= 0) {
                    throw new ValidationException("Member ID must be a positive integer.");
                }
                if (userID != memberID) {
                    throw new ValidationException("Member IDs must be the same.");
                }
                controller.viewLoanHistoryForMember(memberID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Adds a book to a specific category.
     * Prompts the user for the book ID and category ID to associate the book with the category.
     */

    private void addBookToCategory() {
        while(true) {
            try {
                viewAllBooks();
                System.out.print("Enter book ID: ");
                int bookID = Integer.parseInt(scanner.nextLine());
                if (bookID <= 0) {
                    throw new ValidationException("Book ID must be a positive integer.");
                }

                viewAllCategories();
                System.out.print("Enter category ID: ");
                int categoryID = Integer.parseInt(scanner.nextLine());
                if (categoryID <= 0) {
                    throw new ValidationException("Category ID must be a positive integer.");
                }

                controller.addBookToCategory(bookID, categoryID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Views all books within a specific category.
     * Prompts the user for the category ID to retrieve the books in that category.
     */

    private void viewAllBooksInCategory() {
        while(true) {
            try {
                viewAllCategories();
                System.out.print("Enter category ID: ");
                int categoryID = Integer.parseInt(scanner.nextLine());
                if (categoryID <= 0) {
                    throw new ValidationException("Category ID must be a positive integer.");
                }
                controller.viewAllBooksInCategory(categoryID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
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
        while(true) {
            try {
                System.out.print("Enter member ID: ");
                int memberID = Integer.parseInt(scanner.nextLine());
                if (memberID <= 0) {
                    throw new ValidationException("Member ID must be a positive integer.");
                }
                if (userID != memberID) {
                    throw new ValidationException("Member ID must be yours.");
                }
                controller.viewActiveLoans(memberID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Views active reservations for a specific member.
     * Prompts the user for the member ID to retrieve their active reservations.
     */

    private void viewActiveReservations() {
        while(true) {
            try {
                System.out.print("Enter member ID: ");
                int memberID = Integer.parseInt(scanner.nextLine());
                if (memberID <= 0) {
                    throw new ValidationException("Member ID must be a positive integer.");
                }
                if (userID != memberID) {
                    throw new ValidationException("Member IDs must be the same.");
                }

                controller.viewActiveReservations(memberID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
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
        try {
            System.out.println("Enter your email: ");
            String email = scanner.nextLine();
            if(email.isEmpty()) {
                throw new ValidationException("Email cannot be empty.");
            }
            controller.isStaff(email);
        } catch (NumberFormatException e) {
            System.err.println("Error: Invalid email. Please try again.");
        } catch (ValidationException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Adds a new member to the library system.
     * Prompts the user for the member's name, email, and phone number.
     */

    private void addMember() {
        while(true) {
            try {
                System.out.print("Enter member name: ");
                String name = scanner.nextLine();
                if (name.isEmpty() || !name.matches("^[a-zA-Z]+([\\s-][a-zA-Z]+)*$")) {
                    throw new ValidationException("Member name cannot be empty.");
                }
                System.out.print("Enter member email: ");
                String email = scanner.nextLine();
                if (email.isEmpty() || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    throw new ValidationException("Member email cannot be empty or does not match the pattern.");
                }
                System.out.print("Enter member phone number: ");
                String phoneNumber = scanner.nextLine();
                if (!phoneNumber.matches("^0\\d+$") || phoneNumber.length() != 10) {
                    throw new ValidationException("Member phone number cannot be empty or does not match the pattern.");
                }
                controller.addMember(name, email, phoneNumber);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Adds a new author to the library system.
     * Prompts the user for the author's name, email, and phone number.
     */

    private void addAuthor() {
        while(true) {
            try {
                System.out.print("Enter author name: ");
                String name = scanner.nextLine();
                if (name.isEmpty() || !name.matches("^[a-zA-Z]+([\\s-][a-zA-Z]+)*$")) {
                    throw new ValidationException("Author name cannot be empty or contain numbers.");
                }
                System.out.print("Enter author email: ");
                String email = scanner.nextLine();
                if (email.isEmpty() || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    throw new ValidationException("Author email cannot be empty or does not match the pattern.");
                }
                System.out.print("Enter author phone number: ");
                String phoneNumber = scanner.nextLine();
                if (!phoneNumber.matches("^0\\d+$") || phoneNumber.length() != 10) {
                    throw new ValidationException("Author phone number cannot be empty or does not match the pattern.");
                }
                controller.addAuthor(name, email, phoneNumber);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Adds a new publisher to the library system.
     * Prompts the user for the publisher's name, email, and phone number.
     */

    private void addPublisher() {
        while(true) {
            try {
                System.out.print("Enter publisher name: ");
                String name = scanner.nextLine();
                if (name.isEmpty() || !name.matches("^[a-zA-Z]+([\\s-][a-zA-Z]+)*$")) {
                    throw new ValidationException("Publisher name cannot be empty or contain numbers.");
                }
                System.out.print("Enter publisher email: ");
                String email = scanner.nextLine();
                if (email.isEmpty() || !email.matches("^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
                    throw new ValidationException("Publisher email cannot be empty or does not match the pattern.");
                }
                System.out.print("Enter member phone number: ");
                String phoneNumber = scanner.nextLine();
                if (!phoneNumber.matches("^0\\d+$") || phoneNumber.length() != 10) {
                    throw new ValidationException("Publisher phone number cannot be empty or does not match the pattern.");
                }
                controller.addPublisher(name, email, phoneNumber);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     *  Adds a new category to the library system.
     *  Prompts the user for the category's name and description.
     */
    private void addCategory() {
        while(true) {
            try {
                System.out.println("Enter category name: ");
                String name = scanner.nextLine();
                if(name.isEmpty() || !name.matches("^[a-zA-Z]+([\\s-][a-zA-Z]+)*$")) {
                    throw new ValidationException("Category name cannot be empty or contain numbers.");
                }

                System.out.println("Enter category description: ");
                String description = scanner.nextLine();
                if(description.isEmpty()) {
                    throw new ValidationException("Category description cannot be empty.");
                }
                controller.addCategory(name, description);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
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
        while(true) {
            try {
                System.out.println("Do you want to register? (yes/no): ");
                String choice = scanner.nextLine().toLowerCase();
                if (!choice.equals("yes") && !choice.equals("no")) {
                    throw new ValidationException("You have entered an invalid choice.");
                }
                if (choice.equals("yes")) {
                    addMember();
                    System.out.println("Enter your email for confirmation: ");
                    String email = scanner.nextLine();
                    if (email.isEmpty()) {
                        throw new ValidationException("Email cannot be empty.");
                    }
                    viewID(email);
                    userID = controller.getIDbyEmail(email);
                    break;
                } else {
                    continueLoop = false;
                    break;
                }
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            }
        }
    }

    /**
     * Prompts the user for a member ID and prints the recommended books
     */
    public void recommendBooks() {
        while(true) {
            try {
                System.out.print("Enter Member ID: ");
                int memberID = Integer.parseInt(scanner.nextLine());
                if (memberID <= 0) {
                    throw new ValidationException("Member ID must be a positive integer.");
                }
                if (userID != memberID) {
                    throw new ValidationException("Member IDs must be the same.");
                }
                controller.recommendBooksForMember(memberID);
                break;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid number format. Please try again.");
            } catch (ValidationException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /**
     * Shows the books sorted by the average rating
     */
    public void viewSortedBooksByAvgRating() {
        controller.sortBooksByAvgRating();
    }


}