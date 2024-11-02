package LibraryModel;

import java.util.Date;

public class Reservation {

    private int reservationID;
    private Date reservationDate;
    private Book book;

    public Reservation(int reservationID, Date reservationDate, Book book) {
        this.reservationID = reservationID;
        this.reservationDate = reservationDate;
        this.book = book;
    }

    public int getReservationID() {
        return reservationID;
    }

    public void setReservationID(int reservationID) {
        this.reservationID = reservationID;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "reservationID=" + reservationID +
                ", reservationDate=" + reservationDate +
                ", book=" + book +
                '}';
    }
}
