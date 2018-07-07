package fr.elephantasia;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SmallTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import fr.elephantasia.database.DatabaseController;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SmallTest
public class DatabaseInstrumentedTest {

  private Context context;
  private DatabaseController dbController;

  @Before
  public void init() {
    context = InstrumentationRegistry.getTargetContext();
    Realm.init(context);
    dbController = new DatabaseController();
  }

  @After
  public void deleteDatabase() {
    dbController.close();
    dbController.delete();
  }


  @Test
  public void Test_insert() {
    Elephant jackie = new Elephant();
    jackie.name = "jackie";
    jackie.nickName = "kiki";
    jackie.cuid = "jackie_cuid";

    dbController.beginTransaction();
    dbController.insertOrUpdate(jackie);
    dbController.commitTransaction();

    Elephant mustBeJackie = dbController.getElephantById(1);
    assertNotNull(mustBeJackie);
    assertEquals("jackie", mustBeJackie.name);
    assertEquals("kiki", mustBeJackie.nickName);
    assertEquals("jackie_cuid", mustBeJackie.cuid);
  }

}
