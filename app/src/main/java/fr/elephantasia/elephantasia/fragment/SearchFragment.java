package fr.elephantasia.elephantasia.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.databinding.SearchFragmentBinding;
import fr.elephantasia.elephantasia.interfaces.SearchInterface;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.StaticTools;


public class SearchFragment extends Fragment {

    private Spinner sexSpinner;
    private Spinner countrySpinner;
    private Button searchButton;
    private ElephantInfo elephantInfo = new ElephantInfo();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SearchFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.search_fragment, container, false);
        View view = binding.getRoot();
        binding.setE(elephantInfo);
        searchButton = (Button)view.findViewById(R.id.search_button_search);

        sexSpinner = (Spinner)view.findViewById(R.id.search_elephant_sex);
        countrySpinner = (Spinner)view.findViewById(R.id.search_elephant_country);
        countrySpinner.setEnabled(false);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
        StaticTools.setupHideKeyboardListener(view, getActivity());
        return (view);
    }

    private void sendData() {

        switch (sexSpinner.getSelectedItemPosition()) {
            case 1:
                elephantInfo.sex = ElephantInfo.Gender.MALE;
                break;
            case 2:
                elephantInfo.sex = ElephantInfo.Gender.FEMALE;
                break;
        }

        ((SearchInterface) getActivity()).onClickSearch(elephantInfo);
    }

}
