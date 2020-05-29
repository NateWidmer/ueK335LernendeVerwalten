package ch.noseryoung.lernendeverwaltung;

public class User {

    private String firstName;
    private String lastName;

    public User(String firstName, String vorname) {
        this.firstName = firstName;
        this.lastName = vorname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
