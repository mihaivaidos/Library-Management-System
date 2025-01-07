package LibraryModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * Represents a reservation in the library system, associating a member with a reserved book and a reservation date.
 */
public class Reservation implements HasID, Serializable {

    private int reservationID;
    private LocalDate reservationDate;
    private Book book;
    private Member member;

    /**
     * Constructs a new Reservation instance with the specified parameters.
     *
     * @param reservationID   the unique ID for the reservation
     * @param reservationDate the date the reservation was made
     * @param book            the book reserved
     * @param member          the member who made the reservation
     */
    public Reservation(int reservationID, LocalDate reservationDate, Book book, Member member) {
        this.reservationID = reservationID;
        this.reservationDate = reservationDate;
        this.book = book;
        this.member = member;
    }

    /**
     * Gets the unique ID of the reservation.
     *
     * @return the reservation ID
     */
    @Override
    public int getID() {
        return reservationID;
    }

    /**
     * Sets the unique ID for the reservation.
     *
     * @param reservationID the new reservation ID
     */
    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    /**
     * Gets the date when the reservation was made.
     *
     * @return the reservation date
     */
    public LocalDate getReservationDate() {
        return reservationDate;
    }

    /**
     * Sets the date when the reservation was made.
     *
     * @param reservationDate the new reservation date
     */
    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    /**
     * Gets the book associated with this reservation.
     *
     * @return the reserved book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book associated with this reservation.
     *
     * @param book the new reserved book
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the member who made the reservation.
     *
     * @return the member who made the reservation
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member who made the reservation.
     *
     * @param member the new member for this reservation
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Returns a string representation of the reservation, including reservation ID, date, book, and member details.
     *
     * @return a string with reservation details
     */
    @Override
    public String toString() {
        return "Reservation {\n" +
                "  Reservation ID   : " + reservationID + "\n" +
                "  Reservation Date : " + reservationDate + "\n" +
                "  Book             : " + book + "\n" +
                "  Member           : " + member + "\n" +
                "}";
    }

}
