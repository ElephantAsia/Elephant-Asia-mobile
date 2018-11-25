package fr.elephantasia.database.model;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

public class ElephantNote extends RealmObject {

  @Ignore public static final String ID = "id";
  @Ignore public static final String CUID = "cuid";
  @Ignore public static final String ELEPHANT_ID = "elephantId";

  @Ignore public static final String PRIORITY = "priority";
  @Ignore public static final String CATEGORY = "category";
  @Ignore public static final String DESCRIPTION = "description";

  @Ignore public static final String DB_STATE = "dbState";

  @Ignore public static final String CREATED_AT = "createdAt";

  public enum DbState {
    Created
  }

  @PrimaryKey private Integer id = -1;
  private String cuid;
  private Integer elephantId = -1;

  private Integer priority = -1;
  private String category = "undefined";
  private String description = "undefined";

  private String dbState = null;

  private String createdAt;

  public ElephantNote() { }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setCuid(String cuid) {
    this.cuid = cuid;
  }

  public void setElephantId(Integer id) {
    this.elephantId = id;
  }

  public void setPriority(Priority priority) {
    this.priority = priority.getValue();
  }

  public void setCategory(Category category) {
    this.category = category.toString();
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setDbState(@Nullable DbState dbState) { this.dbState = (dbState != null) ? dbState.name() : null; }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  public Integer getId() {
    return id;
  }

  public String getCuid() {
    return cuid;
  }

  public Integer getElephantId() {
    return elephantId;
  }

  public Integer getPriority() {
    return priority;
  }

  public String getCategory() {
    return category;
  }

  public String getDescription() {
    return description;
  }

  public String getDbState() { return dbState; }

  public String getCreatedAt() {
    return createdAt;
  }


  public static class Category {

    public static final Category Medical = new Category(0, "medical");
    public static final Category Physical = new Category(1, "physical");
    public static final Category Administrative = new Category(2, "administrative");
    public static final Category Parentage = new Category(3, "parentage");
    public static final Category Other = new Category(4, "other");
    public static final Category None = new Category(5, "None");

    private static final Map<String, Category> str2c = new HashMap<String, Category>() {{
      put("medical", Medical);
      put("physical", Physical);
      put("administrative", Administrative);
      put("parentage", Parentage);
      put("other", Other);
      put("None", None);
    }};

    private static final Map<Integer, Category> int2c = new HashMap<Integer, Category>() {{
      put(0, Medical);
      put(1, Physical);
      put(2, Administrative);
      put(3, Parentage);
      put(4, Other);
      put(5, None);
    }};

    private Integer value;
    private String strValue;

    private Category(Integer value, String strValue) {
      this.value = value;
      this.strValue = strValue;
    }

    public static Category valueOf(String value) {
      return str2c.get(value);
    }

    public static Category valueOf(Integer value) {
      return int2c.get(value);
    }

    @Override
    public boolean equals(Object other) {
      return getClass() == other.getClass() && ((Category)other).getValue().equals(getValue());
    }

    @Override
    public String toString() {
      return strValue;
    }

    public Integer getValue() {
      return value;
    }
  }

  public static class Priority {

    public static final Priority Low = new Priority(0, "low");
    public static final Priority Medium = new Priority(1, "medium");
    public static final Priority High = new Priority(2, "high");
    public static final Priority None = new Priority(3, "None");

    private static final Map<String, Priority> str2p = new HashMap<String, Priority>() {{
      put("low", Low);
      put("medium", Medium);
      put("high", High);
      put("None", None);
    }};

    private static final Map<Integer, Priority> int2p = new HashMap<Integer, Priority>() {{
      put(0, Low);
      put(1, Medium);
      put(2, High);
      put(3, None);
    }};

    private Integer value;
    private String strValue;

    private Priority(Integer value, String strValue) {
      this.value = value;
      this.strValue = strValue;
    }

    public static Priority valueOf(String value) {
      return str2p.get(value);
    }

    public static Priority valueOf(Integer value) {
      return int2p.get(value);
    }

    @Override
    public boolean equals(Object other) {
      return getClass() == other.getClass() && ((Priority)other).getValue().equals(getValue());
    }

    @Override
    public String toString() {
      return strValue;
    }

    public Integer getValue() {
      return value;
    }
  }

}
