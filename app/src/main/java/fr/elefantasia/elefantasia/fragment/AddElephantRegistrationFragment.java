package fr.elefantasia.elefantasia.fragment;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.activities.AddElephantActivity;
import fr.elefantasia.elefantasia.databinding.AddElephantRegistrationFragmentBinding;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

import static fr.elefantasia.elefantasia.utils.StaticTools.hideSoftKeyboard;


public class AddElephantRegistrationFragment extends Fragment {

    private FloatingActionButton fabNext;
    public AddElephantRegistrationFragment() {}

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

        fabNext = (FloatingActionButton)view.findViewById(R.id.elephant_registration_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm()) {
                    ((AddElephantActivity) getActivity()).nextPage();
                }
            }
        });

        return (view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupEditText(getView());
    }

    public void setupEditText(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(getActivity());
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupEditText(innerView);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean confirm() {
        return true;
//        return (confirmName() && confirmNickname() && confirmId() && confirmChip() && confirmSex()
//                && confirmBirthdate());
    }
}
