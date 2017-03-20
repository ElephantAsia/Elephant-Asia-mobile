package fr.elephantasia.elephantasia.fragment.addElephant;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.elephantasia.databinding.AddElephantDescriptionFragmentBinding;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.StaticTools;


public class AddElephantDescriptionFragment extends Fragment {
    private EditText weightEditText;
    private EditText heightEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        AddElephantDescriptionFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_description_fragment, container, false);
        final ElephantInfo elephant = ((AddElephantActivity)getActivity()).getElephantInfo();
        View view = binding.getRoot();
        binding.setE(elephant);

        ArrayAdapter<String> adapterFront = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, Arrays.asList("0", "1", "2", "3", "4"));
        ArrayAdapter<String> adapterRear = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, Arrays.asList("0", "1", "2", "3", "4", "5"));

        ((Spinner) view.findViewById(R.id.elephant_nails_front_left)).setAdapter(adapterFront);
        ((Spinner) view.findViewById(R.id.elephant_nails_front_right)).setAdapter(adapterFront);
        ((Spinner) view.findViewById(R.id.elephant_nails_rear_right)).setAdapter(adapterRear);
        ((Spinner) view.findViewById(R.id.elephant_nails_rear_left)).setAdapter(adapterRear);

        weightEditText = (EditText) view.findViewById(R.id.elephant_weight);
        heightEditText = (EditText) view.findViewById(R.id.elephant_height);

        final AddElephantDescriptionDialogFragment.Listener unitListener = new AddElephantDescriptionDialogFragment.Listener() {
            @Override
            public void onHeightSet(String height) {
                heightEditText.setText(height);
            }

            @Override
            public void onWeightSet(String weight) {
                weightEditText.setText(weight);
            }
        };

        weightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddElephantDescriptionDialogFragment tmp = AddElephantDescriptionDialogFragment.newInstance(AddElephantDescriptionDialogFragment.DialogType.WEIGHT);
                tmp.setListener(unitListener);
                tmp.show(getFragmentManager(), "");
            }
        });

        heightEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddElephantDescriptionDialogFragment tmp = AddElephantDescriptionDialogFragment.newInstance(AddElephantDescriptionDialogFragment.DialogType.HEIGHT);
                tmp.setListener(unitListener);
                tmp.show(getFragmentManager(), "");
            }
        });


        StaticTools.setupHideKeyboardListener(view, getActivity());
        return (view);
    }


}