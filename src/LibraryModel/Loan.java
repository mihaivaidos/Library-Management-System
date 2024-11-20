package LibraryModel;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a loan transaction in the library system, including details about
 * the loan date, due date, return date, status, the book loaned, and the member who borrowed it.
 */
public class Loan implements HasID, Serializable {
    private static final long serialVersionUID = 1L;

    private int loanID;
    private Date loanDate;
    private Date dueDate;
    private Date returnDate;
    private String status;
    private Book book;
    private Member member;

    /**
     * Constructs a new Loan instance with the specified parameters.
     *
     * @param loanID      the unique ID for the loan
     * @param loanDate    the date the loan was issued
     * @param dueDate     the date by which the loaned book is due
     * @param returnDate  the date the book was returned
     * @param status      the current status of the loan (e.g., active, returned, overdue)
     * @param book        the book that is loaned
     * @param member      the member who borrowed the book
     */
    public Loan(int loanID, Date loanDate, Date dueDate, Date returnDate, String status, Book book, Member member) {
        this.loanID = loanID;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.book = book;
        this.member = member;
    }

    /**
     * Gets the unique ID of the loan.
     *
     * @return the loan ID
     */
    @Override
    public int getID() {
        return loanID;
    }

    /**
     * Sets the unique ID for the loan.
     *
     * @param loanID the new ID for the loan
     */
    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    /**
     * Gets the date the loan was issued.
     *
     * @return the loan date
     */
    public Date getLoanDate() {
        return loanDate;
    }

    /**
     * Sets the date the loan was issued.
     *
     * @param loanDate the new loan date
     */
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * Gets the due date of the loan.
     *
     * @return the due date
     */
    public Date getDueDate() {
        return dueDate;
    }

    /**
     * Sets the due date of the loan.
     *
     * @param dueDate the new due date
     */
    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * Gets the date the book was returned.
     *
     * @return the return date
     */
    public Date getReturnDate() {
        return returnDate;
    }

    /**
     * Sets the date the book was returned.
     *
     * @param returnDate the new return date
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    /**
     * Gets the current status of the loan.
     *
     * @return the loan status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the loan.
     *
     * @param status the new status of the loan
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the book associated with this loan.
     *
     * @return the loaned book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book associated with this loan.
     *
     * @param book the new book for the loan
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the member who borrowed the book.
     *
     * @return the member who borrowed the book
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member who borrowed the book.
     *
     * @param member the new member for the loan
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Returns a string representation of the loan, including loan ID, dates, status, book, and member details.
     *
     * @return a string with the loan's details
     */
    @Override
    public String toString() {
        return "Loan{" +
                "loanID=" + loanID +
                ", loanDate=" + loanDate +
                ", dueDate=" + dueDate +
                ", returnDate=" + returnDate +
                ", status='" + status + '\'' +
                ", book=" + book +
                ", member=" + member +
                '}';
    }
}
