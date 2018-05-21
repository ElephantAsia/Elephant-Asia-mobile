package fr.elephantasia;

import android.support.multidex.MultiDexApplication;

import java.util.Date;

import fr.elephantasia.database.RealmDB;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

import static fr.elephantasia.database.model.Elephant.ID;

/**
 * \brief Entry point of the application
 */
public class BaseApplication extends MultiDexApplication {

  @Override
  public void onCreate() {
    super.onCreate();
    Realm.init(this);

    // addElephantDemo();
  }

  private void addElephantDemo() {

    Realm realm = Realm.getDefaultInstance();

    if (realm.where(Elephant.class).count() == 0) {
      /**
       * Elephant 1 (random)
       */
      final Elephant e = new Elephant();
      // Profil
      e.name = "wung chu";
      e.nickName = "wung";
      e.sex = "M";
      e.currentLoc.provinceName = "Yangoon";
      e.currentLoc.cityName = "Yangoon";
      e.birthDate = "1983/10/27";
      e.birthLoc.provinceName = "Ayeyarwady";

      // Registration
      e.earTag = true;
      e.eyeD = true;
      e.mteOwner = false;
      e.chips1 = "123qlST09";
      e.regID = "MSI4041234";
      e.registrationLoc.cityName = "Pathein";

      // Description
      e.tusk = "none";
      e.nailsFrontLeft = "5";
      e.nailsFrontRight = "5";
      e.nailsRearLeft = "4";
      e.nailsRearRight = "4";
      e.height = "2.7";
      e.heightUnit = "m";
      e.girth = "340";
      e.weight = "2784";
      e.dbState = Elephant.DbState.edited.name();

      /**
       * Elephant 2 (random damaged)
       */
      final Elephant e2 = new Elephant();
      // Profil
      e2.name = "zai po";
      e2.nickName = "zai zai";
      e2.sex = "F";
      e2.currentLoc.provinceName = "Ayeyarwady";
      e2.currentLoc.cityName = "Pathein";
      e2.birthDate = "1990/11/03";
      e2.birthLoc.provinceName = "Mandalay";

      // Registration
      e2.earTag = true;
      e2.eyeD = true;
      e2.mteOwner = false;
      e2.chips1 = "tre109764nb";
      e2.regID = "STA01241";
      e2.registrationLoc.cityName = "Bago";

      // Description
      e2.tusk = "Right tusk only";
      e2.nailsFrontLeft = "2";
      e2.nailsFrontRight = "2";
      e2.nailsRearLeft = "2";
      e2.nailsRearRight = "1";
      e2.height = "2.4";
      e2.heightUnit = "m";
      e2.girth = "320";
      e2.weight = "2424";
      e2.dbState = Elephant.DbState.edited.name();

      /**
       * Elephant 3 (father)
       */
      final Elephant e3 = new Elephant();
      // Profil
      e3.name = "panto zilak";
      e3.nickName = "panto";
      e3.sex = "M";
      e3.currentLoc.provinceName = "Pyay";
      e3.currentLoc.cityName = "Bago";
      e3.birthDate = "1986/08/24";
      e3.birthLoc.provinceName = "Tanintharyi";

      // Registration
      e3.earTag = true;
      e3.eyeD = true;
      e3.mteOwner = true;
      e3.mteNumber = "094253";
      e3.chips1 = "z071l541mc";
      e3.regID = "FD901X";
      e3.registrationLoc.cityName = "Taunggyi";

      // Description
      e3.tusk = "Both";
      e3.nailsFrontLeft = "5";
      e3.nailsFrontRight = "5";
      e3.nailsRearLeft = "4";
      e3.nailsRearRight = "4";
      e3.height = "2.8";
      e3.heightUnit = "m";
      e3.girth = "400";
      e3.weight = "3864";
      e3.dbState = Elephant.DbState.edited.name();

      /**
       * Elephant 4 (mother)
       */
      final Elephant e4 = new Elephant();
      // Profil
      e4.name = "zilui trant";
      e4.nickName = "zizi";
      e4.sex = "F";
      e4.currentLoc.provinceName = "Mandalay";
      e4.currentLoc.cityName = "Meiktila";
      e4.birthDate = "1988/01/06";
      e4.birthLoc.provinceName = "Tanintharyi";

      // Registration
      e4.earTag = true;
      e4.eyeD = true;
      e4.mteOwner = true;
      e4.mteNumber = "094253";
      e4.chips1 = "z071l541mc";
      e4.regID = "FD901X";
      e4.registrationLoc.cityName = "Taunggyi";

      // Description
      e4.tusk = "Both";
      e4.nailsFrontLeft = "5";
      e4.nailsFrontRight = "5";
      e4.nailsRearLeft = "4";
      e4.nailsRearRight = "4";
      e4.height = "2.6";
      e4.heightUnit = "m";
      e4.girth = "330";
      e4.weight = "2604";
      e4.dbState = Elephant.DbState.edited.name();



      /**
       * Elephant 5 (child)
       */
      final Elephant e5 = new Elephant();
      // Profil
      e5.name = "slatu";
      e5.nickName = "slatu";
      e5.sex = "M";
      e5.currentLoc.provinceName = "Sagain";
      e5.currentLoc.cityName = "Monywa";
      e5.birthDate = "2008/08/17";
      e5.birthLoc.provinceName = "Tanintharyi";

      // Registration
      e5.earTag = true;
      e5.eyeD = true;
      e5.mteOwner = true;
      e5.mteNumber = "7120641";
      e5.chips1 = "jdawd6131";
      e5.regID = "ZS781213P";
      e5.registrationLoc.cityName = "Taunggyi";

      // Description
      e5.tusk = "Both";
      e5.nailsFrontLeft = "5";
      e5.nailsFrontRight = "5";
      e5.nailsRearLeft = "4";
      e5.nailsRearRight = "4";
      e5.height = "2.3";
      e5.heightUnit = "m";
      e5.girth = "300";
      e5.weight = "2064";
      e5.dbState = Elephant.DbState.edited.name();


      /**
       * Elephant 6 (child 2)
       */
      final Elephant e6 = new Elephant();
      // Profil
      e6.name = "prantao dest";
      e6.nickName = "tao";
      e6.sex = "F";
      e6.currentLoc.provinceName = "Rakhine";
      e6.currentLoc.cityName = "Sittwe";
      e6.birthDate = "2012/02/01";
      e6.birthLoc.provinceName = "Tanintharyi";

      // Registration
      e6.earTag = true;
      e6.eyeD = true;
      e6.mteOwner = true;
      e6.mteNumber = "411291";
      e6.chips1 = "jp2nf324etnzoqw";
      e6.regID = "T33PODWTW";
      e6.registrationLoc.cityName = "Taunggyi";

      // Description
      e6.tusk = "Both";
      e6.nailsFrontLeft = "5";
      e6.nailsFrontRight = "5";
      e6.nailsRearLeft = "4";
      e6.nailsRearRight = "4";
      e6.height = "2.3";
      e6.heightUnit = "m";
      e6.girth = "280";
      e6.weight = "2064";
      e6.dbState = Elephant.DbState.edited.name();



      // Parentage
      e5.mother = e4;
      e5.father = e3;
      e6.mother = e4;
      e6.father = e3;

      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm bgRealm) {
          saveElephant(e, bgRealm);
          saveElephant(e2, bgRealm);
          saveElephant(e3, bgRealm);
          saveElephant(e4, bgRealm);
          saveElephant(e5, bgRealm);
          saveElephant(e6, bgRealm);
        }
      });

      e3.children.add(e5);
      e3.children.add(e6);
      e4.children.add(e5);
      e4.children.add(e6);

      realm.executeTransaction(new Realm.Transaction() {
        @Override
        public void execute(Realm bgRealm) {
          saveElephant(e, bgRealm);
          saveElephant(e2, bgRealm);
          saveElephant(e3, bgRealm);
          saveElephant(e4, bgRealm);
          saveElephant(e5, bgRealm);
          saveElephant(e6, bgRealm);
        }
      });
    }
    realm.close();
  }

  private void saveElephant(Elephant elephant, Realm bgRealm) {
    if (elephant.id == -1) {
      elephant.id = RealmDB.getNextId(bgRealm, Elephant.class, ID);
    }
    elephant.lastVisited = new Date();
    bgRealm.insertOrUpdate(elephant);
  }

}
