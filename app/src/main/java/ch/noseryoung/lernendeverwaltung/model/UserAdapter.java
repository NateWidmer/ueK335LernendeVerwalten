package ch.noseryoung.lernendeverwaltung.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.noseryoung.lernendeverwaltung.utils.ProfilePicture;
import ch.noseryoung.lernendeverwaltung.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> implements Filterable {

    // Users and duplicate User List for Filter
    private ArrayList<User> users;
    private ArrayList<User> usersFull;

    // Application Context for Profile Picture Rotate
    private Context applicationContext;

    // Profile Picture
    private ProfilePicture profilePicture;

    // On User Listener for On Click on List Element
    private OnUserListener onUserListener;

    public UserAdapter(ArrayList<User> users, Context applicationContext, OnUserListener onUserListener) {
        this.users = users;
        usersFull = new ArrayList<>(users);
        this.applicationContext = applicationContext;
        this.onUserListener = onUserListener;
    }

    // Functions

    // Recycler View
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.avatar_list_item, parent, false);
        return new UserViewHolder(v, onUserListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        profilePicture = new ProfilePicture(users.get(position).getProfilePictureAsBitmap(applicationContext), users.get(position).getProfilePicturePath());

        holder.firstName.setText(users.get(position).getFirstName());
        holder.lastName.setText(users.get(position).getLastName());

        try {
            holder.profilePicture.setImageBitmap(profilePicture.rotateImageIfRequired());
        } catch (IOException e) {
            Log.d("USER_ADAPTER", "Exception rotating Image", e);
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    // Filter Functions
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

                for (User user : usersFull) {
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
            users.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

}
