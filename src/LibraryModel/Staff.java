package LibraryModel;

public class Staff extends Person {

    private String position;

    public Staff(int ID, String name, String email, String phoneNumber, String position) {
        super(ID, name, email, phoneNumber);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "position='" + position + '\'' +
                '}';
    }
}
