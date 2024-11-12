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


        Category fiction = new Category(1, "Fiction", "Fictional books");
        Category nonFiction = new Category(2, "Non-Fiction", "Non-Fictional books");
        categoryRepo.add(fiction);
        categoryRepo.add(nonFiction);

        Author author1 = new Author(1, "J.K. Rowling", "jkrowling@example.com", "1234567890");
        Author author2 = new Author(2, "George R.R. Martin", "georgerrmartin@example.com", "0987654321");
        authorRepo.add(author1);
        authorRepo.add(author2);

        Publisher publisher1 = new Publisher(1, "Bloomsbury", "contact@bloomsbury.com", "1234567890");
        Publisher publisher2 = new Publisher(2, "Bantam Books", "contact@bantambooks.com", "0987654321");
        publisherRepo.add(publisher1);
        publisherRepo.add(publisher2);

        Book book1 = new Book(1, "Harry Potter and the Philosopher's Stone", author1, true, fiction, publisher1);
        Book book2 = new Book(2, "A Game of Thrones", author2, true, fiction, publisher2);
        bookRepo.add(book1);
        bookRepo.add(book2);

        Member member1 = new Member(1, "Alice", "alice@yahoo.com", "1112223333");
        Member member2 = new Member(2, "Bob", "bob@yahoo.com", "4445556666");
        memberRepo.add(member1);
        memberRepo.add(member2);

        Staff staff1 = new Staff(1, "Charlie", "charlieLIB@yahoo.com", "7778889999", "Librarian");
        staffRepo.add(staff1);

        LibraryUI libraryUI = new LibraryUI(libraryController);
        libraryUI.start();

    }
}
