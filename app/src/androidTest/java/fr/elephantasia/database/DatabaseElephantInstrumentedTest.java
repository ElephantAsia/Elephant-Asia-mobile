package fr.elephantasia.database;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.List;

import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *  Feature                 Small       Medium            Large
 *  Network access          No          Localhost only    Yes
 *  Database                No          Yes               Yes
 *  File system access      No          Yes               Yes
 *  Use external systems    No          Discouraged       Yes
 *  Multiple threads        No          Yes               Yes
 *  Sleep statements        No          Yes               Yes
 *  System properties       No          Yes               Yes
 *  Time limit (ms)         100         2000              120000
 *
 *  Source:
 *  https://stackoverflow.com/a/4671991/4643265
 */
@MediumTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseElephantInstrumentedTest {

  static private DatabaseController dbController;

  @BeforeClass
  static public void createDatabase() {
    Context context = InstrumentationRegistry.getTargetContext();
    Realm.init(context);
    dbController = new DatabaseController();
  }

  @AfterClass
  static public void deleteDatabase() {
    dbController.close();
    dbController.delete();
  }

  @Test
  public void Test0_insert() {
    Elephant jackie = new Elephant();
    jackie.name = "jackie";
    jackie.nickName = "kiki";
    jackie.cuid = "jackie_cuid";
    jackie.sex = "F";
    jackie.dbState = Elephant.DbState.Created.name();

    Elephant michel = new Elephant();
    michel.name = "michel";
    michel.nickName = "michou";
    michel.cuid = "michel_cuid";
    michel.sex = "M";
    michel.dbState = Elephant.DbState.Created.name();
    michel.draft = true;

    Elephant sardou = new Elephant();
    sardou.name = "sardou";
    sardou.nickName = "sardine";
    sardou.cuid = "sardou_cuid";
    sardou.sex = "M";
    sardou.dbState = Elephant.DbState.Created.name();
    sardou.syncState = Elephant.SyncState.Pending.name();

    Elephant gilles = new Elephant();
    gilles.name = "gilles";
    gilles.nickName = "gillou";
    gilles.cuid = "gilles_cuid";
    gilles.sex = "M";
    gilles.dbState = Elephant.DbState.Created.name();

    dbController.beginTransaction();
    dbController.insertOrUpdate(jackie);
    dbController.insertOrUpdate(michel);
    dbController.insertOrUpdate(gilles);
    dbController.insertOrUpdate(sardou);
    dbController.commitTransaction();

    long count = dbController.getElephantsCount();
    assertEquals(4, count);
  }

  @Test
  public void Test1_select() {
    Elephant jackie = dbController.getElephantByCuid("jackie_cuid");
    assertNotNull(jackie);
    assertEquals("jackie", jackie.name);
    assertEquals("kiki", jackie.nickName);
    assertEquals("jackie_cuid", jackie.cuid);
    assertEquals("F", jackie.sex);
    assertEquals(Elephant.DbState.Created.name(), jackie.dbState);

    Elephant michel = dbController.getElephantByCuid("michel_cuid");
    assertNotNull(michel);
    assertEquals("michel", michel.name);
    assertEquals("michou", michel.nickName);
    assertEquals("michel_cuid", michel.cuid);
    assertEquals("M", michel.sex);
    assertEquals(true, michel.draft);

    Elephant sardou = dbController.getElephantById(4);
    assertNotNull(sardou);
    assertEquals("sardou", sardou.name);

    long readyToSyncCount = dbController.getElephantsReadyToSyncCount();
    assertEquals(2, readyToSyncCount); // jackie, gilles

    long draftCount = dbController.getElephantsDraftCount();
    assertEquals(1, draftCount); // michel

    long pendingCount = dbController.getElephantsSyncStatePendingCount();
    assertEquals(1, pendingCount); // sardou
  }

  @Test
  public void Test2_lastVisited()  {
    Elephant jackie = dbController.getElephantByCuid("jackie_cuid");
    Elephant michel = dbController.getElephantByCuid("michel_cuid");

    assertNotNull(jackie);
    assertNotNull(michel);
    assertEquals(1, (int)jackie.id);
    assertEquals(2, (int)michel.id);

    dbController.updateLastVisitedDateElephant(jackie.id);
    dbController.updateLastVisitedDateElephant(michel.id);

    List<Elephant> lastVisited = dbController.getLastVisitedElephant();
    assertNotNull(lastVisited);
    assertEquals(2, lastVisited.size());
    assertEquals("michel", lastVisited.get(0).name);
    assertEquals("jackie", lastVisited.get(1).name);
  }

  @Test
  public void Test3_elephantsReadyToUpload() {
    List<Elephant> elephants = dbController.getElephantsReadyToUpload();

    assertNotNull(elephants);
    assertEquals(2, elephants.size());
    assertEquals("jackie", elephants.get(0).name);
    assertEquals("gilles", elephants.get(1).name);
  }

  @Test
  public void Test3_update() {
  }

//  @Test
//  public void Test4_search() {
//  }
//
//  @Test
//  public void Test5_parentage() {
//  }
//
//  @Test
//  public void Test6_children() {
//  }
//
//  @Test
//  public void Test7_delete() {
//  }

}
