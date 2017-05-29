package fr.elephantasia.activities.addElephant.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.AddElephantProfilFragmentBinding;
import fr.elephantasia.dialogs.LocationDialog;
import fr.elephantasia.fragment.DatePickerFragment;
import fr.elephantasia.utils.KeyboardHelpers;

import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_BIRTH_LOCATION;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_CURRENT_LOCATION;

public class ProfilFragment extends Fragment {

  // View binding
  @BindView(R.id.birthLocation) EditText birthLocation;
  @BindView(R.id.currentLocation) EditText currentLocation;
  @BindView(R.id.name) EditText name;   // Mandatory fields
  @BindView(R.id.male) RadioButton male; // Mandatory fields
  @BindView(R.id.female) RadioButton female; // Mandatory fields

  // Attr
  private Elephant elephant;

  // Listener binding
  @OnClick({R.id.male, R.id.female})
  public void removeSexError() {
    male.setError(null);
    female.setError(null);
  }

  @OnClick(R.id.birthDate)
  public void showDatePicker(final EditText editText) {
    DatePickerFragment dialog = new DatePickerFragment();
    dialog.setListener(new DatePickerFragment.Listener() {
      @Override
      public void onDateSet(int year, int month, int dayOfMonth) {
        editText.setText(getString(R.string.date, year, month, dayOfMonth));
      }
    });
    dialog.show(getActivity().getSupportFragmentManager(), "Date");
  }

  @OnClick({R.id.birthLocation, R.id.currentLocation})
  public void showLocationDialog(EditText editText) {
    String currentTitle = getString(R.string.set_current_location);
    String birthTitle = getString(R.string.set_birth_location);

    LocationDialog locationDialog = new LocationDialog(getActivity(),
        editText.getId() == R.id.birthLocation ? elephant.birthLoc : elephant.currentLoc,
        editText.getId() == R.id.birthLocation ? birthTitle : currentTitle,
        editText.getId() == R.id.birthLocation ? REQUEST_BIRTH_LOCATION : REQUEST_CURRENT_LOCATION,
        editText
    );
    locationDialog.show();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    AddElephantProfilFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_profil_fragment, container, false);
    elephant = ((AddElephantActivity) getActivity()).getElephant();
    binding.setE(elephant);
    View view = binding.getRoot();
    ButterKnife.bind(this, view);
    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }

  /**
   * Used by AddElephantActivity to set error
   */
  public void setNameError() {
    name.setError(getText(R.string.name_required));
  }

  /**
   * Used by AddElephantActivity to set error
   */
  public void setSexError() {
    male.setError(getText(R.string.sex_required));
    female.setError(getText(R.string.sex_required));
  }

  /**
   * Set current location from map
   * TODO: Faire mieux la difference entre une location exacte et une
   *
   * @param location the location returned from the map picker
   */
  public void setCurrentLocation(Intent data) {
    final Place place = PlacePicker.getPlace(getActivity(), data);
    Geocoder geocoder = new Geocoder(getActivity());

    try {
      List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
      elephant.currentLoc.cityName = addresses.get(0).getAddressLine(0);
      if (!addresses.get(0).getAddressLine(0).equals(addresses.get(0).getSubAdminArea())) {
        elephant.currentLoc.districtName = addresses.get(0).getSubAdminArea();
      }
      elephant.currentLoc.provinceName = addresses.get(0).getAdminArea();
    } catch (IOException e) {
      e.printStackTrace();
    }

    currentLocation.setText(elephant.currentLoc.format());
  }

  //TODO: Extraire la province, le district et la ville du place picker

  /**
   * Set birth location from map
   *
   * @param location the location returned from the map picker
   */
  public void setBirthLocation(Intent data) {
    final Place place = PlacePicker.getPlace(getActivity(), data);
    Geocoder geocoder = new Geocoder(getActivity());

    try {
      List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1);
      elephant.birthLoc.cityName = addresses.get(0).getAddressLine(0);
      if (!addresses.get(0).getAddressLine(0).equals(addresses.get(0).getSubAdminArea())) {
        elephant.birthLoc.districtName = addresses.get(0).getSubAdminArea();
      }
      elephant.birthLoc.provinceName = addresses.get(0).getAdminArea();
    } catch (IOException e) {
      e.printStackTrace();
    }

    birthLocation.setText(elephant.birthLoc.format());
  }
}
