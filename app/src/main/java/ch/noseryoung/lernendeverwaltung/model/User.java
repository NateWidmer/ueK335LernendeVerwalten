package ch.noseryoung.lernendeverwaltung.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.room.*;

import java.util.UUID;

import ch.noseryoung.lernendeverwaltung.R;

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
    private String profilePicturePath;

    @ColumnInfo(name = "company")
    public String company;

    public User(String firstName, String lastName, String profilePicturePath, String company) {
        this.id = UUID.randomUUID().toString();
        this.firstName = firstName;
        this.lastName = lastName;
        this.profilePicturePath = profilePicturePath;
        this.company = company;
    }

    // Functions

    // Returns Profile Picture as a Bitmap / DrawableBitmap
    public Bitmap getProfilePictureAsBitmap(Context applicationContext) {
        if (this.getProfilePicturePath() != null) {
            return BitmapFactory.decodeFile(this.getProfilePicturePath());
        } else {
            Drawable avatar = ContextCompat.getDrawable(applicationContext, R.drawable.avatar);
            return ((BitmapDrawable)avatar).getBitmap();
        }
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

    public String getProfilePicturePath() {
        return profilePicturePath;
    }

    public void setProfilePicturePath(String profilePicturePath) {
        this.profilePicturePath = profilePicturePath;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
