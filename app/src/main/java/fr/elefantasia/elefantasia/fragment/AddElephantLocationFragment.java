package fr.elefantasia.elefantasia.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;

public class AddElephantLocationFragment extends Fragment {

    private FloatingActionButton fabNext;

    public AddElephantLocationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_elephant_location_fragment, container, false);

        fabNext = (FloatingActionButton)view.findViewById(R.id.elephant_location_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddElephantInterface) getActivity()).next();
            }
        });

        return (view);
    }

}