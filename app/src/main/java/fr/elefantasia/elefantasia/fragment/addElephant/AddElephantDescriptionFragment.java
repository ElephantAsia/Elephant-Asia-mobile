package fr.elefantasia.elefantasia.fragment.addElephant;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;

public class AddElephantDescriptionFragment extends Fragment {

    private FloatingActionButton fabNext;

    private Spinner tuskSpinner;
    private Spinner nailsFrontLeft;
    private Spinner nailsFrontRight;
    private Spinner nailsRearLeft;
    private Spinner nailsRearRight;

    private EditText weightEditText;
    private EditText heightEditText;


    public AddElephantDescriptionFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_elephant_description_fragment, container, false);

        fabNext = (FloatingActionButton)view.findViewById(R.id.elephant_description_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddElephantInterface) getActivity()).nextPage();
            }
        });

        List<String> maxFront = Arrays.asList("0", "1", "2", "3", "4");
        List<String> maxRear = Arrays.asList("0", "1", "2", "3", "4", "5");

        ArrayAdapter<String> adapterFront = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, maxFront);
        ArrayAdapter<String> adapterRear = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, maxRear);

        nailsFrontLeft = (Spinner) view.findViewById(R.id.elephant_nails_front_left);
        nailsFrontRight = (Spinner) view.findViewById(R.id.elephant_nails_front_right);
        nailsRearLeft = (Spinner) view.findViewById(R.id.elephant_nails_rear_left);
        nailsRearRight = (Spinner) view.findViewById(R.id.elephant_nails_rear_right);

        nailsFrontLeft.setAdapter(adapterFront);
        nailsFrontRight.setAdapter(adapterFront);
        nailsRearLeft.setAdapter(adapterRear);
        nailsRearRight.setAdapter(adapterRear);

        weightEditText = (EditText) view.findViewById(R.id.elephant_weight);
        heightEditText = (EditText) view.findViewById(R.id.elephant_height);

        return (view);
    }



}