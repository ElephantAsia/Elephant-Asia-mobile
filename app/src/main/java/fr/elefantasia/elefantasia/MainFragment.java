package fr.elefantasia.elefantasia;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.elefantasia.elefantasia.R;

public class MainFragment extends Fragment {

    private Button pushme;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        pushme = (Button) view.findViewById(R.id.main_button);

        pushme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).push();
            }
        });

        return (view);
    }

}
