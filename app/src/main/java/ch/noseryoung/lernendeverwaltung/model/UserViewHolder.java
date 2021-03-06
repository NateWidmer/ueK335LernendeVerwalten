package ch.noseryoung.lernendeverwaltung.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ch.noseryoung.lernendeverwaltung.R;

public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    // GUI Components
    TextView firstName;
    TextView lastName;
    ImageView profilePicture;

    // On User Listener for on Click on List Element
    OnUserListener onUserListener;

    // Functions

    // Prepare List Elements and set On Click listener
    public UserViewHolder(@NonNull View itemView, OnUserListener onUserListener) {
        super(itemView);
        firstName = itemView.findViewById(R.id.firstNameView);
        lastName = itemView.findViewById(R.id.lastNameView);
        profilePicture = itemView.findViewById(R.id.avatarPicture);
        this.onUserListener = onUserListener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        onUserListener.onUserClick(getAdapterPosition());
    }
}
