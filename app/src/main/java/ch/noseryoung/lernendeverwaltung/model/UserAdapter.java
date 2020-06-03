package ch.noseryoung.lernendeverwaltung.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ch.noseryoung.lernendeverwaltung.R;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> implements Filterable {

    private ArrayList<User> users;
    private ArrayList<User> usersFull;

    public UserAdapter(ArrayList<User> users) {
        this.users = users;
        usersFull = new ArrayList<>(users);
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

    @Override
    public Filter getFilter() {
        return userFilter;
    }

    private Filter userFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<User> filteredUsers = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredUsers.addAll(usersFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(User user : usersFull) {
                    if (user.getFirstName().toLowerCase().contains(filterPattern) || user.getLastName().toLowerCase().contains(filterPattern)) {
                        filteredUsers.add(user);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredUsers;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            users.clear();
            users.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

}
