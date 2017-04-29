package fr.elephantasia.fragment.addElephant;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.Arrays;

import fr.elephantasia.R;
import fr.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.databinding.AddElephantDescriptionFragmentBinding;
import fr.elephantasia.utils.ElephantInfo;
import fr.elephantasia.utils.StaticTools;


public class DescriptionFragment extends Fragment {
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
    final ElephantInfo elephant = ((AddElephantActivity) getActivity()).getElephantInfo();
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

    final View dialogView = inflater.inflate(R.layout.add_elephant_description_dialog_fragment, null);

    final EditText dialogInput = (EditText) dialogView.findViewById(R.id.add_elephant_description_dialog_input);
    final Spinner dialogUnit = (Spinner) dialogView.findViewById(R.id.add_elephant_description_dialog_units);

    // Weight and Height use the same layout and set the spinner unit dynamically
    weightEditText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ArrayAdapter<String> spinnerUnits = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, Arrays.asList("kb", "lbs"));
        dialogUnit.setAdapter(spinnerUnits);
        dialogInput.setText(elephant.weight);
        new MaterialDialog.Builder(getActivity())
            .title(R.string.set_weight)
            .positiveText(R.string.OK)
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                elephant.weight = dialogInput.getText().toString();
                weightEditText.setText(elephant.weight);
              }
            })
            .negativeText(R.string.CANCEL)
            .onNegative(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
              }
            })
            .customView(dialogView, true)
            .show();
      }
    });

    // Weight and Height use the same layout and set the spinner unit dynamically
    heightEditText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ArrayAdapter<String> spinnerUnits = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, Arrays.asList("cm", "m"));
        dialogUnit.setAdapter(spinnerUnits);
        dialogInput.setText(elephant.height);
        new MaterialDialog.Builder(getActivity())
            .title(R.string.set_height)
            .positiveText(R.string.OK)
            .onPositive(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                elephant.height = dialogInput.getText().toString();
                heightEditText.setText(elephant.height);
              }
            })
            .negativeText(R.string.CANCEL)
            .onNegative(new MaterialDialog.SingleButtonCallback() {
              @Override
              public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
              }
            })
            .customView(dialogView, true)
            .show();
      }
    });


    StaticTools.setupHideKeyboardListener(view, getActivity());
    return (view);
  }


}