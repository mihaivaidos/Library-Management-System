package LibraryModel;

import java.util.ArrayList;
import java.util.List;

public class Member extends Person {

    private List<Loan> loans;
    private List<Reservation> reservations;
    private List<Loan> loanHistory;

    public Member(int ID, String name, String email, String phoneNumber) {
        super(ID, name, email, phoneNumber);
        this.loans = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.loanHistory = new ArrayList<>();
    }

    public List<Loan> getLoans() {
        return loans;
    }

    public void setLoans(List<Loan> loans) {
        this.loans = loans;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    public List<Loan> getLoanHistory() {
        return loanHistory;
    }

    public void setLoanHistory(List<Loan> loanHistory) {
        this.loanHistory = loanHistory;
    }

    @Override
    public String toString() {
        return "Member{" +
                "loans=" + loans +
                ", reservations=" + reservations +
                '}';
    }
}
