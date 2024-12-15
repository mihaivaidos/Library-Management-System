package LibraryRepository;

import Exceptions.DatabaseException;
import LibraryModel.*;
import LibraryRepository.DataBaseRepository.ObjectDBRepositories.*;

import java.nio.file.Paths;

/**
 * A class that represents dynamic repository selection.
 * It creates and initializes repositories of the selected type.
 */
public class RepositoryFactory {

    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/LibraryManagementSystem";
    private static final String DATABASE_USER = "postgres";
    private static final String DATABASE_PASSWORD = "password";

    /**
     * Creates a repository based on the repositoryType that is given (in-memory, file or database)
     *
     * @param cls the Class that the repository is created for
     * @param repositoryType the type of the repository
     * @return a new repository
     */
    public static <T extends HasID> IRepository<T> createRepository(Class<T> cls, String repositoryType) throws DatabaseException {
        return switch (repositoryType.toLowerCase()) {
            case "inmemory" -> new InMemoryRepository<>();
            case "file" -> {
                String fileName = cls.getSimpleName().toLowerCase() + ".txt";
                String filePath = Paths.get("src", "LibraryRepository", "FileRepositories", fileName).toString();
                System.out.println("Creating FileRepository at: " + filePath); // Debugging output
                yield new FileRepository<>(filePath);
            }
            case "database" -> createDatabaseRepository(cls);
            default -> throw new IllegalArgumentException("Invalid repository type: " + repositoryType);
        };
    }

    /**
     * Creates a database repository for the specified class.
     *
     * @param cls the Class that the repository is created for
     * @return a database repository for the specified class
     */
    @SuppressWarnings("unchecked")
    private static <T extends HasID> IRepository<T> createDatabaseRepository(Class<T> cls) throws DatabaseException {
        if (cls.equals(Author.class)) {
            return (IRepository<T>) new AuthorDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } else if (cls.equals(Book.class)) {
            return (IRepository<T>) new BookDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } else if (cls.equals(Category.class)) {
            return (IRepository<T>) new CategoryDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } else if (cls.equals(Member.class)) {
            return (IRepository<T>) new MemberDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } else if (cls.equals(Publisher.class)) {
            return (IRepository<T>) new PublisherDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } else if (cls.equals(Staff.class)) {
            return (IRepository<T>) new StaffDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } else if (cls.equals(Loan.class)) {
            return (IRepository<T>) new LoanDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } else if (cls.equals(Reservation.class)) {
            return (IRepository<T>) new ReservationDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        } else if (cls.equals(Review.class)) {
            return (IRepository<T>) new ReviewDBRepository(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        }

        throw new IllegalArgumentException("No database repository found for class: " + cls.getSimpleName());
    }

}
