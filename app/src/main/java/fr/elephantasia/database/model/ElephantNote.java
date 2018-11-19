package fr.elephantasia.database.model;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ElephantNote extends RealmObject {

  @Ignore public static final String ID = "id";
  @Ignore public static final String CUID = "cuid";

  @Ignore public static final String PRIORITY = "priority";
  @Ignore public static final String CATEGORY = "category";
  @Ignore public static final String DESCRIPTION = "description";

  public enum Priority {
    Low,
    Medium,
    High
  }

  public enum Category {
    Medical,
    Physical,
    Administrative,
    Parentage,
    Other
  }

  @PrimaryKey private Integer id = -1;
  private String cuid;

  private String priority;
  private String category;
  private String description;

  public ElephantNote() {
    priority = category = description = "undefined";
  }

  public ElephantNote(Priority priority, Category category, String description) {
    this.priority = priority.name();
    this.category = category.name();
    this.description = description;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setCuid(String cuid) {
    this.cuid = cuid;
  }

  public void setPriority(Priority priority) {
    this.priority = priority.name();
  }

  public void setCategory(Category category) {
    this.category = category.name();
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getId() {
    return id;
  }

  public String getCuid() {
    return cuid;
  }

  public String getPriority() {
    return priority;
  }

  public String getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

}
