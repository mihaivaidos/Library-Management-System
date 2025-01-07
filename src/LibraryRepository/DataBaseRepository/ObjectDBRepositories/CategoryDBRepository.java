package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Category;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing categories in the database.
 */
public class CategoryDBRepository extends DBRepository<Category> {

    /**
     * Constructs a CategoryDBRepository with the given database connection details.
     *
     * @param databaseUrl the URL of the database.
     * @param username    the username for the database connection.
     * @param password    the password for the database connection.
     * @throws DatabaseException if a database connection error occurs.
     */
    public CategoryDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    /**
     * Returns the name of the table associated with this repository.
     *
     * @return the name of the table.
     */
    @Override
    protected String getTableName() {
        return "Category";
    }

    /**
     * Maps a row from the ResultSet to a Category object.
     *
     * @param resultSet the ResultSet containing the row data.
     * @return the Category object created from the row.
     * @throws DatabaseException if an error occurs while mapping the data.
     */
    @Override
    protected Category mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            return new Category(
                    resultSet.getInt("ID"),
                    resultSet.getString("Name"),
                    resultSet.getString("Description")
            );
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new category to the database.
     *
     * @param category the category to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void add(Category category) throws DatabaseException {
        String query = "INSERT INTO Category (ID, Name, Description) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, category.getID());
            statement.setString(2, category.getCategoryName());
            statement.setString(3, category.getDescription());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing category in the database.
     *
     * @param category the category to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void update(Category category) throws DatabaseException {
        String query = "UPDATE Category SET Name = ?, Description = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, category.getCategoryName());
            statement.setString(2, category.getDescription());
            statement.setInt(3, category.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }
}
