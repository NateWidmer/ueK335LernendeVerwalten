package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.MenuItem;
import android.widget.SearchView;
import ch.noseryoung.lernendeverwaltung.model.UserAdapter;
import ch.noseryoung.lernendeverwaltung.persistence.AppDatabase;
import ch.noseryoung.lernendeverwaltung.persistence.UserDao;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  private UserDao userDao;
  FloatingActionButton addButton;
  UserAdapter userAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    loadUsers();

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
    userView.setAdapter(userAdapter);

  }

  public void loadUsers() {
    userDao = AppDatabase.getAppDb(getApplicationContext()).getUserDao();
    this.userAdapter = new UserAdapter((ArrayList) userDao.getAll(), getApplicationContext());
  }

  private void openCreateActivity() {
    Intent intend = new Intent(this, CreateActivity.class);
    startActivity(intend);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.search_menu, menu);

    MenuItem searchItem = menu.findItem(R.id.action_search);
    SearchView searchView = (SearchView) searchItem.getActionView();

    searchView.setQueryHint(getText(R.string.search_hint));
    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {

        userAdapter.getFilter().filter(newText);
        return false;
      }
    });
    return true;
  }
}
