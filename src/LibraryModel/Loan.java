package LibraryModel;

import java.util.Date;

public class Loan {

    private int loanID;
    private Date loanDate;
    private Date dueDate;
    private Date returnDate;
    private String status;
    private Book book;
    private Member member;

    public Loan(int loanID, Date loanDate, Date dueDate, Date returnDate, String status, Book book, Member member) {
        this.loanID = loanID;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.book = book;
        this.member = member;
    }

    public int getLoanID() {
        return loanID;
    }

    public void setLoanID(int loanID) {
        this.loanID = loanID;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public boolean isOverdue() {
        return !returnDate.after(dueDate);
    }

}
