package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Book;
import LibraryModel.Member;
import LibraryModel.Review;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing CRUD operations on the Review table in the database.
 * Extends the generic DBRepository class to handle Review entities.
 */
public class ReviewDBRepository extends DBRepository<Review> {

    private final MemberDBRepository memberDBRepository;
    private final BookDBRepository bookDBRepository;

    /**
     * Constructs a new ReviewDBRepository with the specified database connection details.
     *
     * @param databaseUrl the URL of the database.
     * @param username the username for database authentication.
     * @param password the password for database authentication.
     * @throws DatabaseException if a database connection cannot be established.
     */
    public ReviewDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
        this.memberDBRepository = new MemberDBRepository(databaseUrl, username, password);
        this.bookDBRepository = new BookDBRepository(databaseUrl, username, password);
    }

    /**
     * Returns the name of the database table managed by this repository.
     *
     * @return the name of the Review table.
     */
    @Override
    protected String getTableName() {
        return "Review";
    }

    /**
     * Maps a ResultSet row to a Review entity.
     *
     * @param resultSet the ResultSet to map.
     * @return the mapped Review entity.
     * @throws DatabaseException if a database error occurs during mapping.
     */
    @Override
    protected Review mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            Book book = bookDBRepository.get(resultSet.getInt("ID_book"));
            Member member = memberDBRepository.get(resultSet.getInt("ID_member"));

            return new Review(
                    resultSet.getInt("ID"),
                    resultSet.getInt("Rating"),
                    resultSet.getString("Comments"),
                    book,
                    member
            );
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new Review entity to the database.
     *
     * @param review the Review to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void add(Review review) throws DatabaseException {
        String query = "INSERT INTO Review (ID, Rating, Comments, ID_book, ID_member) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, review.getID());
            statement.setInt(2, review.getRating());
            statement.setString(3, review.getComments());
            statement.setInt(4, review.getBook().getID());
            statement.setInt(5, review.getMember().getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing Review entity in the database.
     *
     * @param review the Review to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void update(Review review) throws DatabaseException {
        String query = "UPDATE Review SET Rating = ?, Comments = ?, ID_book = ?, ID_member = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, review.getRating());
            statement.setString(2, review.getComments());
            statement.setInt(3, review.getBook().getID());
            statement.setInt(4, review.getMember().getID());
            statement.setInt(5, review.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }
}
