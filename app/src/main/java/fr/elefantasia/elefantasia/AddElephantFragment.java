package fr.elefantasia.elefantasia;


import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import fr.elefantasia.elefantasia.R;

public class AddElephantFragment extends Fragment {

    public AddElephantFragment() {
        // Required empty public constructor
    }

    private Button pushme;
    private ElephantInfo elephantedit;
    private EditText etName;
    private EditText etRegistrationNumber;
    private EditText etDBid;
    private EditText etBirthDate;
    private RadioGroup sex;
    private RadioGroup legality;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.main_fragment, container, false);

        elephantedit = new ElephantInfo();

        etName = (EditText) view.findViewById(R.id.etName);
        etRegistrationNumber = (EditText) view.findViewById(R.id.etRegistrationNumber);
        etDBid = (EditText) view.findViewById(R.id.etDBid);
        etBirthDate = (EditText) view.findViewById(R.id.etBirthDate);
        sex = (RadioGroup) view.findViewById(R.id.rgSex);
        legality = (RadioGroup) view.findViewById(R.id.rgLegality);

        pushme = (Button) view.findViewById(R.id.mainButton);
        pushme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elephantedit.setName(etName.getText().toString());
                elephantedit.setRegistrationName(etRegistrationNumber.getText().toString());
                elephantedit.setDatabaseNumber(etDBid.getText().toString());
                elephantedit.setBornDate(etBirthDate.getText().toString());
                int id = sex.getCheckedRadioButtonId();
                switch (id) {
                    case R.id.rbFemale:
                        elephantedit.setSex(ElephantInfo.Gender.FEMALE);
                        break;
                    case R.id.rbMale:
                        elephantedit.setSex(ElephantInfo.Gender.MALE);
                        break;
                    case -1:
                        elephantedit.setSex(ElephantInfo.Gender.UNKNOWN);
                        break;
                }

                int id2 = legality.getCheckedRadioButtonId();

                switch (id2) {
                    case R.id.rbLegallyNo:
                        elephantedit.setLegallyRegistered(ElephantInfo.Legallity.ILLEGAL);
                        break;
                    case R.id.rbLegallyYes:
                        elephantedit.setLegallyRegistered(ElephantInfo.Legallity.LEGAL);
                        break;
                    case -1:
                        elephantedit.setLegallyRegistered(ElephantInfo.Legallity.UNKNOWN);
                        break;
                }
            }
        });
        return (view);
    }
}
