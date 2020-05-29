package ch.noseryoung.lernendeverwaltung;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

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
        UserAdapter userAdapter = new UserAdapter(data);
        userView.setAdapter(userAdapter);
    }

}
