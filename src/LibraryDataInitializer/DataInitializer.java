package LibraryDataInitializer;
import Exceptions.DatabaseException;
import LibraryModel.*;
import LibraryRepository.IRepository;

import java.util.Date;

public class DataInitializer {

    public static void initializeRepositories(
            IRepository<Book> bookRepo,
            IRepository<Loan> loanRepo,
            IRepository<Reservation> reservationRepo,
            IRepository<Category> categoryRepo,
            IRepository<Member> memberRepo,
            IRepository<Review> reviewRepo,
            IRepository<Author> authorRepo,
            IRepository<Publisher> publisherRepo,
            IRepository<Staff> staffRepo
    ) {

        try {
            // Initialize Categories
            Category fiction = new Category(1, "Fiction", "Fictional books");
            Category nonFiction = new Category(2, "Non-Fiction", "Non-Fictional books");
            Category mystery = new Category(3, "Mystery", "Books with mysterious plots");
            Category science = new Category(4, "Science", "Scientific books");
            Category fantasy = new Category(5, "Fantasy", "Fantasy books with imaginary worlds");

            categoryRepo.add(fiction);
            categoryRepo.add(nonFiction);
            categoryRepo.add(mystery);
            categoryRepo.add(science);
            categoryRepo.add(fantasy);

            // Initialize Authors
            Author author1 = new Author(1, "J.K. Rowling", "jkrowling@example.com", "1234567890");
            Author author2 = new Author(2, "George R.R. Martin", "georgerrmartin@example.com", "0987654321");
            Author author3 = new Author(3, "Isaac Asimov", "asimov@example.com", "1112223333");
            Author author4 = new Author(4, "Agatha Christie", "agatha@example.com", "4445556666");
            Author author5 = new Author(5, "J.R.R. Tolkien", "tolkien@example.com", "7778889999");

            authorRepo.add(author1);
            authorRepo.add(author2);
            authorRepo.add(author3);
            authorRepo.add(author4);
            authorRepo.add(author5);

            // Initialize Publishers
            Publisher publisher1 = new Publisher(1, "Bloomsbury", "contact@bloomsbury.com", "1234567890");
            Publisher publisher2 = new Publisher(2, "Bantam Books", "contact@bantambooks.com", "0987654321");
            Publisher publisher3 = new Publisher(3, "Penguin Books", "contact@penguin.com", "1111111111");
            Publisher publisher4 = new Publisher(4, "HarperCollins", "contact@harpercollins.com", "2222222222");
            Publisher publisher5 = new Publisher(5, "Random House", "contact@randomhouse.com", "3333333333");

            publisherRepo.add(publisher1);
            publisherRepo.add(publisher2);
            publisherRepo.add(publisher3);
            publisherRepo.add(publisher4);
            publisherRepo.add(publisher5);

            // Initialize Books
            Book book1 = new Book(1, "Harry Potter and the Philosopher's Stone", author1, true, fiction, publisher1, 5);
            Book book2 = new Book(2, "A Game of Thrones", author2, true, fantasy, publisher2, 3);
            Book book3 = new Book(3, "Foundation", author3, true, science, publisher3, 2);
            Book book4 = new Book(4, "Murder on the Orient Express", author4, true, mystery, publisher4, 1);
            Book book5 = new Book(5, "The Hobbit", author5, true, fantasy, publisher5, 3);

            bookRepo.add(book1);
            bookRepo.add(book2);
            bookRepo.add(book3);
            bookRepo.add(book4);
            bookRepo.add(book5);

            // Add books to authors
            author1.getBooks().add(book1);
            author2.getBooks().add(book2);
            author3.getBooks().add(book3);
            author4.getBooks().add(book4);
            author5.getBooks().add(book5);

            authorRepo.update(author1);
            authorRepo.update(author2);
            authorRepo.update(author3);
            authorRepo.update(author4);
            authorRepo.update(author5);

            // Add books to publishers
            publisher1.getPublishedBooks().add(book1);
            publisher2.getPublishedBooks().add(book2);
            publisher3.getPublishedBooks().add(book3);
            publisher4.getPublishedBooks().add(book4);
            publisher5.getPublishedBooks().add(book5);

            publisherRepo.update(publisher1);
            publisherRepo.update(publisher2);
            publisherRepo.update(publisher3);
            publisherRepo.update(publisher4);
            publisherRepo.update(publisher5);

            fiction.getBooks().add(book1);
            fantasy.getBooks().add(book2);
            science.getBooks().add(book3);
            mystery.getBooks().add(book4);
            fantasy.getBooks().add(book5);

            categoryRepo.update(fiction);
            categoryRepo.update(nonFiction);
            categoryRepo.update(mystery);
            categoryRepo.update(science);
            categoryRepo.update(fantasy);

            // Initialize Members and associate Loans and Reservations
            Member member1 = new Member(1, "Alice", "alice@yahoo.com", "1112223333");
            Member member2 = new Member(2, "Bob", "bob@yahoo.com", "4445556666");
            Member member3 = new Member(3, "Carol", "carol@yahoo.com", "7778889999");
            Member member4 = new Member(4, "Dave", "dave@yahoo.com", "1231231234");
            Member member5 = new Member(5, "Eve", "eve@yahoo.com", "9876543210");

            memberRepo.add(member1);
            memberRepo.add(member2);
            memberRepo.add(member3);
            memberRepo.add(member4);
            memberRepo.add(member5);

            // Initialize Loans and associate with Members
//        Loan loan1 = new Loan(1, new Date(), new Date(), null, "ACTIVE", book1, member1);
//        Loan loan2 = new Loan(2, new Date(), new Date(), null, "ACTIVE", book2, member2);
//        Loan loan3 = new Loan(3, new Date(), new Date(), null, "ACTIVE", book3, member3);
//        Loan loan4 = new Loan(4, new Date(), new Date(), null, "ACTIVE", book4, member4);
//        Loan loan5 = new Loan(5, new Date(), new Date(), null, "ACTIVE", book5, member5);
//
//        loanRepo.add(loan1);
//        loanRepo.add(loan2);
//        loanRepo.add(loan3);
//        loanRepo.add(loan4);
//        loanRepo.add(loan5);
//
//        // Associate loans with members
//        member1.getLoans().add(loan1);
//        member2.getLoans().add(loan2);
//        member3.getLoans().add(loan3);
//        member4.getLoans().add(loan4);
//        member5.getLoans().add(loan5);
//
//        member1.getLoanHistory().add(loan1);
//        member2.getLoanHistory().add(loan2);
//        member3.getLoanHistory().add(loan3);
//        member4.getLoanHistory().add(loan4);
//        member5.getLoanHistory().add(loan5);
//
//        // Initialize Reservations and associate with Members
//        Reservation reservation1 = new Reservation(1, new Date(), book3, member1);
//        Reservation reservation2 = new Reservation(2, new Date(), book1, member2);
//        Reservation reservation3 = new Reservation(3, new Date(), book5, member3);
//        Reservation reservation4 = new Reservation(4, new Date(), book2, member4);
//        Reservation reservation5 = new Reservation(5, new Date(), book4, member5);
//
//        reservationRepo.add(reservation1);
//        reservationRepo.add(reservation2);
//        reservationRepo.add(reservation3);
//        reservationRepo.add(reservation4);
//        reservationRepo.add(reservation5);
//
//        // Associate reservations with members
//        member1.getReservations().add(reservation1);
//        member2.getReservations().add(reservation2);
//        member3.getReservations().add(reservation3);
//        member4.getReservations().add(reservation4);
//        member5.getReservations().add(reservation5);

            // Initialize Staff
            Staff staff1 = new Staff(1, "Charlie", "charlieLIB@yahoo.com", "1111111111", "Librarian");
            Staff staff2 = new Staff(2, "Emily", "emilyLIB@yahoo.com", "2222222222", "Assistant Librarian");
            Staff staff3 = new Staff(3, "Frank", "frankLIB@yahoo.com", "3333333333", "Cataloguer");
            Staff staff4 = new Staff(4, "Grace", "graceLIB@yahoo.com", "4444444444", "Technician");
            Staff staff5 = new Staff(5, "Hank", "hankLIB@yahoo.com", "5555555555", "Clerk");

            staffRepo.add(staff1);
            staffRepo.add(staff2);
            staffRepo.add(staff3);
            staffRepo.add(staff4);
            staffRepo.add(staff5);

            // Initialize Reviews and associate with Books
//        Review review1 = new Review(1, 5, "Amazing book!", book1, member1);
//        Review review2 = new Review(2, 4, "Really good, but a bit long.", book2, member2);
//        Review review3 = new Review(3, 3, "Interesting ideas, but hard to follow.", book3, member3);
//        Review review4 = new Review(4, 5, "A classic mystery novel!", book4, member4);
//        Review review5 = new Review(5, 5, "Absolutely loved it!", book5, member5);
//
//        reviewRepo.add(review1);
//        reviewRepo.add(review2);
//        reviewRepo.add(review3);
//        reviewRepo.add(review4);
//        reviewRepo.add(review5);
//
//        // Associate reviews with books
//        book1.getReviews().add(review1);
//        book2.getReviews().add(review2);
//        book3.getReviews().add(review3);
//        book4.getReviews().add(review4);
//        book5.getReviews().add(review5);
        } catch (DatabaseException e) {
            System.err.println("Error initializing data");
        }
    }
}
