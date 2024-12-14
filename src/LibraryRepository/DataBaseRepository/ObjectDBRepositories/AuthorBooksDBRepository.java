package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import Exceptions.DatabaseException;
import LibraryModel.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AuthorBooksDBRepository {

    private final Connection connection;

    public AuthorBooksDBRepository(Connection connection) {
        this.connection = connection;
    }

    public void updateAuthorBooks(List<Book> books, int authorID) throws DatabaseException {
        String deleteQuery = "DELETE FROM AuthorBooks WHERE ID_author = ?";
        try (PreparedStatement statement = connection.prepareStatement(deleteQuery)) {
            statement.setInt(1, authorID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }

        String insertQuery = "INSERT INTO AuthorBooks (ID_book, ID_author) VALUES (?, ?)";
        for (Book book : books) {
            try (PreparedStatement statement = connection.prepareStatement(insertQuery)) {
                statement.setInt(1, book.getID());
                statement.setInt(2, authorID);
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
            }
        }
    }
}
