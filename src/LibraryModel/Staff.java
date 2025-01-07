package LibraryModel;

import java.io.Serializable;

/**
 * Represents a staff member in the library system, with a specific position in the organization.
 * Extends the Person class.
 */
public class Staff extends Person implements Serializable {

    private String position;

    /**
     * Constructs a new Staff instance with the specified parameters.
     *
     * @param ID          the unique ID for the staff member
     * @param name        the name of the staff member
     * @param email       the email address of the staff member
     * @param phoneNumber the phone number of the staff member
     * @param position    the position or role of the staff member in the library
     */
    public Staff(int ID, String name, String email, String phoneNumber, String position) {
        super(ID, name, email, phoneNumber);
        this.position = position;
    }

    /**
     * Gets the position or role of the staff member in the library.
     *
     * @return the position of the staff member
     */
    public String getPosition() {
        return position;
    }

    /**
     * Sets the position or role of the staff member in the library.
     *
     * @param position the new position of the staff member
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * Returns a string representation of the staff member, including their position.
     *
     * @return a string with the staff member's details
     */
    @Override
    public String toString() {
        return "Staff {\n" +
                "  ID             : " + ID + "\n" +
                "  Name           : '" + name + "'\n" +
                "  Email          : '" + email + "'\n" +
                "  Phone Number   : '" + phoneNumber + "'\n" +
                "  Position       : '" + position + "'\n" +
                "}";
    }

}
