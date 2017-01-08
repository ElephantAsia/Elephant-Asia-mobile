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
import android.widget.Toast;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;
import fr.elefantasia.elefantasia.utils.ElephantInfo;
import fr.elefantasia.elefantasia.view.EA_EditText;


public class AddElephantCivilStatusFragment extends Fragment {

    //private ElefantDatabase database;
    //private LinearLayout addLayout;

    private FloatingActionButton fabNext;

    private EA_EditText nameEditText;
    private EA_EditText nicknameEditText;
    private EA_EditText idNumberEditText;
    private EA_EditText chipNumberEditText;
    private RadioGroup sexRadioGroup;
    private EA_EditText birthDateEditText;


    public AddElephantCivilStatusFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_elephant_civil_status_fragment, container, false);

        /*listView = (ListView) view.findViewById('R.id.main_list);
        listView.setAdapter(adapter);

        database = new ElefantDatabase(getActivity());
        database.open();

        Elefant elefant = createElefant("stephouuu");
        Elefant elefant1 = createElefant("robert");

        database.insertElefant(elefant);
        database.insertElefant(elefant1);

        adapter.addData(database.getElefantWithName("robert"));*/

        nameEditText = (EA_EditText)view.findViewById(R.id.elephant_name);
        nicknameEditText = (EA_EditText)view.findViewById(R.id.elephant_nickname);
        idNumberEditText = (EA_EditText)view.findViewById(R.id.elephant_id_number);
        chipNumberEditText = (EA_EditText)view.findViewById(R.id.elephant_chip);
        sexRadioGroup = (RadioGroup)view.findViewById(R.id.elephant_rg_sex);
        birthDateEditText = (EA_EditText)view.findViewById(R.id.elephant_birthdate);

        fabNext = (FloatingActionButton)view.findViewById(R.id.elephant_civil_status_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (confirm()) {
                    ((AddElephantInterface) getActivity()).next();
                }
            }
        });

        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    confirmName();
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
                ((AddElephantInterface) getActivity()).setElephantName(s.toString());
            }
        });

        nicknameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    confirmNickname();
                }
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
                ((AddElephantInterface) getActivity()).setElephantNickname(s.toString());
            }
        });

        idNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    confirmId();
                }
            }
        });

        idNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                ((AddElephantInterface) getActivity()).setElephantIDNumber(s.toString());
            }
        });

        chipNumberEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    confirmChip();
                }
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
                ((AddElephantInterface) getActivity()).setElephantChipNumber(s.toString());
            }
        });

        sexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View view = group.findViewById(R.id.elephant_radio_male);
                if (view == group.findViewById(checkedId)) {
                    ((AddElephantInterface) getActivity()).setElephantSex(ElephantInfo.Gender.MALE);
                } else {
                    ((AddElephantInterface) getActivity()).setElephantSex(ElephantInfo.Gender.FEMALE);
                }
            }
        });

        birthDateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    confirmBirthdate();
                }
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
                ((AddElephantInterface) getActivity()).setElephantBirthdate(s.toString());
            }
        });

        return (view);
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
        if (idNumberEditText.getText().toString().trim().length() == 0) {
            idNumberEditText.setError(getResources().getString(R.string.id_number_empty));
            return false;
        }
        idNumberEditText.setError(null);
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
