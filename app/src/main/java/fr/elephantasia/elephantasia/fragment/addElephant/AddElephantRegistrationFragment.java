package fr.elephantasia.elephantasia.fragment.addElephant;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.elephantasia.databinding.AddElephantRegistrationFragmentBinding;
import fr.elephantasia.elephantasia.fragment.DatePickerFragment;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.StaticTools;


public class AddElephantRegistrationFragment extends Fragment {

    private EditText birthDateEditText;

    //Mandatory field
    private EditText name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AddElephantRegistrationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_registration_fragment, container, false);
        final ElephantInfo elephant = ((AddElephantActivity)getActivity()).getElephantInfo();
        View view = binding.getRoot();
        binding.setE(elephant);

        name = (EditText)view.findViewById(R.id.elephant_name);
        birthDateEditText = (EditText)view.findViewById(R.id.elephant_birth_date);
        birthDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.setListener(new DatePickerFragment.Listener() {
                    @Override
                    public void onDateSet(int year, int month, int dayOfMonth) {
                        birthDateEditText.setText(getString(R.string.date, year, month, dayOfMonth));
                    }
                });
                ((AddElephantActivity)getActivity()).showDialogFragment(dialog);
            }
        });
        StaticTools.setupHideKeyboardListener(view, getActivity());
        return (view);
    }


    public void setNameError() {
        name.setError("Name is required");
    }
}
