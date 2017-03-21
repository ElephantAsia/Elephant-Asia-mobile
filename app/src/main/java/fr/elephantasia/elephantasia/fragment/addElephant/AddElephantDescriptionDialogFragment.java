package fr.elephantasia.elephantasia.fragment.addElephant;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.activities.AddElephantActivity;

public class AddElephantDescriptionDialogFragment extends DialogFragment implements Dialog.OnDismissListener {

    public enum DialogType {
        HEIGHT,
        WEIGHT,
    }

    DialogType type;
    private String title;
    private String text;
    private String value;
    private List<String> units = new ArrayList<>();
    private Listener listener;

    EditText dialogInput;
    Spinner unitSpinner;

    /**
     * Create a new instance of AddElephantDescriptionDialogFragment, providing the type of dialog
     * as an argument.
     */
    public static AddElephantDescriptionDialogFragment newInstance(DialogType type) {
        AddElephantDescriptionDialogFragment f = new AddElephantDescriptionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("dialogType", type);
        f.setArguments(args);
        return f;
    }

    private void setupDialog(DialogType type) {
        if (type == DialogType.HEIGHT) {
            units = Arrays.asList("cm", "m", "ch");
            title = "Select height";
            text = "Height";
            value = ((AddElephantActivity)getActivity()).getElephantInfo().height;
        } else if (type == DialogType.WEIGHT) {
            units = Arrays.asList("kg", "lbs");
            title = "Select weight";
            text = "Weight";
            value = ((AddElephantActivity)getActivity()).getElephantInfo().weight;
        }

        // Remove unit from value
        if (!value.isEmpty()) {
            value = value.replaceAll("[^\\d.]", "");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        type = (DialogType) getArguments().getSerializable("dialogType");
        setupDialog(type);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.add_elephant_description_dialog_fragment, null);
        ((TextView) view.findViewById(R.id.add_elephant_description_dialog_title)).setText(title);
        ((TextView) view.findViewById(R.id.add_elephant_description_dialog_type)).setText(text);
        ArrayAdapter<String> spinnerUnits = new ArrayAdapter<>(getContext(), R.layout.ea_spinner, units);

        unitSpinner = (Spinner) view.findViewById(R.id.add_elephant_description_dialog_units);
        unitSpinner.setAdapter(spinnerUnits);
        dialogInput = (EditText) view.findViewById(R.id.add_elephant_description_dialog_input);
        dialogInput.setText(value);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String value = dialogInput.getText().toString();

                        if (!value.isEmpty()) {
                            value += " " + unitSpinner.getSelectedItem().toString();
                        }

                        if (type == DialogType.WEIGHT) {
                            listener.onWeightSet(value);
                        } else if (type == DialogType.HEIGHT) {
                            listener.onHeightSet(value);
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {}
                })
                .setView(view);

        return builder.create();
    }

    public void setListener(AddElephantDescriptionDialogFragment.Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onWeightSet(String weight);
        void onHeightSet(String height);
    }
}