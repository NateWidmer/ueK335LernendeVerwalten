package ch.noseryoung.lernendeverwaltung;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.FileProvider;

import ch.noseryoung.lernendeverwaltung.model.User;
import ch.noseryoung.lernendeverwaltung.persistence.AppDatabase;
import ch.noseryoung.lernendeverwaltung.persistence.UserDao;
import ch.noseryoung.lernendeverwaltung.utils.ProfilePicture;
import ch.noseryoung.lernendeverwaltung.utils.Validator;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

public class CreateActivity extends AppCompatActivity {

    //GUI Components
    Spinner spinner;
    FloatingActionButton saveButton;
    ImageButton profilePictureButton;
    ImageView avatarPicture;
    EditText firstName;
    EditText lastName;
    TextView firstNameLetterCount;
    TextView lastNameLetterCount;

    //Dao
    private UserDao userDao;

    //For Camera
    static final int REQUEST_IMAGE_CAPTURE = 1;
    String profilePicturePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set View and Title
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.create_title);
        setContentView(R.layout.activity_create);

        //Define UserDao
        userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();

        //Define Spinner
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.companies, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner = findViewById(R.id.companySpinner);
        spinner.setAdapter(adapter);

        //Ask for Camera Permission
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
        }

        //Define Profile Picture Button
        profilePictureButton = findViewById(R.id.addProfilePictureButton);
        profilePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });

        //Define Form
        firstName = findViewById(R.id.firstNameInputEditText);
        firstNameLetterCount = findViewById(R.id.firstNameLetterCount);
        lastName = findViewById(R.id.lastNameInputEditText);
        lastNameLetterCount = findViewById(R.id.lastNameLetterCount);

        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    firstName.setError("Eingabe obligatorisch");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                firstNameLetterCount.setText(s.length() + "/ 50");

                if (s.length() > 50) {
                    firstName.setError("Die Eingabe ist zu lang (max 50 Zeichen)");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() == 0) {
                    lastName.setError("Eingabe obligatorisch");
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lastNameLetterCount.setText(s.length() + "/ 50");
                if (s.length() > 50) {
                    lastName.setError("Die Eingabe ist zu lang (max 50 Zeichen)");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final LinkedList<EditText> createForm = new LinkedList<EditText>();
        createForm.add(firstName);
        createForm.add(lastName);

        //Save User
        final Validator validator = new Validator();
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validator.isValid(createForm)) {
                    userDao.insert(new User(firstName.getText().toString(), lastName.getText().toString(), profilePicturePath, spinner.getSelectedItem().toString()));
                    openMainActivity();
                }
            }
        });
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
                openMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Profile Picture and Camera Functions
    private void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            photoFile = createImageFile();

            if (photoFile != null) {
                profilePicturePath = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(CreateActivity.this, "ch.noseryoung.lernendeverwaltung", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() {
        // Create an image file name
        String filename = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    filename,
                    ".jpg",
                    storageDir
            );
        } catch (IOException e) {
            Log.d("ImageFile", "Exception creating Image File");
        }
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ProfilePicture profilePicture;
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(profilePicturePath);
            profilePicture = new ProfilePicture(imageBitmap, profilePicturePath);

            try {
                imageBitmap = profilePicture.rotateImageIfRequired();
            } catch (IOException e) {
                e.printStackTrace();
            }

            avatarPicture = findViewById(R.id.avatarPicture);
            avatarPicture.setImageBitmap(imageBitmap);
        }
    }

    //Change Activity
    private void openMainActivity() {
        Intent intend = new Intent(this, MainActivity.class);
        startActivity(intend);
    }


}
