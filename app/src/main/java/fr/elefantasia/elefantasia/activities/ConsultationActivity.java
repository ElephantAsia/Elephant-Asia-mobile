package fr.elefantasia.elefantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.database.ElefantDatabase;
import fr.elefantasia.elefantasia.fragment.ElephantConsultationFragment;
import fr.elefantasia.elefantasia.interfaces.ConsultationInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

import static fr.elefantasia.elefantasia.activities.SearchBrowserActivity.EXTRA_ELEPHANT;

public class ConsultationActivity extends AppCompatActivity implements ConsultationInterface {

    private ElefantDatabase database;
    private ElephantInfo elephantInfo;

    private Toolbar toolbar;

    private static ElephantInfo getElephantInfo(Intent intent) {
        return (ElephantInfo)intent.getParcelableExtra(EXTRA_ELEPHANT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultation_activity);

        database = new ElefantDatabase(this);
        database.open();

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        elephantInfo = getElephantInfo(getIntent());
        refreshToolbarTitle();

        refreshFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        setResult(RESULT_OK);
        finish();
        return true;
    }

    @Override
    public void updateElephant(ElephantInfo info) {
        elephantInfo = new ElephantInfo(info);
        database.updateElephant(elephantInfo);
        refreshFragment();
        refreshToolbarTitle();
    }

    @Override
    public ElephantInfo getElephantInfo() {
        return elephantInfo;
    }

    private void refreshToolbarTitle() {
        String name = elephantInfo.name;
        String regID = elephantInfo.registrationID;
        String format = String.format(getString(R.string.elephant_consultation_title), name, regID);
        ((TextView)toolbar.findViewById(R.id.title)).setText(format);
    }

    private void refreshFragment() {
        ElephantConsultationFragment fragment = new ElephantConsultationFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_ELEPHANT, elephantInfo);
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().replace(R.id.consultation_fragment, fragment).commit();
    }
}
