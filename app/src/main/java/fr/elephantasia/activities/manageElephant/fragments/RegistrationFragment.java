package fr.elephantasia.activities.manageElephant.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.manageElephant.dialogs.LocationInputDialog;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ManageElephantRegistrationFragmentBinding;
import fr.elephantasia.utils.KeyboardHelpers;

public class RegistrationFragment extends Fragment {

  // View binding
  @BindView(R.id.registration_location) EditText registrationLocation;
  @BindView(R.id.mte_input_layout) TextInputLayout mteInputLayout;
  @BindView(R.id.mte_input) EditText mteInput;

  // Attr
  private Elephant elephant;

  // Listener
  @OnClick(R.id.mte_checkbox)
  void displayMteInput() {
    if (elephant.mteOwner) {
      mteInputLayout.setVisibility(View.VISIBLE);
    } else {
      mteInputLayout.setVisibility(View.GONE);
      mteInput.setText(null);
      elephant.mteNumber = null;
    }
  }

  @OnClick(R.id.registration_location)
  public void showLocationDialog(EditText editText) {
    LocationInputDialog locationDialog = new LocationInputDialog(
        getActivity(),
        elephant.registrationLoc,
        getString(R.string.set_registration_location),
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
    ManageElephantRegistrationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.manage_elephant_registration_fragment, container, false);
    elephant = ((ManageElephantActivity) getActivity()).getElephant();
    View view = binding.getRoot();
    binding.setE(elephant);
    ButterKnife.bind(this, view);
    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }
}
