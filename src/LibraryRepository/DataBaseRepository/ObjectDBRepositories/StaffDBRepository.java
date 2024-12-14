package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Staff;
import LibraryRepository.DataBaseRepository.DBRepository;

public class StaffDBRepository extends DBRepository<Staff> {

    public StaffDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    @Override
    protected String getTableName() {
        return "Staff";
    }

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

    @Override
    public void add(Staff staff) throws DatabaseException {
        String query = "INSERT INTO Staff (Name, Email, Phone_number, Position) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, staff.getName());
            statement.setString(2, staff.getEmail());
            statement.setString(3, staff.getPhoneNumber());
            statement.setString(4, staff.getPosition());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

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
