package ch.noseryoung.lernendeverwaltung;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;

import java.io.IOException;

public class ProfilePicture {

  private Bitmap imageBitmap;
  private String profilePicturePath;

  public ProfilePicture(Bitmap imageBitmap, String profilePicturePath) {
    this.imageBitmap = imageBitmap;
    this.profilePicturePath = profilePicturePath;
  }

  public Bitmap rotateImageIfRequired() throws IOException {
    ExifInterface ei = new ExifInterface(profilePicturePath);
    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

    switch (orientation) {
      case ExifInterface.ORIENTATION_ROTATE_90:
        return rotateImage(imageBitmap, 90);
      case ExifInterface.ORIENTATION_ROTATE_180:
        return rotateImage(imageBitmap, 180);
      case ExifInterface.ORIENTATION_ROTATE_270:
        return rotateImage(imageBitmap, 270);
      default:
        return imageBitmap;
    }
  }

  private Bitmap rotateImage(Bitmap imageBitmap, int degree) {
    Matrix matrix = new Matrix();
    matrix.postRotate(degree);
    Bitmap rotatedImage = Bitmap.createBitmap(imageBitmap, 0, 0, imageBitmap.getWidth(), imageBitmap.getHeight(), matrix, true);
    imageBitmap.recycle();
    return rotatedImage;
  }

  public String getProfilePicturePath() {
    return profilePicturePath;
  }

  public void setProfilePicturePath(String profilePicturePath) {
    this.profilePicturePath = profilePicturePath;
  }

  public Bitmap getImageBitmap() {
    return imageBitmap;
  }

  public void setImageBitmap(Bitmap imageBitmap) {
    this.imageBitmap = imageBitmap;
  }
}
