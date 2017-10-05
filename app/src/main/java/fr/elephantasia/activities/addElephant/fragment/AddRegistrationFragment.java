package fr.elephantasia.activities.addElephant.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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
import fr.elephantasia.databinding.AddElephantRegistrationFragmentBinding;
import fr.elephantasia.dialogs.LocationInputDialog;
import fr.elephantasia.utils.KeyboardHelpers;

public class AddRegistrationFragment extends Fragment {

  // View binding
  @BindView(R.id.registration_location) EditText registrationLocation;
  @BindView(R.id.mte_input) TextInputLayout mteInput;

  // Attr
  private Elephant elephant;


  // Listener
  @OnClick(R.id.mte_checkbox)
  void displayMteInput() {
    if (elephant.mteOwner) {
      mteInput.setVisibility(View.VISIBLE);
    } else {
      mteInput.setVisibility(View.GONE);
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
    AddElephantRegistrationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_registration_fragment, container, false);
    elephant = ((AddElephantActivity) getActivity()).getElephant();
    View view = binding.getRoot();
    binding.setE(elephant);
    ButterKnife.bind(this, view);
    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }
}
