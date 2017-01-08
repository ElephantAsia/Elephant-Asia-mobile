package fr.elefantasia.elefantasia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.SearchInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;
import fr.elefantasia.elefantasia.view.EA_EditText;


public class SearchFragment extends Fragment {

    private EA_EditText nameEditText;
    private Button searchButton;


    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        nameEditText = (EA_EditText)view.findViewById(R.id.search_elephant_name);
        searchButton = (Button)view.findViewById(R.id.search_button_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm()) {
                    sendData();
                }
            }
        });

        return (view);
    }

    private boolean confirm() {
        return (confirmName());
    }

    private boolean confirmName() {
        if (nameEditText.getText().toString().trim().length() == 0) {
            nameEditText.setError(getResources().getString(R.string.name_empty));
            return false;
        }
        nameEditText.setError(null);
        return true;
    }

    private void sendData() {
        ElephantInfo elephantInfo = new ElephantInfo();

        elephantInfo.name = nameEditText.getText().toString();

        ((SearchInterface) getActivity()).onClickSearch(elephantInfo);
    }

}
