package ch.noseryoung.lernendeverwaltung.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

public class ProfilePicture {

  private Bitmap profilePictureBitmap;
  private String profilePicturePath;

  public ProfilePicture(Bitmap imageBitmap, String profilePicturePath) {
    this.profilePictureBitmap = imageBitmap;
    this.profilePicturePath = profilePicturePath;
  }

  //Functions

  //Rotate Image
  public Bitmap rotateImageIfRequired() throws IOException {
    if (profilePicturePath != null) {
      ExifInterface ei = new ExifInterface(profilePicturePath);
      int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          return rotateImage(profilePictureBitmap, 90);
        case ExifInterface.ORIENTATION_ROTATE_180:
          return rotateImage(profilePictureBitmap, 180);
        case ExifInterface.ORIENTATION_ROTATE_270:
          return rotateImage(profilePictureBitmap, 270);
        default:
          return profilePictureBitmap;
      }
    }
    return profilePictureBitmap;
  }

  private Bitmap rotateImage(Bitmap imageBitmap, int degree) {
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    Bitmap rotatedImage = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
    imageBitmap.recycle();
    return rotatedImage;
  }

  //Getters and Setters
  public String getProfilePicturePath() {
    return profilePicturePath;
  }

  public void setProfilePicturePath(String profilePicturePath) {
    this.profilePicturePath = profilePicturePath;
  }

  public Bitmap getProfilePictureBitmap() {
    return profilePictureBitmap;
  }

  public void setProfilePictureBitmap(Bitmap profilePictureBitmap) {
    this.profilePictureBitmap = profilePictureBitmap;
  }
}
