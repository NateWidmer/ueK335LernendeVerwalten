package ch.noseryoung.lernendeverwaltung;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder{

    TextView firstName;
    TextView lastName;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        firstName = itemView.findViewById(R.id.firstNameView);
        lastName = itemView.findViewById(R.id.lastNameView);
    }
}
