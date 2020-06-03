package ch.noseryoung.lernendeverwaltung.model.user;

import androidx.annotation.NonNull;
import androidx.room.*;
import ch.noseryoung.lernendeverwaltung.model.company.Company;

import java.util.UUID;

@Entity(tableName = "users", foreignKeys =
@ForeignKey(entity=Company.class, parentColumns = "id", childColumns = "company_id"))
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

    @ColumnInfo(name = "company_id")
    public String companyId;

    public User(String id, String firstName, String lastName, String profilePicture, String companyId) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicture = profilePicture;
        this.companyId = companyId;
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
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}
