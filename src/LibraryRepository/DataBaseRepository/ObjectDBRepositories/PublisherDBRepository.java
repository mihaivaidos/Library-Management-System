package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Publisher;
import LibraryRepository.DataBaseRepository.DBRepository;

public class PublisherDBRepository extends DBRepository<Publisher> {

    public PublisherDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    @Override
    protected String getTableName() {
        return "Publisher";
    }

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
