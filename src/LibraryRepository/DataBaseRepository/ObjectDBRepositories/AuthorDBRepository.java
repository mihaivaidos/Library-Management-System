package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Author;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing authors in the database.
 */
public class AuthorDBRepository extends DBRepository<Author> {

    /**
     * Constructs an AuthorDBRepository and establishes a database connection.
     *
     * @param databaseUrl the URL of the database.
     * @param username    the username for the database connection.
     * @param password    the password for the database connection.
     * @throws DatabaseException if a database connection error occurs.
     */
    public AuthorDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    /**
     * Maps a row from the ResultSet to an Author object.
     *
     * @param resultSet the ResultSet containing the row data.
     * @return the Author object created from the row.
     * @throws DatabaseException if an error occurs while mapping the data.
     */
    @Override
    protected Author mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            return new Author(
                    resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Email"),
                    resultSet.getString("Phone_number")
            );
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new author to the database.
     *
     * @param author the author to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void add(Author author) throws DatabaseException {
        String query = "INSERT INTO Author (ID, Name, Email, Phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, author.getID());
            statement.setString(2, author.getName());
            statement.setString(3, author.getEmail());
            statement.setString(4, author.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing author in the database.
     *
     * @param author the author to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void update(Author author) throws DatabaseException {
        String query = "UPDATE Author SET Name = ?, Email = ?, Phone_number = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getEmail());
            statement.setString(3, author.getPhoneNumber());
            statement.setInt(4, author.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }

        AuthorBooksDBRepository authorBooksDBRepository = new AuthorBooksDBRepository(connection);
        authorBooksDBRepository.updateAuthorBooks(author.getBooks(), author.getID());
    }

    /**
     * Specifies the name of the table associated with this repository.
     *
     * @return the name of the table.
     */
    @Override
    protected String getTableName() {
        return "Author";
    }
}
