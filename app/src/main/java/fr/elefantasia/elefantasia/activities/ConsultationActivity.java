package fr.elefantasia.elefantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.fragment.ElephantConsultationFragment;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

import static fr.elefantasia.elefantasia.activities.SearchBrowserActivity.EXTRA_ELEPHANT;

public class ConsultationActivity extends AppCompatActivity {

    private ElephantInfo elephantInfo;

    private static ElephantInfo getElephantInfo(Intent intent) {
        return (ElephantInfo)intent.getParcelableExtra(EXTRA_ELEPHANT);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.consultation_activity);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        elephantInfo = getElephantInfo(getIntent());
        refreshToolbarTitle(toolbar);

        refreshFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void refreshToolbarTitle(Toolbar toolbar) {
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
