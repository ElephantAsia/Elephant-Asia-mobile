package fr.elephantasia.activities.showElephant.fragment;

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
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.AddElephantRegistrationFragmentBinding;
import fr.elephantasia.databinding.ShowRegistrationFragmentBinding;
import fr.elephantasia.dialogs.LocationDialog;
import fr.elephantasia.utils.KeyboardHelpers;

public class ShowRegistrationFragment extends Fragment {

  private Elephant elephant;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ShowRegistrationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_registration_fragment, container, false);
    elephant = ((ShowElephantActivity) getActivity()).getElephant();
    View view = binding.getRoot();
    binding.setE(elephant);
    return (view);
  }
}
