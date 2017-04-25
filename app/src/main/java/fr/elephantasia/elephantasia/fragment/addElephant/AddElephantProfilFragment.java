package fr.elephantasia.elephantasia.fragment.addElephant;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.DialogFragment;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.elephantasia.databinding.AddElephantProfilFragmentBinding;
import fr.elephantasia.elephantasia.databinding.AddElephantRegistrationFragmentBinding;

import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.elephantasia.databinding.AddElephantProfilFragmentBinding;
import fr.elephantasia.elephantasia.fragment.DatePickerFragment;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.StaticTools;

public class AddElephantProfilFragment extends Fragment {

  private EditText birthDateEditText;
  private EditText birthLocationEditText;
  private EditText currentLocationEditText;

  // Mandatory fields
  private EditText name;
  private RadioButton male;
  private RadioButton female;

  ElephantInfo elephant;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    AddElephantProfilFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_profil_fragment, container, false);
    elephant = ((AddElephantActivity) getActivity()).getElephantInfo();
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
        ((AddElephantActivity) getActivity()).showLocationDialog(AddElephantActivity.LocationType.CURRENT);
      }
    });


    birthLocationEditText = (EditText) view.findViewById(R.id.elephant_birth_location);
    birthLocationEditText.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ((AddElephantActivity) getActivity()).showLocationDialog(AddElephantActivity.LocationType.BIRTH);
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


  //TODO: Ameliorer l affichage si il n y a pas de tous les inputs de set (pas de truncage, pas de tiret)
  /**
   * Set current location manually using ET
   */
  public void setCurrentLocation(String province, String district, String city) {
    elephant.currentProvince = province;
    elephant.currentDistrict = district;
    elephant.currentCity = city;

    String prAbbr = province.length() > 3 ? province.substring(0, 3) : province;
    String location = prAbbr.toUpperCase() + " - " + district + " - " + city;
    currentLocationEditText.setText(location);
  }

  //TODO: Extraire la province, le district et la ville du place picker

  /**
   * Set current location from map
   * @param location the location returned from the map picker
   */
  public void setCurrentLocation(String location) {
//        elephant.birthProvince = province;
//        elephant.birthDistrict = district;
//        elephant.birthCity = city;

    currentLocationEditText.setText(location);
  }

  //TODO: Ameliorer l affichage si il n y a pas de tous les inputs de set (pas de truncage, pas de tiret)
  /**
   * Set birth location manually
   */
  public void setBirthLocation(String province, String district, String city) {
    elephant.birthProvince = province;
    elephant.birthDistrict = district;
    elephant.birthCity = city;

    String prAbbr = province.length() > 3 ? province.substring(0, 3) : province;
    String location = prAbbr.toUpperCase() + " - " + district + " - " + city;
    birthLocationEditText.setText(location);
  }

  //TODO: Extraire la province, le district et la ville du place picker
  /**
   * Set birth location from map
   * @param location the location returned from the map picker
   */
  public void setBirthLocation(String location) {
//        elephant.birthProvince = province;
//        elephant.birthDistrict = district;
//        elephant.birthCity = city;

    birthLocationEditText.setText(location);
  }
}
