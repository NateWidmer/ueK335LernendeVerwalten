package ch.noseryoung.lernendeverwaltung;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class CreateActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTitle(R.string.create_title);
        setContentView(R.layout.activity_create);
    }
}
