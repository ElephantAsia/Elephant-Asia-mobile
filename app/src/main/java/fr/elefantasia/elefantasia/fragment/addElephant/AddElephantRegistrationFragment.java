package fr.elefantasia.elefantasia.fragment.addElephant;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.activities.AddElephantActivity;
import fr.elefantasia.elefantasia.databinding.AddElephantRegistrationFragmentBinding;
import fr.elefantasia.elefantasia.utils.ElephantInfo;
import fr.elefantasia.elefantasia.utils.StaticTools;



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
            ((AddElephantActivity) getActivity()).nextPage();
            }
        });

        return (view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StaticTools.setupHideKeyboardListener(getView(), getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
