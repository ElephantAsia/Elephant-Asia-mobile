package fr.elefantasia.elefantasia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

import static fr.elefantasia.elefantasia.activities.SearchBrowserActivity.EXTRA_ELEPHANT;

public class ElephantConsultationFragment extends Fragment {

    private ElephantInfo info;


    public ElephantConsultationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.elephant_consultation_elephant_fragment, container, false);

        Bundle args = getArguments();
        info = args.getParcelable(EXTRA_ELEPHANT);

        TextView name = (TextView)view.findViewById(R.id.search_overview_name);
        TextView registration = (TextView)view.findViewById(R.id.search_overview_registration);
        TextView location = (TextView)view.findViewById(R.id.search_overview_location);

        String sex = (info.sex == ElephantInfo.Gender.FEMALE) ? getActivity().getString(R.string.female) : getActivity().getString(R.string.male);
        name.setText(String.format(getActivity().getString(R.string.elephant_name_sex_age), info.name, sex, "-"));
        registration.setText(info.registrationID);
        location.setText(String.format(getActivity().getString(R.string.elephant_location), info.registrationVillage, info.registrationProvince));

        name = (TextView)view.findViewById(R.id.elephant_consultation_name);
        TextView nickname = (TextView)view.findViewById(R.id.elephant_consultation_nickname);
        TextView idNumber = (TextView)view.findViewById(R.id.elephant_consultation_identification_number);
        TextView chip = (TextView)view.findViewById(R.id.elephant_consultation_chip);
        TextView birthDate = (TextView)view.findViewById(R.id.elephant_consultation_birth_date);
        TextView edn = (TextView)view.findViewById(R.id.elephant_consultation_edn);
        TextView earTag = (TextView)view.findViewById(R.id.elephant_consultation_ear_tag);

        name.setText(info.name);
        nickname.setText(info.nickName);
        idNumber.setText(info.registrationID);
        chip.setText(info.chips1);
        birthDate.setText(info.birthDate);
        edn.setText(("00000000" + String.valueOf(info.id)).substring(String.valueOf(info.id).length()));

        if (info.earTag) {
            earTag.setText(getString(R.string.yes_enabled));
            earTag.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            earTag.setText(getString(R.string.no_disabled));
            earTag.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        return view;
    }

}
