package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Author;
import LibraryRepository.DataBaseRepository.DBRepository;

public class AuthorDBRepository extends DBRepository<Author> {

    public AuthorDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

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

    @Override
    public void add(Author author) throws DatabaseException {
        String query = "INSERT INTO Author (Name, Email, Phone_number) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, author.getName());
            statement.setString(2, author.getEmail());
            statement.setString(3, author.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

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

    @Override
    protected String getTableName() {
        return "Author";
    }
}
