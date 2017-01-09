package fr.elefantasia.elefantasia.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;
import fr.elefantasia.elefantasia.view.EA_EditText;


public class AddElephantRegistrationFragment extends Fragment {

    private FloatingActionButton fabNext;

    private EA_EditText nameEditText;
    private EA_EditText nicknameEditText;

    private RadioGroup sexRadioGroup;

    private CheckBox earTagCheckBox;
    private CheckBox eyedCheckBox;

    private EA_EditText birthDateEditText;
    private EA_EditText birthVillageEditText;
    private EA_EditText birthDistrictEditText;
    private EA_EditText birthProvinceEditText;

    private EA_EditText chipNumberEditText;

    private EA_EditText registrationIDEditText;
    private EA_EditText registrationVillageEditText;
    private EA_EditText registrationDistrictEditText;
    private EA_EditText registrationProvinceEditText;


    public AddElephantRegistrationFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_elephant_registration_fragment, container, false);

        nameEditText = (EA_EditText)view.findViewById(R.id.elephant_name);
        nicknameEditText = (EA_EditText)view.findViewById(R.id.elephant_nickname);
        sexRadioGroup = (RadioGroup)view.findViewById(R.id.elephant_rg_sex);
        earTagCheckBox = (CheckBox)view.findViewById(R.id.ear_tag);
        eyedCheckBox = (CheckBox)view.findViewById(R.id.eyeD);
        birthDateEditText = (EA_EditText)view.findViewById(R.id.elephant_birth_date);
        birthVillageEditText = (EA_EditText)view.findViewById(R.id.elephant_birth_village);
        birthDistrictEditText = (EA_EditText)view.findViewById(R.id.elephant_birth_district);
        birthProvinceEditText = (EA_EditText)view.findViewById(R.id.elephant_birth_province);
        chipNumberEditText = (EA_EditText)view.findViewById(R.id.elephant_chip);
        registrationIDEditText = (EA_EditText)view.findViewById(R.id.elephant_registration_id);
        registrationVillageEditText = (EA_EditText)view.findViewById(R.id.elephant_registration_village);
        registrationDistrictEditText = (EA_EditText)view.findViewById(R.id.elephant_registration_district);
        registrationProvinceEditText = (EA_EditText)view.findViewById(R.id.elephant_registration_province);

        fabNext = (FloatingActionButton)view.findViewById(R.id.elephant_registration_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm()) {
                    ((AddElephantInterface) getActivity()).nextPage();
                }
            }
        });

        nameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setName(s.toString());
            }
        });

        nicknameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setNickname(s.toString());
            }
        });

        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View view = group.findViewById(R.id.elephant_radio_male);
                if (view == group.findViewById(checkedId)) {
                    ((AddElephantInterface) getActivity()).setSex(ElephantInfo.Gender.MALE);
                } else {
                    ((AddElephantInterface) getActivity()).setSex(ElephantInfo.Gender.FEMALE);
                }
            }
        });

        earTagCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((AddElephantInterface) getActivity()).hasEarTag(isChecked);
            }
        });

        eyedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ((AddElephantInterface) getActivity()).hasEyeD(isChecked);
            }
        });

        birthDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setBirthdate(s.toString());
            }
        });

        birthVillageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setBirthVillage(s.toString());
            }
        });

        birthDistrictEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setBirthDistrict(s.toString());
            }
        });

        birthProvinceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setBirthProvince(s.toString());
            }
        });

        chipNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setChipNumber(s.toString());
            }
        });

        registrationIDEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setRegistrationID(s.toString());
            }
        });

        registrationVillageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setRegistrationVillage(s.toString());
            }
        });

        registrationDistrictEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setRegistrationDistrict(s.toString());
            }
        });

        registrationProvinceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setRegistrationProvince(s.toString());
            }
        });

        return (view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private boolean confirm() {
        return (confirmName() && confirmNickname() && confirmId() && confirmChip() && confirmSex()
                && confirmBirthdate());
    }

    private boolean confirmName() {
        if (nameEditText.getText().toString().trim().length() == 0) {
            nameEditText.setError(getResources().getString(R.string.name_empty));
            return false;
        }
        nameEditText.setError(null);
        return true;
    }

    private boolean confirmNickname() {
        if (nicknameEditText.getText().toString().trim().length() == 0) {
            nicknameEditText.setError(getResources().getString(R.string.nickname_empty));
            return false;
        }
        nicknameEditText.setError(null);
        return true;
    }

    private boolean confirmId() {
        if (registrationIDEditText.getText().toString().trim().length() == 0) {
            registrationIDEditText.setError(getResources().getString(R.string.id_number_empty));
            return false;
        }
        registrationIDEditText.setError(null);
        return true;
    }

    private boolean confirmChip() {
        if (chipNumberEditText.getText().toString().trim().length() == 0) {
            chipNumberEditText.setError(getResources().getString(R.string.chip_number_empty));
            return false;
        }
        chipNumberEditText.setError(null);
        return true;
    }

    private boolean confirmSex() {
        boolean ret = (sexRadioGroup.getCheckedRadioButtonId() != -1);

        if (!ret) {
            Toast.makeText(getActivity(), R.string.sex_empty, Toast.LENGTH_SHORT).show();
        }
        return ret;
    }

    private boolean confirmBirthdate() {
        if (birthDateEditText.getText().toString().trim().length() == 0) {
            birthDateEditText.setError(getResources().getString(R.string.birthdate_empty));
            return false;
        }
        birthDateEditText.setError(null);
        return true;
    }


    /*private Elefant createElefant(String name) {
        Elefant elefant = new Elefant();
        elefant.name = name;

        return (elefant);
    }*/

    /*private static class Adapter extends BaseAdapter {

        private Context context;

        List<Elefant> data = new ArrayList<>();

        public Adapter(@NonNull Context context) {
            this.context = context;
        }

        public void addData(List<Elefant> list) {
            data.addAll(list);
            notifyDataSetChanged();
        }

        public void addItem(Elefant item) {
            data.add(item);
            notifyDataSetChanged();
        }

        public void resetData() {
            this.data.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Elefant getItem(int index) {
            return data.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            boolean creation = view == null;

//            if (creation) {
//                view = inflater.inflate(R.layout.elefant_list_fragment, parent, false);
//            }

            Elefant elefant = getItem(index);

            refreshView(view, creation, elefant);
            refreshContent(view, creation, elefant);

            return view;
        }

        private void refreshView(View view, boolean creation, final Elefant Elefant) {
            if (creation) {
                view.setFocusable(true);
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                }
            });

        }

        private void refreshContent(View view, boolean creation, Elefant elefant) {
            //TextView id = (TextView) view.findViewById(R.id.etRegistrationNumber);
            //TextView name = (TextView) view.findViewById(R.id.etName);

            //id.setText(String.valueOf(elefant.id));
            //name.setText(elefant.name);
        }

    }*/

}
