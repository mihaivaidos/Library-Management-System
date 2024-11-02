package LibraryModel;

public class Review {

    private int reviewID;
    private int rating;
    private String comments;
    private Book book;

    public Review(int reviewID, int rating, String comments, Book book) {
        this.reviewID = reviewID;
        this.rating = rating;
        this.comments = comments;
        this.book = book;
    }

    public int getReviewID() {
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
