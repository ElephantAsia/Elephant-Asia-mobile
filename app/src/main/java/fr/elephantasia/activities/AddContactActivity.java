package fr.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import fr.elephantasia.R;
import fr.elephantasia.realm.model.Contact;
import fr.elephantasia.utils.ElephantInfo;

public class AddContactActivity extends AppCompatActivity {

  private Contact contact;

  public AddContactActivity() {

  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_contact_activity);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  public boolean onSupportNavigateUp() {
    confirmFinish();
    return true;
  }

  @Override
  public void onBackPressed() {
    confirmFinish();
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK && data != null) {
      ElephantInfo info = data.getParcelableExtra(SearchElephantActivity.EXTRA_RESULT);

//      switch (requestCode) {
//        case REQUEST_SET_FATHER:
//          refreshFather(info);
//          break;
//        case REQUEST_SET_MOTHER:
//          refreshMother(info);
//          break;
//        case REQUEST_ADD_CHILDREN:
//          refreshChildren(info);
//          break;
//        case REQUEST_CAPTURE_PHOTO:
//          addDocument(Uri.fromFile(ImageUtil.getCapturePhotoFile(this)));
//          break;
//        case REQUEST_IMPORT_PHOTO:
//          addDocument(data.getData());
//          break;
//        case REQUEST_CURRENT_LOCATION:
//          profilFragment.setCurrentLocation(selectedPlace.getAddress().toString());
//          break;
//        case REQUEST_BIRTH_LOCATION:
//          profilFragment.setBirthLocation(selectedPlace.getAddress().toString());
//          break;
//        case REQUEST_REGISTRATION_LOCATION:
//          registrationFragment.setRegistrationLocation(selectedPlace.getAddress().toString());
//          break;
//      }
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.add_elephant_more_menu, menu);
    return true;
  }

  private boolean checkMandatoryFields() {
//    if (elephant.name.isEmpty()) {
//      profilFragment.setNameError();
//      Toast.makeText(this, R.string.name_required, Toast.LENGTH_SHORT).show();
//      return false;
//    }
//    if (elephant.sex.gender == null) {
//      profilFragment.setSexError();
//      Toast.makeText(this, R.string.sex_required, Toast.LENGTH_SHORT).show();
//      return false;
//    }
    return true;
  }

  public Contact getContact() {
    return this.contact;
  }

  /**
   * Prevent user to cancel activity without saving his current Elephant.
   * Happens only if some data have been set.
   */
  private void confirmFinish() {

//    if (elephantInfo.isEmpty()) {
//      setResult(RESULT_CANCELED);
//      finish();
//    } else {
//      new AlertDialog.Builder(this)
//          .setMessage(R.string.put_drafts)
//          .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//              setResult(RESULT_CANCELED);
//              finish();
//            }
//          })
//          .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//              if (checkMandatoryFields()) {
//                elephantInfo.state = ElephantInfo.State.DRAFT;
//                database.insert(elephantInfo);
//                setResult(RESULT_DRAFT);
//                finish();
//              }
//            }
//          })
//          .show();
//    }
  }
}
