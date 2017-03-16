package fr.elephantasia.elephantasia.fragment.addElephant;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.interfaces.AddElephantInterface;

public class AddElephantConsultationFragment extends Fragment {

    private FloatingActionButton fabNext;

    public AddElephantConsultationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_elephant_consultation_fragment, container, false);

        fabNext = (FloatingActionButton)view.findViewById(R.id.elephant_consultation_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddElephantInterface) getActivity()).nextPage();
            }
        });

        return (view);
    }

}