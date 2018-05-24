package fr.elephantasia.activities.manageElephant.fragment;

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
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.manageElephant.dialog.DatePickerDialog;
import fr.elephantasia.activities.manageElephant.dialog.LocationInputDialog;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ManageElephantProfilFragmentBinding;
import fr.elephantasia.utils.KeyboardHelpers;

public class ProfilFragment extends Fragment {

  // View binding
  @BindView(R.id.birthLocation) EditText birthLocation;
  @BindView(R.id.name) EditText name;   // Mandatory fields
  @BindView(R.id.male) RadioButton male; // Mandatory fields
  @BindView(R.id.female) RadioButton female; // Mandatory fields

  // Attr
  private Elephant elephant;

  // Listener binding
  @OnClick({R.id.male, R.id.female})
  public void removeSexError(RadioButton radioButton) {
    switch (radioButton.getId()) {
      case R.id.male:
        elephant.sex = "M";
        break;
      case R.id.female:
        elephant.sex = "F";
        break;
    }
    male.setError(null);
    female.setError(null);
  }

  @OnClick(R.id.birthDate)
  public void showDatePicker(final EditText editText) {
    DatePickerDialog dialog = new DatePickerDialog();
    dialog.setListener(new DatePickerDialog.Listener() {
      @Override
      public void onDateSet(int year, int month, int dayOfMonth) {
        editText.setText(getString(R.string.date, year, month, dayOfMonth));
      }
    });
    dialog.show(getActivity().getSupportFragmentManager(), "Date");
  }

  @OnClick(R.id.birthLocation)
  public void showLocationDialog(EditText editText) {
    LocationInputDialog locationDialog = new LocationInputDialog(
        getActivity(),
        elephant.birthLoc,
        getString(R.string.set_birth_location),
        editText
    );
    locationDialog.show();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ManageElephantProfilFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.manage_elephant_profil_fragment, container, false);
    elephant = ((ManageElephantActivity) getActivity()).getElephant();
    binding.setE(elephant);
    View view = binding.getRoot();
    ButterKnife.bind(this, view);

    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    refresh();
    return (view);
  }

  public void setNameError() {
    name.setError(getText(R.string.name_required));
  }

  public void setSexError() {
    male.setError(getText(R.string.sex_required));
    female.setError(getText(R.string.sex_required));
  }

  private void refresh() {
    refreshSex();
  }

  private void refreshSex() {
    if (elephant.sex.equals("M")) {
      male.toggle();
    } else {
      female.toggle();
    }
  }
}
