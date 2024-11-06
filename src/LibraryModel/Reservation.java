package LibraryModel;

import java.util.Date;

public class Reservation implements HasID{

    private int reservationID;
    private Date reservationDate;
    private Book book;
    private Member member;

    public Reservation(int reservationID, Date reservationDate, Book book, Member member) {
        this.reservationID = reservationID;
        this.reservationDate = reservationDate;
        this.book = book;
        this.member = member;
    }

    @Override
    public int getID() {
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
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
