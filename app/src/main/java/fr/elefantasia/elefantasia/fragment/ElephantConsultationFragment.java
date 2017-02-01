package fr.elefantasia.elefantasia.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.databinding.AddElephantRegistrationFragmentBinding;
import fr.elefantasia.elefantasia.databinding.ElephantConsultationElephantFragmentBinding;
import fr.elefantasia.elefantasia.interfaces.ConsultationInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

import static fr.elefantasia.elefantasia.utils.StaticTools.hideSoftKeyboard;

public class ElephantConsultationFragment extends Fragment {

    public enum State {
        CONSULTATION,
        EDITION
    }

    private ElephantInfo info;
    private FloatingActionButton fab;
    ElephantConsultationElephantFragmentBinding binding;

    private Button deleteButton;

    public ElephantConsultationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.elephant_consultation_elephant_fragment, container, false);
        View view = binding.getRoot();
        info = new ElephantInfo(((ConsultationInterface)getActivity()).getElephantInfo());
        binding.setE(info);
        binding.setMode(State.CONSULTATION);

        deleteButton = (Button) view.findViewById(R.id.elephant_consultation_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ConsultationInterface) getActivity()).deleteElephant(info.id);
            }
        });

        fab = (FloatingActionButton) view.findViewById(R.id.elephant_consultation_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.getMode() == State.CONSULTATION) {
                    fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_save));
                    binding.setMode(State.EDITION);
                } else {
                    fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_edit));
                    ((ConsultationInterface) getActivity()).updateElephant(info);
                    binding.setMode(State.CONSULTATION);
                }
            }
        });

        return view;
    }

    //TODO: add listener to hide keyboard
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        setupEditText(getView());
//    }
//
//    public void setupEditText(View view) {
//        // Set up touch listener for non-text box views to hide keyboard.
//        if (view != null && !(view instanceof EditText)) {
//            view.setOnTouchListener(new View.OnTouchListener() {
//                public boolean onTouch(View v, MotionEvent event) {
//                    hideSoftKeyboard(getActivity());
//                    return false;
//                }
//            });
//        }
//
//        //If a layout container, iterate over children and seed recursion.
//        if (view instanceof ViewGroup) {
//            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
//                View innerView = ((ViewGroup) view).getChildAt(i);
//                    setupEditText(innerView);
//            }
//        }
//    }
}