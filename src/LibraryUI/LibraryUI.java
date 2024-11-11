package LibraryUI;

import LibraryController.LibraryController;

import java.util.Scanner;

public class LibraryUI {
    private final LibraryController controller;
    private final Scanner scanner;

    public LibraryUI(LibraryController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            System.out.println("\nLibrary Management System:");
            System.out.print("Enter your email: ");
            String email = scanner.nextLine();

            // Check if the user is staff
            boolean isStaff = controller.isStaff(email);

            if (isStaff) {
                staffMenu();
            } else {
                memberMenu();
            }
        }
    }

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
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

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
                case 0 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice! Please try again.");
            }
        }
    }

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

    private void deleteBook() {
        viewAllBooks();
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        controller.deleteBook(bookID);
    }

    private void viewBooksByPublisher() {
        viewAllPublishers();
        System.out.println("Enter publisher ID: ");
        int publisherID = Integer.parseInt(scanner.nextLine());
        controller.viewBooksByPublisher(publisherID);
    }

    private void viewBooksByAuthor() {
        viewAllAuthors();
        System.out.println("Enter author ID: ");
        int authorID = Integer.parseInt(scanner.nextLine());
        controller.viewBooksByAuthor(authorID);
    }

    private void viewAllPublishers() {
        controller.viewAllPublishers();
    }

    private void viewAllAuthors() {
        controller.viewAllAuthors();
    }

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

    private void viewAllReviewsOfBook() {
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        controller.viewAllReviewsOfBook(bookID);
    }

    private void borrowBook() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        controller.borrowBook(memberID, bookID);
    }

    private void returnBook() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        controller.viewActiveLoans(memberID);
        System.out.print("Enter loan ID: ");
        int loanID = Integer.parseInt(scanner.nextLine());
        controller.returnBook(loanID);
    }

    private void viewLoanHistoryForMember() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        controller.viewLoanHistoryForMember(memberID);
    }

    private void addBookToCategory() {
        viewAllBooks();
        System.out.print("Enter book ID: ");
        int bookID = Integer.parseInt(scanner.nextLine());
        viewAllCategories();
        System.out.print("Enter category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        controller.addBookToCategory(bookID, categoryID);
    }

    private void viewAllBooksInCategory() {
        viewAllCategories();
        System.out.print("Enter category ID: ");
        int categoryID = Integer.parseInt(scanner.nextLine());
        controller.viewAllBooksInCategory(categoryID);
    }

    private void viewAllCategories() {
        controller.viewAllCategories();
    }

    private void viewActiveLoans() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        controller.viewActiveLoans(memberID);
    }

    private void viewActiveReservations() {
        System.out.print("Enter member ID: ");
        int memberID = Integer.parseInt(scanner.nextLine());
        controller.viewActiveReservations(memberID);
    }

    private void viewAllBooks() {
        controller.viewAllBooks();
    }

    private void isStaff() {
        System.out.println("Enter your email: ");
        String email = scanner.nextLine();
        controller.isStaff(email);
    }
}