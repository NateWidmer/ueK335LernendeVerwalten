package ch.noseryoung.lernendeverwaltung.persistence;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import ch.noseryoung.lernendeverwaltung.model.User;

@Database(entities = {User.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {

  private static final String DB_Name = "uek335";
  private static AppDatabase appDb;

  public static AppDatabase getAppDb(Context context) {
    if (appDb == null) {
      appDb = Room.databaseBuilder(context, AppDatabase.class, DB_Name)
          .fallbackToDestructiveMigration()
          .allowMainThreadQueries()
          .build();
    }
    return appDb;
  }

  public abstract UserDao getUserDao();

}
