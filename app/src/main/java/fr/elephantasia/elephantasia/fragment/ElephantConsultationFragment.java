package fr.elephantasia.elephantasia.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.databinding.ElephantConsultationElephantFragmentBinding;
import fr.elephantasia.elephantasia.interfaces.ConsultationInterface;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.StaticTools;


public class ElephantConsultationFragment extends Fragment {

    public enum State {
        CONSULTATION,
        EDITION
    }

    private ElephantInfo info;
    private FloatingActionButton fab;
    ElephantConsultationElephantFragmentBinding binding;

    private Button deleteButton;

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
                ((ConsultationInterface) getActivity()).deleteElephant(info);
            }
        });

        fab = (FloatingActionButton) view.findViewById(R.id.elephant_consultation_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.getMode() == State.CONSULTATION) {
                    fab.setImageResource(android.R.drawable.ic_menu_save);
                    binding.setMode(State.EDITION);
                } else {
                    fab.setImageResource(android.R.drawable.ic_menu_edit);

                    ((ConsultationInterface) getActivity()).updateElephant(info);
                    binding.setMode(State.CONSULTATION);
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StaticTools.setupHideKeyboardListener(getView(), getActivity());
    }
}