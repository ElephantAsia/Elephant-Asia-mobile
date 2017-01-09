package fr.elefantasia.elefantasia.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.ConsultationInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;
import fr.elefantasia.elefantasia.view.EA_EditText;

public class ElephantConsultationFragment extends Fragment {

    private enum State {
        CONSULTATION,
        EDITION
    }

    private ElephantInfo info;
    private FloatingActionButton fab;
    private State state;

    private RelativeLayout header;

    private TextView nameHeaderTextView;
    private TextView registrationHeaderTextView;
    private TextView locationHeaderTextView;

    private TextView nameTextView;
    private TextView nickNameTextView;
    private TextView idNumberTextView;
    private TextView chipTextView;
    private TextView birthDateTextView;
    private TextView ednTextView;
    private TextView earTagTextView;

    private RadioGroup sexRadioGroup;

    private EA_EditText nameEditText;
    private EA_EditText idNumberEditText;
    private EA_EditText nickNameEditText;
    private EA_EditText chipEditText;
    private EA_EditText birthDateEditText;

    public ElephantConsultationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.elephant_consultation_elephant_fragment, container, false);

        header = (RelativeLayout) view.findViewById(R.id.elephant_consultation_header);

        nameHeaderTextView = (TextView) view.findViewById(R.id.elephant_consultation_header_name);
        registrationHeaderTextView = (TextView) view.findViewById(R.id.elephant_consultation_header_registration);
        locationHeaderTextView = (TextView) view.findViewById(R.id.elephant_consultation_header_location);

        nameTextView = (TextView) view.findViewById(R.id.elephant_consultation_name);
        nickNameTextView = (TextView) view.findViewById(R.id.elephant_consultation_nickname);
        idNumberTextView = (TextView) view.findViewById(R.id.elephant_consultation_identification_number);
        chipTextView = (TextView) view.findViewById(R.id.elephant_consultation_chip);
        birthDateTextView = (TextView) view.findViewById(R.id.elephant_consultation_birth_date);
        ednTextView = (TextView) view.findViewById(R.id.elephant_consultation_edn);
        earTagTextView = (TextView) view.findViewById(R.id.elephant_consultation_ear_tag);

        sexRadioGroup = (RadioGroup) view.findViewById(R.id.elephant_rg_sex);

        nameEditText = (EA_EditText) view.findViewById(R.id.elephant_consultation_edit_name);
        idNumberEditText = (EA_EditText) view.findViewById(R.id.elephant_consultation_edit_identification_number);
        nickNameEditText = (EA_EditText )view.findViewById(R.id.elephant_consultation_edit_nickname);
        chipEditText = (EA_EditText) view.findViewById(R.id.elephant_consultation_edit_chip);
        birthDateEditText = (EA_EditText) view.findViewById(R.id.elephant_consultation_edit_birth_date);

        fab = (FloatingActionButton) view.findViewById(R.id.elephant_consultation_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == State.CONSULTATION) {
                    updateState(State.EDITION);
                } else {
                    if (state == State.EDITION) {
                        ((ConsultationInterface) getActivity()).updateElephant(info);
                    }
                    updateState(State.CONSULTATION);
                }
            }
        });

        info = new ElephantInfo(((ConsultationInterface)getActivity()).getElephantInfo());
        updateState(State.CONSULTATION);

        return view;
    }

    private void updateState(State state) {
        this.state = state;

        switch (this.state) {
            case EDITION:
                setEditionMode();
                break;
            default:
                setConsultationMode();
        }
    }

    private void setConsultationMode() {
        destroyEditionMode();

        String sex = (info.sex == ElephantInfo.Gender.FEMALE) ? getActivity().getString(R.string.female) : getActivity().getString(R.string.male);
        nameHeaderTextView.setText(String.format(getActivity().getString(R.string.elephant_name_sex_age), info.name, sex, "-"));
        registrationHeaderTextView.setText(info.registrationID);
        locationHeaderTextView.setText(String.format(getActivity().getString(R.string.elephant_location), info.registrationVillage, info.registrationProvince));

        nameTextView.setText(info.name);
        nickNameTextView.setText(info.nickName);
        idNumberTextView.setText(info.registrationID);
        chipTextView.setText(info.chips1);
        birthDateTextView.setText(info.birthDate);
        ednTextView.setText(("00000000" + String.valueOf(info.id)).substring(String.valueOf(info.id).length()));

        if (info.earTag) {
            earTagTextView.setText(getString(R.string.yes_enabled));
            earTagTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            earTagTextView.setText(getString(R.string.no_disabled));
            earTagTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    private void destroyConsultationMode() {
        header.setVisibility(View.GONE);
        fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_save));

        nameTextView.setVisibility(View.GONE);
        nickNameTextView.setVisibility(View.GONE);
        idNumberTextView.setVisibility(View.GONE);
        chipTextView.setVisibility(View.GONE);
        birthDateTextView.setVisibility(View.GONE);

        nameEditText.setVisibility(View.VISIBLE);
        nickNameEditText.setVisibility(View.VISIBLE);
        idNumberEditText.setVisibility(View.VISIBLE);
        chipEditText.setVisibility(View.VISIBLE);
        birthDateEditText.setVisibility(View.VISIBLE);
    }

    private void setEditionMode() {
        destroyConsultationMode();

        nameEditText.setText(info.name);
        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                info.name = s.toString();
            }
        });

        nickNameEditText.setText(info.nickName);
        nickNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                info.nickName = s.toString();
            }
        });

        idNumberEditText.setText(info.registrationID);
        idNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                info.registrationID = s.toString();
            }
        });

        chipEditText.setText(info.chips1);
        chipEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                info.chips1 = s.toString();
            }
        });

        sexRadioGroup.setVisibility(View.VISIBLE);
        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View view = group.findViewById(R.id.elephant_radio_male);
                if (view == group.findViewById(checkedId)) {
                    info.sex = ElephantInfo.Gender.MALE;
                } else {
                    info.sex = ElephantInfo.Gender.FEMALE;
                }
            }
        });
        if (info.sex == ElephantInfo.Gender.MALE) {
            sexRadioGroup.check(R.id.elephant_radio_male);
        } else {
            sexRadioGroup.check(R.id.elephant_radio_female);
        }

        birthDateEditText.setText(info.birthDate);
        birthDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                info.birthDate = s.toString();
            }
        });
    }

    private void destroyEditionMode() {
        header.setVisibility(View.VISIBLE);
        fab.setImageDrawable(getResources().getDrawable(android.R.drawable.ic_menu_edit));

        nameTextView.setVisibility(View.VISIBLE);
        nickNameTextView.setVisibility(View.VISIBLE);
        idNumberTextView.setVisibility(View.VISIBLE);
        chipTextView.setVisibility(View.VISIBLE);
        birthDateTextView.setVisibility(View.VISIBLE);

        sexRadioGroup.setVisibility(View.GONE);

        nameEditText.setVisibility(View.GONE);
        nickNameEditText.setVisibility(View.GONE);
        idNumberEditText.setVisibility(View.GONE);
        chipEditText.setVisibility(View.GONE);
        birthDateEditText.setVisibility(View.GONE);
    }
}