package fr.elephantasia.activities.addElephant.fragment;


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

import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.AddElephantDescriptionFragmentBinding;
import fr.elephantasia.dialogs.HeightDialog;
import fr.elephantasia.dialogs.WeightDialog;
import fr.elephantasia.utils.KeyboardHelpers;


public class DescriptionFragment extends Fragment {

  // Attr
  private Elephant elephant;

  // Listener binding
  @OnClick(R.id.height)
  public void showHeightDialog(EditText editText) {
    HeightDialog dialog = new HeightDialog(getActivity(), getContext(), elephant, editText);
    dialog.show();
  }

  @OnClick(R.id.weight)
  public void showWeightDialog(EditText editText) {
    WeightDialog dialog = new WeightDialog(getActivity(), getContext(), elephant, editText);
    dialog.show();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    AddElephantDescriptionFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_description_fragment, container, false);
    elephant = ((AddElephantActivity) getActivity()).getElephant();
    View view = binding.getRoot();
    binding.setE(elephant);
    ButterKnife.bind(this, view);

    ArrayAdapter<String> adapterFront = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, Arrays.asList("0", "1", "2", "3", "4"));
    ArrayAdapter<String> adapterRear = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, Arrays.asList("0", "1", "2", "3", "4", "5"));

    ((Spinner) view.findViewById(R.id.nailsFrontLeft)).setAdapter(adapterFront);
    ((Spinner) view.findViewById(R.id.nailsFrontRight)).setAdapter(adapterFront);
    ((Spinner) view.findViewById(R.id.nailsRearLeft)).setAdapter(adapterRear);
    ((Spinner) view.findViewById(R.id.nailsRearRight)).setAdapter(adapterRear);

    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }


}