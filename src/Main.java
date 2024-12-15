import LibraryDataInitializer.DataInitializer;
import LibraryModel.*;
import LibraryController.LibraryController;
import LibraryRepository.DataBaseRepository.DBConnection;
import LibraryRepository.RepositoryFactory;
import LibraryService.LibraryService;
import LibraryUI.LibraryUI;
import LibraryRepository.IRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose repository type: (inmemory/file/database)");
        String repositoryType = scanner.nextLine().toLowerCase();

        IRepository<Book> bookRepo = RepositoryFactory.createRepository(Book.class, repositoryType);
        IRepository<Loan> loanRepo = RepositoryFactory.createRepository(Loan.class, repositoryType);
        IRepository<Reservation> reservationRepo = RepositoryFactory.createRepository(Reservation.class, repositoryType);
        IRepository<Category> categoryRepo = RepositoryFactory.createRepository(Category.class, repositoryType);
        IRepository<Member> memberRepo = RepositoryFactory.createRepository(Member.class, repositoryType);
        IRepository<Review> reviewRepo = RepositoryFactory.createRepository(Review.class, repositoryType);
        IRepository<Author> authorRepo = RepositoryFactory.createRepository(Author.class, repositoryType);
        IRepository<Publisher> publisherRepo = RepositoryFactory.createRepository(Publisher.class, repositoryType);
        IRepository<Staff> staffRepo = RepositoryFactory.createRepository(Staff.class, repositoryType);

        if (repositoryType.equals("inmemory") || repositoryType.equals("file")) {
            DataInitializer.initializeRepositories(bookRepo, loanRepo, reservationRepo, categoryRepo, memberRepo, reviewRepo, authorRepo, publisherRepo, staffRepo);
        }

        LibraryService libraryService = new LibraryService( bookRepo, loanRepo, reservationRepo, categoryRepo, memberRepo, reviewRepo, authorRepo, publisherRepo, staffRepo);
        LibraryController libraryController = new LibraryController(libraryService);

        LibraryUI libraryUI = new LibraryUI(libraryController);
        libraryUI.start();

//        try (Connection connection = DBConnection.getConnection()) {
//            if (connection != null) {
//                System.out.println("Connection successful!");
//            } else {
//                System.out.println("Failed to connect.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        String URL = "jdbc:postgresql://localhost:5432/LibraryManagementSystem";  // URL for PostgreSQL
//        String USER = "postgres";  // PostgreSQL username
//        String PASSWORD = "password";
//
//        IRepository<Author> authorDBRepo = new AuthorDBRepository(URL, USER, PASSWORD);
//        IRepository<Book> bookDBRepo = new BookDBRepository(URL, USER, PASSWORD);
//        IRepository<Category> categoryDBRepo = new CategoryDBRepository(URL, USER, PASSWORD);
//        IRepository<Loan> loanDBRepo = new LoanDBRepository(URL, USER, PASSWORD);
//        IRepository<Member> memberDBRepo = new MemberDBRepository(URL, USER, PASSWORD);
//        IRepository<Publisher> publisherDBRepo = new PublisherDBRepository(URL, USER, PASSWORD);
//        IRepository<Reservation> reservationDBRepo = new ReservationDBRepository(URL, USER, PASSWORD);
//        IRepository<Review> reviewDBRepo = new ReviewDBRepository(URL, USER, PASSWORD);
//        IRepository<Staff> staffDBRepo = new StaffDBRepository(URL, USER, PASSWORD);
//
//        //staffDBRepo.delete(1);
//
//        Author author1 = new Author(1, "J.K. Rowling", "jkrowling@example.com", "1234567890");
//        Author author2 = new Author(2, "George R.R. Martin", "georgerrmartin@example.com", "0987654321");
//        //authorDBRepo.add(author1);
////        authorDBRepo.add(author2);
////
//        Category fiction = new Category(1, "Fiction", "Fictional books");
//        Category nonFiction = new Category(2, "Non-Fiction", "Non-Fictional books");
////
//        //categoryDBRepo.add(fiction);
////        categoryDBRepo.add(nonFiction);
////
//        Publisher publisher1 = new Publisher(1, "Bloomsbury", "contact@bloomsbury.com", "1234567890");
//        Publisher publisher2 = new Publisher(2, "Bantam Books", "contact@bantambooks.com", "0987654321");
////
////        publisherDBRepo.add(publisher1);
////        publisherDBRepo.add(publisher2);
////
//        Book book1 = new Book(1, "Harry Potter and the Philosopher's Stone", author1, true, fiction, publisher1, 5);
//        Book book2 = new Book(2, "A Game of Thrones", author2, true, nonFiction, publisher2, 3);
////
//        //bookDBRepo.add(book1);
//        //bookDBRepo.update(book1);
//        //bookDBRepo.add(book2);
//
//        //Staff staff = new Staff(1, "Mihoi", "mihai@vaidos.com", "0821000040", "Manager");
//        //staffDBRepo.add(staff);
//
//        //categoryDBRepo.delete(1);
//
//        //System.out.println(staffDBRepo.getAll());
//        //System.out.println(staffDBRepo.get(1));
//        bookDBRepo.delete(1);
//        System.out.println(authorDBRepo.getAll());
//        System.out.println(bookDBRepo.getAll());


    }
}
