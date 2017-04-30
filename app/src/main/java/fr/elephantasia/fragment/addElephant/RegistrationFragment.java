package fr.elephantasia.fragment.addElephant;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.AddElephantActivity;
import fr.elephantasia.databinding.AddElephantRegistrationFragmentBinding;
import fr.elephantasia.dialogs.LocationDialog;
import fr.elephantasia.realm.model.Elephant;
import fr.elephantasia.utils.KeyboardHelpers;

public class RegistrationFragment extends Fragment {

  private Elephant elephant;

  @BindView(R.id.registrationLocation) EditText registrationLocation;

  @OnClick(R.id.registrationLocation)
  public void showLocationDialog(EditText editText) {
    LocationDialog locationDialog = new LocationDialog(getActivity(),
        elephant.registrationLoc,
        getString(R.string.set_registration_location),
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
    AddElephantRegistrationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_registration_fragment, container, false);
    elephant = ((AddElephantActivity) getActivity()).getElephant();
    View view = binding.getRoot();
    binding.setE(elephant);
    ButterKnife.bind(this, view);
    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }

  //TODO: Extraire la province, le district et la ville du place picker
  public void setRegistrationLocation(String location) {
    registrationLocation.setText(location);
  }
}
