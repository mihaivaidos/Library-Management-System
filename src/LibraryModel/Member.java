package LibraryModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a library member, including the member's active loans, reservations, and loan history.
 * Extends the Person class.
 */
public class Member extends Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Loan> loans;
    private List<Reservation> reservations;
    private List<Loan> loanHistory;

    /**
     * Constructs a new Member instance with the specified parameters.
     *
     * @param ID          the unique ID for the member
     * @param name        the name of the member
     * @param email       the email address of the member
     * @param phoneNumber the phone number of the member
     */
    public Member(int ID, String name, String email, String phoneNumber) {
        super(ID, name, email, phoneNumber);
        this.loans = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.loanHistory = new ArrayList<>();
    }

    /**
     * Gets the list of active loans for the member.
     *
     * @return a list of active loans
     */
    public List<Loan> getLoans() {
        return loans;
    }

    /**
     * Sets the list of active loans for the member.
     *
     * @param loans the new list of active loans
     */
    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    /**
     * Gets the list of active reservations for the member.
     *
     * @return a list of active reservations
     */
    public List<Reservation> getReservations() {
        return reservations;
    }

    /**
     * Sets the list of active reservations for the member.
     *
     * @param reservations the new list of reservations
     */
    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    /**
     * Gets the loan history of the member, which includes all past loans.
     *
     * @return a list of past loans
     */
    public List<Loan> getLoanHistory() {
        return loanHistory;
    }

    /**
     * Sets the loan history for the member.
     *
     * @param loanHistory the new loan history list
     */
    public void setLoanHistory(List<Loan> loanHistory) {
        this.loanHistory = loanHistory;
    }

    /**
     * Returns a string representation of the member, including lists of active loans, reservations, and loan history.
     *
     * @return a string with the member's loans, reservations, and loan history
     */
    @Override
    public String toString() {
        return "Member {\n" +
                "  ID             : " + ID + "\n" +
                "  Name           : '" + name + "'\n" +
                "  Email          : '" + email + "'\n" +
                "  Phone Number   : '" + phoneNumber + "'\n" +
                "  Loans          : " + loans + "\n" +
                "  Reservations   : " + reservations + "\n" +
                "  Loan History   : " + loanHistory + "\n" +
                "}";
    }
}
