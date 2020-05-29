package ch.noseryoung.lernendeverwaltung;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder>{

    private ArrayList<User> users;

    public UserAdapter(ArrayList<User> users) {
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.avatar_list_item, parent, false);
        return new UserViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.firstName.setText(users.get(position).getFirstName());
        holder.lastName.setText(users.get(position).getLastName());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
