package fr.elefantasia.elefantasia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import fr.elefantasia.elefantasia.R;

public class MainFragment extends Fragment {

    public MainFragment() {
        // Required empty public constructor
    }

    private LinearLayout addLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        addLayout = (LinearLayout) view.findViewById(R.id.addView);

        return (view);
    }

}
