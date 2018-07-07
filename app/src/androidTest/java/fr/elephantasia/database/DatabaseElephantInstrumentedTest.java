package fr.elephantasia.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *  Feature                 Small     Medium            Large
 *  Network access          No        Localhost only    Yes
 *  Database                No        Yes               Yes
 *  File system access      No        Yes               Yes
 *  Use external systems    No        Discouraged       Yes
 *  Multiple threads        No        Yes               Yes
 *  Sleep statements        No        Yes               Yes
 *  System properties       No        Yes               Yes
 *  Time limit (ms)         100       2000              120000
 *
 *  Source:
 *  https://stackoverflow.com/a/4671991/4643265
 */
@MediumTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseElephantInstrumentedTest {

  private DatabaseController dbController;

  @Before
  public void init() {
    Context context = InstrumentationRegistry.getTargetContext();
    Realm.init(context);
    dbController = new DatabaseController();
  }

  @After
  public void deleteDatabase() {
    dbController.close();
    dbController.delete();
  }

  @Test
  public void Test0_insert() {
    Elephant jackie = new Elephant();
    jackie.name = "jackie";
    jackie.nickName = "kiki";
    jackie.cuid = "jackie_cuid";
    jackie.sex = "M";

    dbController.beginTransaction();
    dbController.insertOrUpdate(jackie);
    dbController.commitTransaction();

    Elephant mustBeJackie = dbController.getElephantById(1);
    assertNotNull(mustBeJackie);
    assertEquals("jackie", mustBeJackie.name);
    assertEquals("kiki", mustBeJackie.nickName);
    assertEquals("jackie_cuid", mustBeJackie.cuid);
    assertEquals("M", mustBeJackie.sex);
  }

  @Test
  public void Test1_select() {
  }

  @Test
  public void Test2_update() {
  }

  @Test
  public void Test3_search() {
  }

  @Test
  public void Test4_parentage() {
  }

  @Test
  public void Test5_children() {
  }

  @Test
  public void Test6_delete() {
  }

}
