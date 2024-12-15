package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Book;
import LibraryModel.Member;
import LibraryModel.Review;
import LibraryRepository.DataBaseRepository.DBRepository;

public class ReviewDBRepository extends DBRepository<Review> {

    private final MemberDBRepository memberDBRepository;
    private final BookDBRepository bookDBRepository;

    public ReviewDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
        this.memberDBRepository = new MemberDBRepository(databaseUrl, username, password);
        this.bookDBRepository = new BookDBRepository(databaseUrl, username, password);
    }

    @Override
    protected String getTableName() {
        return "Review";
    }

    @Override
    protected Review mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            Book book = bookDBRepository.get(resultSet.getInt(("ID_book")));
            Member member = memberDBRepository.get(resultSet.getInt(("ID_member")));

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

    @Override
    public void add(Review review) throws DatabaseException {
        String query = "INSERT INTO Review (Rating, Comments, ID_book, ID_member) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, review.getRating());
            statement.setString(2, review.getComments());
            statement.setInt(3, review.getBook().getID());
            statement.setInt(4, review.getMember().getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

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
