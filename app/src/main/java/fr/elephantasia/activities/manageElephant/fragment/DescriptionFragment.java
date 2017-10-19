package fr.elephantasia.activities.manageElephant.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ManageElephantDescriptionFragmentBinding;
import fr.elephantasia.dialogs.GirthDialog;
import fr.elephantasia.dialogs.HeightDialog;
import fr.elephantasia.utils.KeyboardHelpers;


public class DescriptionFragment extends Fragment {

  // View binding
  @BindView(R.id.weight) TextView weightView;

  // Attr
  private Elephant elephant;

  // Listener binding
  @OnClick(R.id.height)
  public void showHeightDialog(EditText editText) {
    HeightDialog dialog = new HeightDialog(getActivity(), getContext(), elephant, editText);
    dialog.show();
  }

  @OnClick(R.id.girth)
  public void showWeightDialog(EditText girthView) {
    GirthDialog dialog = new GirthDialog(getActivity(), getContext(), elephant, weightView, girthView);
    dialog.show();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    ManageElephantDescriptionFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.manage_elephant_description_fragment, container, false);
    elephant = ((ManageElephantActivity) getActivity()).getElephant();
    View view = binding.getRoot();
    binding.setE(elephant);
    ButterKnife.bind(this, view);

    ArrayAdapter<String> adapterFront = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, Arrays.asList("0", "1", "2", "3", "4", "5"));
    ArrayAdapter<String> adapterRear = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, Arrays.asList("0", "1", "2", "3", "4"));

    ((Spinner) view.findViewById(R.id.nailsFrontLeft)).setAdapter(adapterFront);
    ((Spinner) view.findViewById(R.id.nailsFrontRight)).setAdapter(adapterFront);
    ((Spinner) view.findViewById(R.id.nailsRearLeft)).setAdapter(adapterRear);
    ((Spinner) view.findViewById(R.id.nailsRearRight)).setAdapter(adapterRear);

    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }


}