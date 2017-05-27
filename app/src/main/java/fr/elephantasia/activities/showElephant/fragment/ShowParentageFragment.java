package fr.elephantasia.activities.showElephant.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.databinding.ShowParentageFragmentBinding;

public class ShowParentageFragment extends Fragment {
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ShowParentageFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_parentage_fragment, container, false);
    View view = binding.getRoot();
    binding.setE(((ShowElephantActivity) getActivity()).getElephant());
    return (view);
  }
}
