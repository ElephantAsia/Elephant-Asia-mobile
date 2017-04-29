package fr.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import fr.elephantasia.R;
import fr.elephantasia.database.Database;
import fr.elephantasia.fragment.ElephantConsultationFragment;
import fr.elephantasia.interfaces.ConsultationInterface;
import fr.elephantasia.utils.ElephantInfo;

public class ConsultationActivity extends AppCompatActivity implements ConsultationInterface {

  public static final String EXTRA_ELEPHANT = "elephant";

  private Database database;
  private ElephantInfo elephantInfo;

  private Toolbar toolbar;

  private static ElephantInfo getElephantID(Intent intent) {
    return intent.getParcelableExtra(EXTRA_ELEPHANT);
  }

  public static void setElephant(Intent intent, ElephantInfo info) {
    intent.putExtra(EXTRA_ELEPHANT, info);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.consultation_activity);

    database = new Database(this);
    database.open();

    toolbar = (Toolbar) findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    elephantInfo = getElephantID(getIntent());
    refreshToolbarTitle();

    refreshFragment();
  }

  @Override
  public boolean onSupportNavigateUp() {
    setResult(RESULT_OK);
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    return true;
  }

  @Override
  public ElephantInfo getElephantInfo() {
    return elephantInfo;
  }

  @Override
  public void updateElephant(ElephantInfo info) {
    elephantInfo = new ElephantInfo(info);
    database.update(elephantInfo);
    refreshFragment();
    refreshToolbarTitle();
  }

  @Override
  public void deleteElephant(ElephantInfo info) {
    database.delete(info);
    setResult(RESULT_OK);
    Toast.makeText(this, "Elephant deleted with success", Toast.LENGTH_SHORT).show();
    finish();
    overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
  }

  private void refreshToolbarTitle() {
    String name = elephantInfo.name;
    String regID = elephantInfo.regID;
    String format = String.format(getString(R.string.elephant_consultation_title), name, regID);
    ((TextView) toolbar.findViewById(R.id.title)).setText(format);
  }

  private void refreshFragment() {
    ElephantConsultationFragment fragment = new ElephantConsultationFragment();
    getSupportFragmentManager().beginTransaction().replace(R.id.consultation_fragment, fragment).commit();
  }
}
