package fr.elephantasia.fragment.addElephant;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import fr.elephantasia.R;
import fr.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.databinding.AddElephantProfilFragmentBinding;
import fr.elephantasia.dialogs.LocationDialog;
import fr.elephantasia.fragment.DatePickerFragment;
import fr.elephantasia.realm.Elephant;
import fr.elephantasia.utils.LocationHelpers;
import fr.elephantasia.utils.StaticTools;

public class ProfilFragment extends Fragment {

  Elephant elephant;
  private EditText birthDateEditText;
  private EditText birthLocationEditText;
  private EditText currentLocationEditText;
  // Mandatory fields
  private EditText name;
  private RadioButton male;
  private RadioButton female;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    AddElephantProfilFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_profil_fragment, container, false);
    elephant = ((AddElephantActivity) getActivity()).getElephant();
    View view = binding.getRoot();
    binding.setE(elephant);

    name = (EditText) view.findViewById(R.id.elephant_name);
    male = (RadioButton) view.findViewById(R.id.elephant_radio_male);
    female = (RadioButton) view.findViewById(R.id.elephant_radio_female);
    RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.elephant_rg_sex);
    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {
        removeSexError();
      }
    });

    birthDateEditText = (EditText) view.findViewById(R.id.elephant_birth_date);
    birthDateEditText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        DatePickerFragment dialog = new DatePickerFragment();
        dialog.setListener(new DatePickerFragment.Listener() {
          @Override
          public void onDateSet(int year, int month, int dayOfMonth) {
            birthDateEditText.setText(getString(R.string.date, year, month, dayOfMonth));
          }
        });
        ((AddElephantActivity) getActivity()).showDialogFragment(dialog);
      }
    });

    currentLocationEditText = (EditText) view.findViewById(R.id.elephant_current_location);
    currentLocationEditText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LocationDialog locationDialog = new LocationDialog(getActivity(),
            elephant.currentLoc,
            getString(R.string.set_current_location),
            AddElephantActivity.REQUEST_CURRENT_LOCATION,
            currentLocationEditText
        );

        locationDialog.show();

      }
    });

    birthLocationEditText = (EditText) view.findViewById(R.id.elephant_birth_location);
    birthLocationEditText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LocationDialog locationDialog = new LocationDialog(getActivity(),
            elephant.birthLoc,
            getString(R.string.set_birth_location),
            AddElephantActivity.REQUEST_BIRTH_LOCATION,
            birthLocationEditText);
        locationDialog.show();

        birthLocationEditText.setText(LocationHelpers.concat(elephant.birthLoc));
      }
    });

    StaticTools.setupHideKeyboardListener(view, getActivity());
    return (view);
  }

  public void setNameError() {
    name.setError(getText(R.string.name_required));
  }

  public void setSexError() {
    male.setError(getText(R.string.sex_required));
    female.setError(getText(R.string.sex_required));
  }

  public void removeSexError() {
    male.setError(null);
    female.setError(null);
  }

  /**
   * Set current location from map
   * TODO: Extraire la province, le district et la ville du place picker
   *
   * @param location the location returned from the map picker
   */
  public void setCurrentLocation(String location) {
//    elephant.currentLoc.provinceName = province;
//    elephant.currentLoc.districtName = district;
//    elephant.currentLoc.cityName = city;

    currentLocationEditText.setText(location);
  }

  //TODO: Extraire la province, le district et la ville du place picker

  /**
   * Set birth location from map
   *
   * @param location the location returned from the map picker
   */
  public void setBirthLocation(String location) {
//        elephant.birthProvince = province;
//        elephant.birthDistrict = district;
//        elephant.birthCity = city;

    birthLocationEditText.setText(location);
  }
}
