package ch.noseryoung.lernendeverwaltung;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.empty_title);
        setContentView(R.layout.activity_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cancel_menu, menu);
        return true;
    }
}
