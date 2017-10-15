package fr.elephantasia.activities.editElephant.fragment;


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
import fr.elephantasia.activities.editElephant.EditElephantActivity;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.AddElephantProfilFragmentBinding;
import fr.elephantasia.dialogs.LocationInputDialog;
import fr.elephantasia.dialogs.DatePickerDialog;
import fr.elephantasia.utils.KeyboardHelpers;

public class EditProfilFragment extends Fragment {

  // View binding
  @BindView(R.id.birthLocation) EditText birthLocation;
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
    AddElephantProfilFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_profil_fragment, container, false);
    elephant = ((EditElephantActivity) getActivity()).getElephant();
    binding.setE(elephant);
    View view = binding.getRoot();
    ButterKnife.bind(this, view);
    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }

  /**
   * Used by EditElephantActivity to set error
   */
  public void setNameError() {
    name.setError(getText(R.string.name_required));
  }

  /**
   * Used by EditElephantActivity to set error
   */
  public void setSexError() {
    male.setError(getText(R.string.sex_required));
    female.setError(getText(R.string.sex_required));
  }
}
