package fr.elephantasia.activities.showElephant.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ShowParentageFragmentBinding;

public class ShowParentageFragment extends Fragment {

  // View binding
  @BindView(R.id.mother_preview) ElephantPreview mother;
  @BindView(R.id.father_preview) ElephantPreview father;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ShowParentageFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_parentage_fragment, container, false);
    View view = binding.getRoot();

    Elephant elephant = ((ShowElephantActivity) getActivity()).getElephant();
    binding.setE(elephant);
    ButterKnife.bind(this, view);

    mother.setElephant(elephant.mother);
    mother.setPreviewContent();
    mother.hideActionButton();

    father.setElephant(elephant.father);
    father.setPreviewContent();
    father.hideActionButton();
    return (view);
  }
}
