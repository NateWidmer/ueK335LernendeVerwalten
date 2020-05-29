package ch.noseryoung.lernendeverwaltung;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.create_title);
        setContentView(R.layout.activity_create);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,
                R.array.companies, R.layout.spinner_item);

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinner = findViewById(R.id.companySpinner);
        spinner.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cancel_menu, menu);
        return true;
    }
}
