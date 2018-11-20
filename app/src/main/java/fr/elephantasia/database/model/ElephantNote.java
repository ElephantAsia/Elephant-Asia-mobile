package fr.elephantasia.database.model;

import java.util.HashMap;
import java.util.Map;

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

  @Ignore public static final String CREATED_AT = "createdAt";

  public enum Category {
    Medical,
    Physical,
    Administrative,
    Parentage,
    Other
  }

  @PrimaryKey private Integer id = -1;
  private String cuid;
  private Integer elephantId = -1;

  private Integer priority = -1;
  private String category = "undefined";
  private String description = "undefined";

  // private Date createdAt;
  private String createdAt;

  public ElephantNote() {
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setCuid(String cuid) {
    this.cuid = cuid;
  }

  public void setElephantId(Integer id) {
    this.elephantId = id;
  }

  public void setPriority(Prioriy priority) {
    this.priority = priority.getValue();
  }

  public void setCategory(Category category) {
    this.category = category.name();
  }

  public void setDescription(String description) {
    this.description = description;
  }

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

  public String getCreatedAt() {
    return createdAt;
  }


  public static class Prioriy {

    public static final Prioriy Low = new Prioriy(0, "Low");
    public static final Prioriy Medium = new Prioriy(1, "Medium");
    public static final Prioriy High = new Prioriy(2, "High");

    private static final Map<String, Prioriy> str2p = new HashMap<String, Prioriy>() {{
      put("Low", Low);
      put("Medium", Medium);
      put("High", High);
    }};

    private static final Map<Integer, Prioriy> int2p = new HashMap<Integer, Prioriy>() {{
      put(0, Low);
      put(1, Medium);
      put(2, High);
    }};

    private Integer value;
    private String strValue;

    private Prioriy(Integer value, String strValue) {
      this.value = value;
      this.strValue = strValue;
    }

    public static Prioriy valueOf(String value) {
      return str2p.get(value);
    }

    public static Prioriy valueOf(Integer value) {
      return int2p.get(value);
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
