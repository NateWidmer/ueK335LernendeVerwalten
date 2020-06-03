package ch.noseryoung.lernendeverwaltung.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import ch.noseryoung.lernendeverwaltung.model.company.Company;

import java.util.List;

@Dao
public interface CompanyDao {

  @Query("SELECT * FROM company")
  List<Company> getAll();

  @Insert
  void insert(Company company);
}
