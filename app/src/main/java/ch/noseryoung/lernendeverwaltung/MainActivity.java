package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import ch.noseryoung.lernendeverwaltung.model.User;
import ch.noseryoung.lernendeverwaltung.model.UserAdapter;
import ch.noseryoung.lernendeverwaltung.persistence.AppDatabase;
import ch.noseryoung.lernendeverwaltung.persistence.UserDao;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

  private UserDao userDao;
  FloatingActionButton addButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    addButton = findViewById(R.id.addButton);
    addButton.bringToFront();
    addButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        openCreateActivity();
      }
    });

    RecyclerView userView = findViewById(R.id.avatarList);
    userView.setHasFixedSize(true);
    // use a linear layout manager
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    userView.setLayoutManager(linearLayoutManager);

    userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();

    List<User> usersFromDatabase = userDao.getAll();
    UserAdapter userAdapter = new UserAdapter(usersFromDatabase);
    userView.setAdapter(userAdapter);

  }

    private void openCreateActivity() {
        Intent intend = new Intent(this, CreateActivity.class);
        startActivity(intend);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));

        return true;
    }
}
