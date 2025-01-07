package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;
import java.util.List;

import Exceptions.DatabaseException;
import LibraryModel.Book;
import LibraryModel.Author;
import LibraryModel.Category;
import LibraryModel.Publisher;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing books in the database.
 */
public class BookDBRepository extends DBRepository<Book> {

    private final CategoryDBRepository categoryDBRepository;
    private final PublisherDBRepository publisherDBRepository;
    private final AuthorDBRepository authorDBRepository;

    /**
     * Constructs a BookDBRepository and establishes connections to related repositories.
     *
     * @param databaseUrl the URL of the database.
     * @param username    the username for the database connection.
     * @param password    the password for the database connection.
     * @throws DatabaseException if a database connection error occurs.
     */
    public BookDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
        this.categoryDBRepository = new CategoryDBRepository(databaseUrl, username, password);
        this.publisherDBRepository = new PublisherDBRepository(databaseUrl, username, password);
        this.authorDBRepository = new AuthorDBRepository(databaseUrl, username, password);
    }

    /**
     * Maps a row from the ResultSet to a Book object.
     *
     * @param resultSet the ResultSet containing the row data.
     * @return the Book object created from the row.
     * @throws DatabaseException if an error occurs while mapping the data.
     */
    protected Book mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            int categoryID = resultSet.getInt("ID_category");
            int publisherID = resultSet.getInt("ID_publisher");
            int authorID = resultSet.getInt("ID_author");

            Category category = categoryDBRepository.get(categoryID);
            Publisher publisher = publisherDBRepository.get(publisherID);
            Author author = authorDBRepository.get(authorID);

            return new Book(
                    resultSet.getInt("ID"),
                    resultSet.getString("Title"),
                    author,
                    resultSet.getBoolean("Is_available"),
                    category,
                    publisher,
                    resultSet.getInt("Copies_available")
            );
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new book to the database.
     *
     * @param book the book to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void add(Book book) throws DatabaseException {
        String query = "INSERT INTO Book (ID, Title, ID_author, Is_available, ID_category, ID_publisher, Copies_available) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, book.getID());
            statement.setString(2, book.getBookName());
            statement.setInt(3, book.getAuthor().getID());
            statement.setBoolean(4, book.isAvailable());
            statement.setInt(5, book.getCategory().getID());
            statement.setInt(6, book.getPublisher().getID());
            statement.setInt(7, book.getCopiesAvailable());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the name of the table associated with this repository.
     *
     * @return the name of the table.
     */
    @Override
    protected String getTableName() {
        return "Book";
    }

    /**
     * Updates an existing book in the database.
     *
     * @param book the book to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void update(Book book) throws DatabaseException {
        String query = "UPDATE Book SET Title = ?, ID_author = ?, Is_available = ?, ID_category = ?, ID_publisher = ?, Copies_available = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book.getBookName());
            statement.setInt(2, book.getAuthor().getID());
            statement.setBoolean(3, book.isAvailable());
            statement.setInt(4, book.getCategory().getID());
            statement.setInt(5, book.getPublisher().getID());
            statement.setInt(6, book.getCopiesAvailable());
            statement.setInt(7, book.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }

        AuthorBooksDBRepository authorBooksDBRepository = new AuthorBooksDBRepository(connection);
        authorBooksDBRepository.updateAuthorBooks(List.of(book), book.getAuthor().getID());
    }
}
