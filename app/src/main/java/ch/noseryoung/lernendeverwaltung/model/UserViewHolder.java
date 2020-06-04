package ch.noseryoung.lernendeverwaltung.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ch.noseryoung.lernendeverwaltung.R;

import java.io.File;

public class UserViewHolder extends RecyclerView.ViewHolder{

    TextView firstName;
    TextView lastName;
    ImageView profilePicture;

    public UserViewHolder(@NonNull View itemView) {
        super(itemView);
        firstName = itemView.findViewById(R.id.firstNameView);
        lastName = itemView.findViewById(R.id.lastNameView);
        profilePicture = itemView.findViewById(R.id.avatarPictureList);
    }
}
