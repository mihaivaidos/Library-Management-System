package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Book;
import LibraryModel.Member;
import LibraryModel.Reservation;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing reservations in the database.
 */
public class ReservationDBRepository extends DBRepository<Reservation> {

    private final MemberDBRepository memberDBRepository;
    private final BookDBRepository bookDBRepository;

    /**
     * Constructs a ReservationDBRepository with the given database connection details.
     *
     * @param databaseUrl the URL of the database.
     * @param username    the username for the database connection.
     * @param password    the password for the database connection.
     * @throws DatabaseException if a database connection error occurs.
     */
    public ReservationDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
        this.memberDBRepository = new MemberDBRepository(databaseUrl, username, password);
        this.bookDBRepository = new BookDBRepository(databaseUrl, username, password);
    }

    /**
     * Returns the name of the table associated with this repository.
     *
     * @return the name of the table.
     */
    @Override
    protected String getTableName() {
        return "Reservation";
    }

    /**
     * Maps a row from the ResultSet to a Reservation object.
     *
     * @param resultSet the ResultSet containing the row data.
     * @return the Reservation object created from the row.
     * @throws DatabaseException if an error occurs while mapping the data.
     */
    @Override
    protected Reservation mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            Book book = bookDBRepository.get(resultSet.getInt(("ID_book")));
            Member member = memberDBRepository.get(resultSet.getInt(("ID_member")));

            return new Reservation(
                    resultSet.getInt("ID"),
                    resultSet.getDate("Reservation_date").toLocalDate(),
                    book,
                    member
            );
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Adds a new reservation to the database.
     *
     * @param reservation the reservation to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void add(Reservation reservation) throws DatabaseException {
        String query = "INSERT INTO Reservation (ID, Reservation_date, ID_book, ID_member) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, reservation.getID());
            statement.setDate(2, Date.valueOf(reservation.getReservationDate()));
            statement.setInt(3, reservation.getBook().getID());
            statement.setInt(4, reservation.getMember().getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    /**
     * Updates an existing reservation in the database.
     *
     * @param reservation the reservation to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
    @Override
    public void update(Reservation reservation) throws DatabaseException {
        String query = "UPDATE Reservation SET Reservation_date = ?, ID_book = ?, ID_member = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(reservation.getReservationDate()));
            statement.setInt(2, reservation.getBook().getID());
            statement.setInt(3, reservation.getMember().getID());
            statement.setInt(4, reservation.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }
}
