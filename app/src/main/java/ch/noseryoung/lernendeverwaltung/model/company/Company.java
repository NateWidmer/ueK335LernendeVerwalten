package ch.noseryoung.lernendeverwaltung.model.company;

import androidx.annotation.NonNull;
import androidx.room.*;

import java.util.UUID;

@Entity
public class Company {

  @PrimaryKey
  @NonNull
  private String id;

  @ColumnInfo(name = "company_name")
  private String companyName;

  public Company(String companyName) {
    this.id = UUID.randomUUID().toString();
    this.companyName = companyName;
  }

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }
}
