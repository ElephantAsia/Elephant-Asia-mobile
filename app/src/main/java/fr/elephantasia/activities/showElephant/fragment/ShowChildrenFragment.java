package fr.elephantasia.activities.showElephant.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.adapter.ChildrenListAdapter;
import fr.elephantasia.adapter.SearchElephantAdapter;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ShowChildrenFragmentBinding;
import fr.elephantasia.databinding.ShowParentageFragmentBinding;

public class ShowChildrenFragment extends Fragment {

  // View binding
  @BindView(R.id.list) ListView childrenList;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ShowChildrenFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_children_fragment, container, false);
    View view = binding.getRoot();

    Elephant elephant = ((ShowElephantActivity) getActivity()).getElephant();
    binding.setE(elephant);
    ButterKnife.bind(this, view);

    ChildrenListAdapter adapter = new ChildrenListAdapter(getContext(), elephant.children, true);
    childrenList.setAdapter(adapter);
    return (view);
  }
}
