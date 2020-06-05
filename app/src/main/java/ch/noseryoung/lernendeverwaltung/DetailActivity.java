package ch.noseryoung.lernendeverwaltung;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.model.User;
import ch.noseryoung.lernendeverwaltung.utils.ProfilePicture;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    //Gui Components
    TextView firstName;
    TextView lastName;
    TextView company;
    ImageView profileView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Current User
        User currentUser = (User) getIntent().getSerializableExtra("USER");

        //Set Title to Username
        this.setTitle(currentUser.getFirstName() + " " + currentUser.getLastName());

        setContentView(R.layout.activity_detail);

        //insert Data into Views
        try {
            insertUserData(currentUser);
        } catch (IOException e) {
            Log.d("DETAIL_ACTIVITY", "An Error occurred while inserting The Data of The User into the View", e);
        }
    }

    //Functions

    //Functions for Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cancel_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cancel:
                Intent i = new Intent(this, MainActivity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Function to fill Views
    public void insertUserData(User currentUser) throws IOException {
        firstName = findViewById(R.id.firstNameTextView);
        firstName.setText(currentUser.getFirstName());
        lastName = findViewById(R.id.lastNameTextView);
        lastName.setText(currentUser.getLastName());
        company = findViewById(R.id.companyTextView);
        company.setText(currentUser.getCompany());

        ProfilePicture profilePicture = new ProfilePicture(currentUser.getProfilePictureAsBitmap(this), currentUser.getProfilePicturePath());

        profileView = findViewById(R.id.avatarPicture);
        profileView.setImageBitmap(profilePicture.rotateImageIfRequired());
    }
}
