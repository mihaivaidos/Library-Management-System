package LibraryModel;

import java.util.List;

public class Member extends Person {

    private List<Loan> loans;
    private List<Reservation> reservations;

    public Member(int ID, String name, String email, String phoneNumber, List<Loan> loans, List<Reservation> reservations) {
        super(ID, name, email, phoneNumber);
        this.loans = loans;
        this.reservations = reservations;
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

    @Override
    public String toString() {
        return "Member{" +
                "loans=" + loans +
                ", reservations=" + reservations +
                '}';
    }
}
