package LibraryModel;

import java.io.Serializable;

/**
 * Represents a review for a book in the library system, including a rating, comments, and associations with a specific book and member.
 */
public class Review implements HasID, Serializable {
    private static final long serialVersionUID = 1L;

    private int reviewID;
    private int rating;
    private String comments;
    private Book book;
    private Member member;

    /**
     * Constructs a new Review instance with the specified parameters.
     *
     * @param reviewID the unique ID for the review
     * @param rating   the rating given in the review (out of 5)
     * @param comments additional comments about the book
     * @param book     the book being reviewed
     * @param member   the member who wrote the review
     */
    public Review(int reviewID, int rating, String comments, Book book, Member member) {
        this.reviewID = reviewID;
        this.rating = rating;
        this.comments = comments;
        this.book = book;
        this.member = member;
    }

    /**
     * Gets the unique ID of the review.
     *
     * @return the review ID
     */
    @Override
    public int getID() {
        return reviewID;
    }

    /**
     * Sets the unique ID for the review.
     *
     * @param reviewID the new review ID
     */
    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    /**
     * Gets the rating given in the review.
     *
     * @return the rating of the review
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the rating for the review.
     *
     * @param rating the new rating for the review
     */
    public void setRating(int rating) {
        this.rating = rating;
    }

    /**
     * Gets the comments provided in the review.
     *
     * @return the review comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * Sets the comments for the review.
     *
     * @param comments the new comments for the review
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * Gets the book associated with this review.
     *
     * @return the reviewed book
     */
    public Book getBook() {
        return book;
    }

    /**
     * Sets the book associated with this review.
     *
     * @param book the new book for this review
     */
    public void setBook(Book book) {
        this.book = book;
    }

    /**
     * Gets the member who wrote this review.
     *
     * @return the member who wrote the review
     */
    public Member getMember() {
        return member;
    }

    /**
     * Sets the member who wrote this review.
     *
     * @param member the new member for this review
     */
    public void setMember(Member member) {
        this.member = member;
    }

    /**
     * Returns a string representation of the review, including review ID, rating, comments, book, and member details.
     *
     * @return a string with the review details
     */
    @Override
    public String toString() {
        return "Review{" +
                "reviewID=" + reviewID +
                ", rating=" + rating +
                ", comments='" + comments + '\'' +
                ", book=" + book +
                ", member=" + member +
                '}';
    }
}
