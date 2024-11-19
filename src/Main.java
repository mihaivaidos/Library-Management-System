import LibraryDataInitializer.DataInitializer;
import LibraryModel.*;
import LibraryController.LibraryController;
import LibraryRepository.RepositoryFactory;
import LibraryService.LibraryService;
import LibraryUI.LibraryUI;
import LibraryRepository.IRepository;

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

        LibraryService libraryService = new LibraryService( bookRepo, loanRepo, reservationRepo, categoryRepo, memberRepo, reviewRepo, authorRepo, publisherRepo, staffRepo);
        LibraryController libraryController = new LibraryController(libraryService);

        DataInitializer.initializeRepositories(bookRepo, loanRepo, reservationRepo, categoryRepo, memberRepo, reviewRepo, authorRepo, publisherRepo, staffRepo);

        LibraryUI libraryUI = new LibraryUI(libraryController);
        libraryUI.start();

    }
}
