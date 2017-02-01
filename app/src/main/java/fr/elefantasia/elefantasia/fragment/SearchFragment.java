package fr.elefantasia.elefantasia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.SearchInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;
import fr.elefantasia.elefantasia.utils.StaticTools;

import static android.content.ContentValues.TAG;


public class SearchFragment extends Fragment {

    private EditText nameEditText;
    private EditText microchipEditText;
    private EditText registrationProvinceEditText;
    private EditText registrationDistrictEditText;
    private EditText registrationVillageEditText;

    private Spinner sexSpinner;
    private Spinner countrySpinner;
    private Button searchButton;

    public SearchFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        nameEditText = (EditText)view.findViewById(R.id.search_elephant_name);
        microchipEditText = (EditText)view.findViewById(R.id.search_elephant_microchip);
        registrationProvinceEditText = (EditText)view.findViewById(R.id.search_elephant_province);
        registrationDistrictEditText = (EditText)view.findViewById(R.id.search_elephant_district);
        registrationVillageEditText = (EditText)view.findViewById(R.id.search_elephant_village);

        searchButton = (Button)view.findViewById(R.id.search_button_search);

        sexSpinner = (Spinner)view.findViewById(R.id.search_elephant_sex);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),
                R.array.elephant_sex, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sexSpinner.setAdapter(adapter1);

        countrySpinner = (Spinner)view.findViewById(R.id.search_elephant_country);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),
                R.array.country, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter2);

        countrySpinner.setEnabled(false);

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
//        if (nameEditText.isEmpty() && microchipEditText.isEmpty()
//                && registrationProvinceEditText.isEmpty() && registrationDistrictEditText.isEmpty()
//                && registrationVillageEditText.isEmpty()) {
//            Toast.makeText(getActivity(), "At least one field must be set", Toast.LENGTH_LONG).show();
//            return false;
//        }
        return true;
    }

    private void sendData() {
        ElephantInfo elephantInfo = new ElephantInfo();

        elephantInfo.name = nameEditText.getText().toString();
        elephantInfo.chips1 = microchipEditText.getText().toString();

//        switch (sexSpinner.getSelectedItemPosition()) {
//            case 1:
//                elephantInfo.sex = ElephantInfo.Gender.MALE;
//                break;
//            case 2:
//                elephantInfo.sex = ElephantInfo.Gender.FEMALE;
//                break;
//        }

        ((SearchInterface) getActivity()).onClickSearch(elephantInfo);
    }

}
