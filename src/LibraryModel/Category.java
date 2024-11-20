package LibraryModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a category in the library system with a unique ID, name, description,
 * and a list of books associated with the category.
 */
public class Category implements HasID, Serializable {
    private static final long serialVersionUID = 1L;

    private int categoryID;
    private String categoryName;
    private String description;
    private List<Book> books;

    /**
     * Constructs a new Category instance with the specified parameters.
     *
     * @param categoryID   the unique ID for the category
     * @param categoryName the name of the category
     * @param description  the description of the category
     */
    public Category(int categoryID, String categoryName, String description) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.description = description;
        this.books = new ArrayList<>();
    }

    /**
     * Gets the unique ID of the category.
     *
     * @return the category ID
     */
    @Override
    public int getID() {
        return categoryID;
    }

    /**
     * Sets the unique ID for the category.
     *
     * @param categoryID the new ID for the category
     */
    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    /**
     * Gets the name of the category.
     *
     * @return the category name
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the name of the category.
     *
     * @param categoryName the new name for the category
     */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /**
     * Gets the description of the category.
     *
     * @return the category description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the category.
     *
     * @param description the new description for the category
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the list of books associated with this category.
     *
     * @return a list of books in this category
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the list of books associated with this category.
     *
     * @param books the new list of books for the category
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Returns a string representation of the category, including the category ID,
     * name, description and the list of books.
     *
     * @return a string with the category's ID, name, description and books
     */
    @Override
    public String toString() {
        return "Category{" +
                "categoryID=" + categoryID +
                ", categoryName='" + categoryName + '\'' +
                ", description='" + description + '\'' +
                ", books=" + books +
                '}';
    }
}
