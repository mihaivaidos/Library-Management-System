import LibraryDataInitializer.DataInitializer;
import LibraryModel.*;
import LibraryController.LibraryController;
import LibraryRepository.InMemoryRepository;
import LibraryService.LibraryService;
import LibraryUI.LibraryUI;
import LibraryRepository.IRepository;

public class Main {
    public static void main(String[] args) {

        IRepository<Book> bookRepo = new InMemoryRepository<>();
        IRepository<Loan> loanRepo = new InMemoryRepository<>();
        IRepository<Reservation> reservationRepo = new InMemoryRepository<>();
        IRepository<Category> categoryRepo = new InMemoryRepository<>();
        IRepository<Member> memberRepo = new InMemoryRepository<>();
        IRepository<Review> reviewRepo = new InMemoryRepository<>();
        IRepository<Author> authorRepo = new InMemoryRepository<>();
        IRepository<Publisher> publisherRepo = new InMemoryRepository<>();
        IRepository<Staff> staffRepo = new InMemoryRepository<>();

        LibraryService libraryService = new LibraryService( bookRepo, loanRepo, reservationRepo, categoryRepo, memberRepo, reviewRepo, authorRepo, publisherRepo, staffRepo);
        LibraryController libraryController = new LibraryController(libraryService);

        DataInitializer.initializeRepositories(bookRepo, loanRepo, reservationRepo, categoryRepo, memberRepo, reviewRepo, authorRepo, publisherRepo, staffRepo);

        LibraryUI libraryUI = new LibraryUI(libraryController);
        libraryUI.start();

    }
}
