package LibraryModel;

/**
 * Represents a person in the library system with basic contact information.
 * This abstract class provides common properties for specific types of persons, such as members or authors.
 */
public abstract class Person implements HasID {
    private int ID;
    public String name;
    private String email;
    private String phoneNumber;

    /**
     * Constructs a new Person instance with the specified parameters.
     *
     * @param ID          the unique identifier for the person
     * @param name        the name of the person
     * @param email       the email address of the person
     * @param phoneNumber the phone number of the person
     */
    public Person(int ID, String name, String email, String phoneNumber) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets the unique ID of the person.
     *
     * @return the person's ID
     */
    public int getID() {
        return ID;
    }

    /**
     * Sets the unique ID for the person.
     *
     * @param ID the new ID for the person
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * Gets the name of the person.
     *
     * @return the person's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the person.
     *
     * @param name the new name for the person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the email address of the person.
     *
     * @return the person's email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the person.
     *
     * @param email the new email address for the person
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the phone number of the person.
     *
     * @return the person's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets the phone number of the person.
     *
     * @param phoneNumber the new phone number for the person
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Returns a string representation of the person, including ID, name, email, and phone number.
     *
     * @return a string with the person's ID, name, email, and phone number
     */
    @Override
    public String toString() {
        return "Person{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
