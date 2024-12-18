package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Book;
import LibraryModel.Loan;
import LibraryModel.Member;
import LibraryRepository.DataBaseRepository.DBRepository;

public class LoanDBRepository extends DBRepository<Loan> {

    private final MemberDBRepository memberDBRepository;
    private final BookDBRepository bookDBRepository;

    public LoanDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
        super(databaseUrl, username, password);
        this.memberDBRepository = new MemberDBRepository(databaseUrl, username, password);
        this.bookDBRepository = new BookDBRepository(databaseUrl, username, password);
    }

    @Override
    protected String getTableName() {
        return "Loan";
    }

    @Override
    protected Loan mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            Book book = bookDBRepository.get(resultSet.getInt(("ID_book")));
            Member member = memberDBRepository.get(resultSet.getInt(("ID_member")));

            return new Loan(
                    resultSet.getInt("ID"),
                    resultSet.getDate("Loan_date").toLocalDate(),
                    resultSet.getDate("Due_date").toLocalDate(),
                    resultSet.getDate("Return_date") != null ? resultSet.getDate("Return_date").toLocalDate() : null,
                    resultSet.getString("Status"),
                    book,
                    member
            );
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    @Override
    public void add(Loan loan) throws DatabaseException {
        String query = "INSERT INTO Loan (ID, Loan_date, Due_date, Return_date, Status, ID_book, ID_member) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, loan.getID());
            statement.setDate(2, Date.valueOf(loan.getLoanDate()));
            statement.setDate(3, Date.valueOf(loan.getDueDate()));
            statement.setDate(4, loan.getReturnDate() != null ? Date.valueOf(loan.getReturnDate()) : null);
            statement.setString(5, loan.getStatus());
            statement.setInt(6, loan.getBook().getID());
            statement.setInt(7, loan.getMember().getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }

    @Override
    public void update(Loan loan) throws DatabaseException {
        String query = "UPDATE Loan SET Loan_date = ?, Due_date = ?, Return_date = ?, Status = ?, ID_book = ?, ID_member = ? WHERE ID = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(loan.getLoanDate()));
            statement.setDate(2, Date.valueOf(loan.getDueDate()));
            statement.setDate(3, loan.getReturnDate() != null ? Date.valueOf(loan.getReturnDate()) : null);
            statement.setString(4, loan.getStatus());
            statement.setInt(5, loan.getBook().getID());
            statement.setInt(6, loan.getMember().getID());
            statement.setInt(7, loan.getID());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Database error occurred: " + e.getMessage(), e);
        }
    }
}
