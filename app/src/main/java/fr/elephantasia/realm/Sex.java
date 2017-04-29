package fr.elephantasia.realm;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;

/**
 * Created by seb on 29/04/2017.
 */

public class Sex extends RealmObject {
  @Ignore
  public final static String MALE = "male";
  @Ignore
  public final static String FEMALE = "female";
  public String gender;

  public void setSex(String sex) {
    this.gender = sex;
  }
}
