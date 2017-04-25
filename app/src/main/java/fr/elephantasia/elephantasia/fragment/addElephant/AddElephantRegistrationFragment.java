package fr.elephantasia.elephantasia.fragment.addElephant;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.elephantasia.databinding.AddElephantRegistrationFragmentBinding;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.StaticTools;


public class AddElephantRegistrationFragment extends Fragment {

    private ElephantInfo elephant;

    private EditText registrationLocationEditText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        AddElephantRegistrationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_registration_fragment, container, false);
        elephant = ((AddElephantActivity)getActivity()).getElephantInfo();
        View view = binding.getRoot();
        binding.setE(elephant);
        registrationLocationEditText = (EditText)view.findViewById(R.id.registration_location);
        registrationLocationEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddElephantActivity) getActivity()).showLocationDialog(AddElephantActivity.LocationType.REGISTRATION);
            }
        });

        StaticTools.setupHideKeyboardListener(view, getActivity());
        return (view);
    }

    //TODO: Ameliorer l affichage si il n y a pas de tous les inputs de set (pas de truncage, pas de tiret)
    public void setRegistrationLocation(String province, String district, String city) {
        elephant.regProvince = province;
        elephant.regDistrict = district;
        elephant.regCity = city;

        String prAbbr = province.length() > 3 ? province.substring(0,3) : province;
        String location = prAbbr.toUpperCase() + " - " + district + " - " + city;
        registrationLocationEditText.setText(location);
    }

    //TODO: Extraire la province, le district et la ville du place picker
    public void setRegistrationLocation(String location) {
//        elephant.regProvince = province;
//        elephant.regDistrict = district;
//        elephant.regCity = city;

        registrationLocationEditText.setText(location);
    }
}
