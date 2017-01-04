package fr.elefantasia.elefantasia.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.fragment.MainFragment;


public class MainActivity extends AppCompatActivity implements IPushMe {

    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        fragment = new MainFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_fragment, fragment).commit();
    }

    @Override
    public void push() {
        Toast.makeText(this, "pushed", Toast.LENGTH_SHORT).show();
    }
}
