package LibraryModel;

public class Review implements HasID{

    private int reviewID;
    private int rating;
    private String comments;
    private Book book;
    private Member member;

    public Review(int reviewID, int rating, String comments, Book book, Member member) {
        this.reviewID = reviewID;
        this.rating = rating;
        this.comments = comments;
        this.book = book;
        this.member = member;
    }

    @Override
    public int getID() {
        return reviewID;
    }

    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        return "Review{" +
                "reviewID=" + reviewID +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", book=" + book +
                '}';
    }
}
