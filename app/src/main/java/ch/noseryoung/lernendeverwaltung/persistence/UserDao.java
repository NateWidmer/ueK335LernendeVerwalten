package ch.noseryoung.lernendeverwaltung.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import ch.noseryoung.lernendeverwaltung.model.user.User;

import java.util.List;

@Dao
public interface UserDao {

  @Query("SELECT * FROM users")
  List<User> getAll();

  @Insert
  void insert(User user);

}
