package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Book;
import LibraryModel.Member;
import LibraryModel.Reservation;
import LibraryRepository.DataBaseRepository.DBRepository;

public class ReservationDBRepository extends DBRepository<Reservation> {

    private final MemberDBRepository memberDBRepository;
    private final BookDBRepository bookDBRepository;

    public ReservationDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
        this.memberDBRepository = new MemberDBRepository(databaseUrl, username, password);
        this.bookDBRepository = new BookDBRepository(databaseUrl, username, password);
    }

    @Override
    protected String getTableName() {
        return "Reservation";
    }

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

    @Override
    public void add(Reservation reservation) throws DatabaseException {
        String query = "INSERT INTO Reservation (Reservation_date, ID_book, ID_member) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(reservation.getReservationDate()));
            statement.setInt(2, reservation.getBook().getID());
            statement.setInt(3, reservation.getMember().getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

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
