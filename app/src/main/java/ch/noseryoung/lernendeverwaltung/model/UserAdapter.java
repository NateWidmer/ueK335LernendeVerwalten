package ch.noseryoung.lernendeverwaltung.model;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;
import ch.noseryoung.lernendeverwaltung.DetailActivity;
import ch.noseryoung.lernendeverwaltung.ProfilePicture;
import ch.noseryoung.lernendeverwaltung.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> implements Filterable {

  private ArrayList<User> users;
  private ArrayList<User> usersFull;
  private Context applicationContext;
  private ProfilePicture profilePicture;
  private OnUserListener onUserListener;

  public UserAdapter(ArrayList<User> users, Context applicationContext, OnUserListener onUserListener) {
    this.users = users;
    usersFull = new ArrayList<>(users);
    this.applicationContext = applicationContext;
    this.onUserListener = onUserListener;
  }

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
    profilePicture = new ProfilePicture(users.get(position).getProfilePictureAsBitmap(applicationContext), users.get(position).getProfilePicture());

    holder.firstName.setText(users.get(position).getFirstName());
    holder.lastName.setText(users.get(position).getLastName());

    try {
      holder.profilePicture.setImageBitmap(profilePicture.rotateImageIfRequired());
    } catch (IOException e) {
      e.printStackTrace();
    }

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
