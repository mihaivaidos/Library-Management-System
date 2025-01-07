package LibraryRepository.DataBaseRepository;

import Exceptions.DatabaseException;
import LibraryModel.HasID;
import LibraryRepository.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for database repositories, implementing common CRUD operations.
 *
 * @param <T> The type of entity managed by the repository.
 */
public abstract class DBRepository<T extends HasID> implements IRepository<T>, AutoCloseable {

    protected Connection connection;

    /**
     * Constructs a DBRepository and establishes a connection to the database.
     *
     * @param DBUrl      the database URL.
     * @param DBUser     the database username.
     * @param DBPassword the database password.
     * @throws DatabaseException if a database connection error occurs.
     */
    public DBRepository(String DBUrl, String DBUser, String DBPassword) throws DatabaseException {
        try {
            this.connection = DriverManager.getConnection(DBUrl, DBUser, DBPassword);
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Closes the database connection.
     *
     * @throws Exception if an error occurs while closing the connection.
     */
    @Override
    public void close() throws Exception {
        connection.close();
    }

    /**
     * Specifies the table name in the database.
     * Subclasses must override this method to provide the table name.
     *
     * @return the name of the table.
     */
    protected abstract String getTableName();

    /**
     * Maps a row in the ResultSet to an instance of type T.
     * Subclasses must implement this method to define how entities are created from database rows.
     *
     * @param resultSet the ResultSet containing the row data.
     * @return an instance of T representing the row.
     * @throws SQLException if an error occurs while accessing the ResultSet.
     * @throws DatabaseException if mapping errors occur.
     */
    protected abstract T mapResultSetToEntity(ResultSet resultSet) throws SQLException, DatabaseException;

    /**
     * Retrieves an entity by its ID.
     *
     * @param id the ID of the entity to retrieve.
     * @return the entity with the specified ID, or null if no such entity exists.
     * @throws DatabaseException if a database error occurs.
     */
    @Override
    public T get(int id) throws DatabaseException {
        String SQL = "SELECT * FROM " + getTableName() + " WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return mapResultSetToEntity(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Retrieves all entities from the database.
     *
     * @return a List of all entities in the table.
     * @throws DatabaseException if a database error occurs.
     */
    @Override
    public List<T> getAll() throws DatabaseException {
        String SQL = "SELECT * FROM " + getTableName();
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            ResultSet resultSet = statement.executeQuery();
            List<T> objects = new ArrayList<>();
            while (resultSet.next()) {
                objects.add(mapResultSetToEntity(resultSet));
            }
            return objects;
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an entity by its ID.
     *
     * @param id the ID of the entity to delete.
     * @throws DatabaseException if a database error occurs.
     */
    @Override
    public void delete(int id) throws DatabaseException {
        String SQL = "DELETE FROM " + getTableName() + " WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(SQL)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }
}
