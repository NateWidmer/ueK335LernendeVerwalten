package ch.noseryoung.lernendeverwaltung;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.content.FileProvider;
import ch.noseryoung.lernendeverwaltung.model.User;
import ch.noseryoung.lernendeverwaltung.persistence.AppDatabase;
import ch.noseryoung.lernendeverwaltung.persistence.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateActivity extends AppCompatActivity {

  Spinner spinner;
  FloatingActionButton saveButton;
  ImageButton profilePictureButton;
  private UserDao userDao;
  static final int REQUEST_IMAGE_CAPTURE = 1;
  ImageView avatarPicture;
  String profilePicturePath;
  ProfilePicture profilePicture;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.setTitle(R.string.create_title);
    setContentView(R.layout.activity_create);

    userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();

    ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
        R.array.companies, R.layout.spinner_item);

    adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
    spinner = findViewById(R.id.companySpinner);
    spinner.setAdapter(adapter);

    saveButton = findViewById(R.id.saveButton);
    saveButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openMainActivity();
      }
    });

    if (Build.VERSION.SDK_INT >= 23) {
      requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
    }

    profilePictureButton = findViewById(R.id.addProfilePictureButton);
    profilePictureButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openCamera();
      }
    });
  }

  private void openMainActivity() {
    saveUser();
    Intent intend = new Intent(this, MainActivity.class);
    startActivity(intend);
  }

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

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
      Bitmap imageBitmap = BitmapFactory.decodeFile(profilePicturePath);

      profilePicture = new ProfilePicture(imageBitmap, profilePicturePath);

      try {
        imageBitmap = profilePicture.rotateImageIfRequired();
      } catch (IOException e) {
        e.printStackTrace();
      }

      avatarPicture = findViewById(R.id.avatarPictureList);
      avatarPicture.setImageBitmap(imageBitmap);
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

  private void saveUser() {
    TextInputEditText preNameTextInput = findViewById(R.id.preNameInputEditText);
    String firstName = preNameTextInput.getText().toString();
    TextInputEditText lastNameTextInput = findViewById(R.id.lastNameInputEditText);
    String lastName = lastNameTextInput.getText().toString();
    Spinner companySpinner = findViewById(R.id.companySpinner);
    String company = companySpinner.getSelectedItem().toString();

    userDao.insert(new User(firstName, lastName, profilePicturePath, company));
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
