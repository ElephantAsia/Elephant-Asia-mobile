package fr.elephantasia.activities.addElephant.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.databinding.AddElephantProfilFragmentBinding;
import fr.elephantasia.dialogs.LocationDialog;
import fr.elephantasia.fragment.DatePickerFragment;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.utils.KeyboardHelpers;

public class ProfilFragment extends Fragment {

  private Elephant elephant;

  @BindView(R.id.birthLocation) EditText birthLocation;
  @BindView(R.id.currentLocation) EditText currentLocation;

  // Mandatory fields
  @BindView(R.id.name) EditText name;
  @BindView(R.id.male) RadioButton male;
  @BindView(R.id.female) RadioButton female;

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
        AddElephantActivity.REQUEST_CURRENT_LOCATION,
        editText
    );
    locationDialog.show();
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
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
   * TODO: Extraire la province, le district et la ville du place picker
   *
   * @param location the location returned from the map picker
   */
  public void setCurrentLocation(String location) {
    currentLocation.setText(location);
  }

  //TODO: Extraire la province, le district et la ville du place picker

  /**
   * Set birth location from map
   *
   * @param location the location returned from the map picker
   */
  public void setBirthLocation(String location) {
    birthLocation.setText(location);
  }
}
