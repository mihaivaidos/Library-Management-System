package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Staff;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing CRUD operations on the Staff table in the database.
 * Extends the generic DBRepository class to handle Staff entities.
 */
public class StaffDBRepository extends DBRepository<Staff> {

    /**
     * Constructs a new StaffDBRepository with the specified database connection details.
     *
     * @param databaseUrl the URL of the database.
     * @param username the username for database authentication.
     * @param password the password for database authentication.
     * @throws DatabaseException if a database connection cannot be established.
     */
    public StaffDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    /**
     * Returns the name of the database table managed by this repository.
     *
     * @return the name of the Staff table.
     */
    @Override
    protected String getTableName() {
        return "Staff";
    }

    /**
     * Maps a ResultSet row to a Staff entity.
     *
     * @param resultSet the ResultSet to map.
     * @return the mapped Staff entity.
     * @throws DatabaseException if a database error occurs during mapping.
     */
    @Override
    protected Staff mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            return new Staff(
                    resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Email"),
                    resultSet.getString("Phone_number"),
                    resultSet.getString("Position")
            );
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new Staff entity to the database.
     *
     * @param staff the Staff to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void add(Staff staff) throws DatabaseException {
        String query = "INSERT INTO Staff (ID, Name, Email, Phone_number, Position) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, staff.getID());
            statement.setString(2, staff.getName());
            statement.setString(3, staff.getEmail());
            statement.setString(4, staff.getPhoneNumber());
            statement.setString(5, staff.getPosition());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing Staff entity in the database.
     *
     * @param staff the Staff to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void update(Staff staff) throws DatabaseException {
        String query = "UPDATE Staff SET Name = ?, Email = ?, Phone_number = ?, Position = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staff.getName());
            statement.setString(2, staff.getEmail());
            statement.setString(3, staff.getPhoneNumber());
            statement.setString(4, staff.getPosition());
            statement.setInt(5, staff.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }
}