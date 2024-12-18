package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Category;
import LibraryRepository.DataBaseRepository.DBRepository;

public class CategoryDBRepository extends DBRepository<Category> {

    public CategoryDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    @Override
    protected String getTableName() {
        return "Category";
    }

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
