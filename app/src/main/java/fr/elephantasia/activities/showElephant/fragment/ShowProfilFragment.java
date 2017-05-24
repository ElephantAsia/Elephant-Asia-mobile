package fr.elephantasia.activities.showElephant.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ShowDescriptionFragmentBinding;
import fr.elephantasia.databinding.ShowProfilFragmentBinding;

public class ShowProfilFragment extends Fragment {

  private Elephant elephant;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ShowProfilFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_profil_fragment, container, false);
    elephant = ((ShowElephantActivity) getActivity()).getElephant();
    View view = binding.getRoot();
    binding.setE(elephant);
    return (view);
  }
}
