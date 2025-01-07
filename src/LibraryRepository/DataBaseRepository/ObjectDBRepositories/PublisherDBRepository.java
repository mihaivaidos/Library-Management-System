package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Publisher;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing publishers in the database.
 */
public class PublisherDBRepository extends DBRepository<Publisher> {

    /**
     * Constructs a PublisherDBRepository with the given database connection details.
     *
     * @param databaseUrl the URL of the database.
     * @param username    the username for the database connection.
     * @param password    the password for the database connection.
     * @throws DatabaseException if a database connection error occurs.
     */
    public PublisherDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    /**
     * Returns the name of the table associated with this repository.
     *
     * @return the name of the table.
     */
    @Override
    protected String getTableName() {
        return "Publisher";
    }

    /**
     * Maps a row from the ResultSet to a Publisher object.
     *
     * @param resultSet the ResultSet containing the row data.
     * @return the Publisher object created from the row.
     * @throws DatabaseException if an error occurs while mapping the data.
     */
    @Override
    protected Publisher mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            return new Publisher(
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
     * Adds a new publisher to the database.
     *
     * @param publisher the publisher to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void add(Publisher publisher) throws DatabaseException {
        String query = "INSERT INTO Publisher (ID, Name, Email, Phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, publisher.getID());
            statement.setString(2, publisher.getName());
            statement.setString(3, publisher.getEmail());
            statement.setString(4, publisher.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing publisher in the database.
     *
     * @param publisher the publisher to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void update(Publisher publisher) throws DatabaseException {
        String query = "UPDATE Publisher SET Name = ?, Email = ?, Phone_number = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, publisher.getName());
            statement.setString(2, publisher.getEmail());
            statement.setString(3, publisher.getPhoneNumber());
            statement.setInt(4, publisher.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }
}
