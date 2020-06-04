package ch.noseryoung.lernendeverwaltung.model;

import android.app.Application;
import android.content.Context;
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

  public UserAdapter(ArrayList<User> users, Context applicationContext) {
    this.users = users;
    usersFull = new ArrayList<>(users);
    this.applicationContext = applicationContext;
  }

  @NonNull
  @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    // create a new view
    View v = (View) LayoutInflater.from(parent.getContext())
        .inflate(R.layout.avatar_list_item, parent, false);
    return new UserViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
    holder.firstName.setText(users.get(position).getFirstName());
    holder.lastName.setText(users.get(position).getLastName());

    profilePicture = new ProfilePicture(users.get(position).getProfilePictureAsBitmap(), users.get(position).getProfilePicture());

    if (users.get(position).getProfilePicture() != null) {
      try {
        holder.profilePicture.setImageBitmap(profilePicture.rotateImageIfRequired());
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      holder.profilePicture.setImageBitmap(convertDrawableToBitmap(applicationContext));
    }
  }

  public Bitmap convertDrawableToBitmap(Context applicationContext) {
    Drawable avatar = ContextCompat.getDrawable(applicationContext, R.drawable.avatar);
    Bitmap bitmap = ((BitmapDrawable)avatar).getBitmap();

    return bitmap;
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
