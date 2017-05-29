package fr.elephantasia.activities.showElephant.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.adapter.ListElephantPreviewAdapter;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ShowParentageFragmentBinding;
import fr.elephantasia.utils.ViewHelpers;

public class ShowParentageFragment extends Fragment {

  // View binding
  @BindView(R.id.list) ListView children;

  // Attr
  private ListElephantPreviewAdapter adapter;
  private Elephant elephant;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    elephant = ((ShowElephantActivity) getActivity()).getElephant();
    adapter = new ListElephantPreviewAdapter(getContext(), elephant.children, false, false);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ShowParentageFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_parentage_fragment, container, false);
    View view = binding.getRoot();
    binding.setE(elephant);
    ButterKnife.bind(this, view);
    children.setAdapter(adapter);
    ViewHelpers.extendListView(children);
    return (view);
  }


}
