package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    UserAdapter userAdapter;
    ArrayList<User> data = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        data.add(new User("Gianluca", "Daffré"));
        data.add(new User("Peter", "Meier"));
        data.add(new User("Hans", "Muster"));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView userView = findViewById(R.id.avatarList);
        userView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        userView.setLayoutManager(linearLayoutManager);
        userAdapter = new UserAdapter(data);
        userView.setAdapter(userAdapter);
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
