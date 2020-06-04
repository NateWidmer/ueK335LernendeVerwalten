package ch.noseryoung.lernendeverwaltung;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.model.User;
import ch.noseryoung.lernendeverwaltung.persistence.AppDatabase;
import ch.noseryoung.lernendeverwaltung.persistence.UserDao;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

public class DetailActivity extends AppCompatActivity {

    UserDao userDao;
    User currentUser;
    TextView firstName;
    TextView lastName;
    TextView company;
    ImageView profileView;
    ProfilePicture profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();
        String userID = getIntent().getStringExtra("USER_ID");
        currentUser = userDao.getById(userID);
        this.setTitle(currentUser.getFirstName() + " " + currentUser.getLastName());
        setContentView(R.layout.activity_detail);
        try {
            insertUserData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void insertUserData() throws IOException {
        firstName = findViewById(R.id.preNameTextView);
        firstName.setText(currentUser.getFirstName());
        lastName = findViewById(R.id.lastNameTextView);
        lastName.setText(currentUser.getLastName());
        company = findViewById(R.id.companyTextView);
        company.setText(currentUser.getCompany());

        profilePicture = new ProfilePicture(currentUser.getProfilePictureAsBitmap(this), currentUser.getProfilePicture());

        profileView = findViewById(R.id.avatarPicture);
        profileView.setImageBitmap(profilePicture.rotateImageIfRequired());
    }

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
}
