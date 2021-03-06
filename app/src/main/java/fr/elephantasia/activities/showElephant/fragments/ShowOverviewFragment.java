package fr.elephantasia.activities.showElephant.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.databinding.ShowOverviewFragmentBinding;

public class ShowOverviewFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ShowOverviewFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_overview_fragment, container, false);
    View view = binding.getRoot();
    binding.setE(((ShowElephantActivity) getActivity()).getElephant());
    return (view);
  }

}
