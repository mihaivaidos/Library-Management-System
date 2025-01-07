package LibraryRepository.DataBaseRepository.ObjectDBRepositories;

import java.sql.*;

import Exceptions.DatabaseException;
import LibraryModel.Book;
import LibraryModel.Loan;
import LibraryModel.Member;
import LibraryRepository.DataBaseRepository.DBRepository;

/**
 * Repository class for managing loans in the database.
 */
public class LoanDBRepository extends DBRepository<Loan> {

    private final MemberDBRepository memberDBRepository;
    private final BookDBRepository bookDBRepository;

    /**
     * Constructs a LoanDBRepository with the given database connection details.
     *
     * @param databaseUrl the URL of the database.
     * @param username    the username for the database connection.
     * @param password    the password for the database connection.
     * @throws DatabaseException if a database connection error occurs.
     */
    public LoanDBRepository(String databaseUrl, String username, String password) throws DatabaseException {
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
        return "Loan";
    }

    /**
     * Maps a row from the ResultSet to a Loan object.
     *
     * @param resultSet the ResultSet containing the row data.
     * @return the Loan object created from the row.
     * @throws DatabaseException if an error occurs while mapping the data.
     */
    @Override
    protected Loan mapResultSetToEntity(ResultSet resultSet) throws DatabaseException {
        try {
            Book book = bookDBRepository.get(resultSet.getInt("ID_book"));
            Member member = memberDBRepository.get(resultSet.getInt("ID_member"));

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

    /**
     * Adds a new loan to the database.
     *
     * @param loan the loan to add.
     * @throws DatabaseException if a database error occurs during the operation.
     */
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

    /**
     * Updates an existing loan in the database.
     *
     * @param loan the loan to update.
     * @throws DatabaseException if a database error occurs during the operation.
     */
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
