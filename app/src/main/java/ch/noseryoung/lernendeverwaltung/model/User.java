package ch.noseryoung.lernendeverwaltung.model;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.UUID;

@Entity(tableName = "users")
public class User {

    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name="first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

    @ColumnInfo(name = "profile_picture")
    private String profilePicture;

    @ColumnInfo(name = "company")
    public String company;

    public User(String firstName, String lastName, String profilePicture, String company) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
        this.company = company;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getCompanyId() {
        return company;
    }

    public void setCompanyId(String company) {
        this.company = company;
    }
}
