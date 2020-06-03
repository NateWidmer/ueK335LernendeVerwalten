package ch.noseryoung.lernendeverwaltung;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import ch.noseryoung.lernendeverwaltung.model.User;
import ch.noseryoung.lernendeverwaltung.persistence.AppDatabase;
import ch.noseryoung.lernendeverwaltung.persistence.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class CreateActivity extends AppCompatActivity {

  Spinner spinner;
  FloatingActionButton saveButton;
  private UserDao userDao;

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
  }

  private void openMainActivity() {
    saveUser();
    Intent intend = new Intent(this, MainActivity.class);
    startActivity(intend);
  }

  private void saveUser() {
    TextInputEditText preNameTextInput = findViewById(R.id.preNameInputEditText);
    String firstName = preNameTextInput.getText().toString();
    TextInputEditText lastNameTextInput = findViewById(R.id.lastNameInputEditText);
    String lastName = lastNameTextInput.getText().toString();
    Spinner companySpinner = findViewById(R.id.companySpinner);
    String company = companySpinner.getSelectedItem().toString();

    userDao.insert(new User(firstName, lastName, "Bild", company));
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
