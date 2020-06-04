package ch.noseryoung.lernendeverwaltung.persistence;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import ch.noseryoung.lernendeverwaltung.model.User;

import java.util.List;

@Dao
public interface UserDao {

  @Query("SELECT * FROM users")
  List<User> getAll();

  @Query("SELECT * FROM users WHERE id=:id")
  public User getById(String id);

  @Insert
  void insert(User user);

}
