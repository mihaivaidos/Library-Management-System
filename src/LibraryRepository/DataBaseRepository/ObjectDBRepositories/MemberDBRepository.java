package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Member;
import LibraryRepository.DataBaseRepository.DBRepository;

public class MemberDBRepository extends DBRepository<Member> {

    public MemberDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    @Override
    protected String getTableName() {
        return "Member";
    }

    @Override
    protected Member mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            return new Member(
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
    public void add(Member member) throws DatabaseException {
        String query = "INSERT INTO Member (ID, Name, Email, Phone_number) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, member.getID());
            statement.setString(2, member.getName());
            statement.setString(3, member.getEmail());
            statement.setString(4, member.getPhoneNumber());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Member member) throws DatabaseException {
        String query = "UPDATE Member SET Name = ?, Email = ?, Phone_number = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, member.getName());
            statement.setString(2, member.getEmail());
            statement.setString(3, member.getPhoneNumber());
            statement.setInt(4, member.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }
}
