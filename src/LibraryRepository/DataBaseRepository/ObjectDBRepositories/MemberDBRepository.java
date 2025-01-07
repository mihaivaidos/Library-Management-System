package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Member;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing members in the database.
 */
public class MemberDBRepository extends DBRepository<Member> {

    /**
     * Constructs a MemberDBRepository with the given database connection details.
     *
     * @param databaseUrl the URL of the database.
     * @param username    the username for the database connection.
     * @param password    the password for the database connection.
     * @throws DatabaseException if a database connection error occurs.
     */
    public MemberDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
    }

    /**
     * Returns the name of the table associated with this repository.
     *
     * @return the name of the table.
     */
    @Override
    protected String getTableName() {
        return "Member";
    }

    /**
     * Maps a row from the ResultSet to a Member object.
     *
     * @param resultSet the ResultSet containing the row data.
     * @return the Member object created from the row.
     * @throws DatabaseException if an error occurs while mapping the data.
     */
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

    /**
     * Adds a new member to the database.
     *
     * @param member the member to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
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

    /**
     * Updates an existing member in the database.
     *
     * @param member the member to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
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
