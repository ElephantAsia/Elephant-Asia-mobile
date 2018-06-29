package fr.elephantasia.activities.manageElephant.fragments;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.searchElephant.SearchElephantActivity;
import fr.elephantasia.adapter.ChildrenListAdapter;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ManageElephantChildrenFragmentBinding;
import fr.elephantasia.utils.KeyboardHelpers;

import static butterknife.ButterKnife.findById;
import static fr.elephantasia.activities.manageElephant.ManageElephantActivity.REQUEST_CHILD_SELECTED;

public class ChildrenFragment extends Fragment {

  // View binding
  @BindView(R.id.list) ListView childrenList;

  // Attr
  private Elephant elephant;
  private ChildrenListAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    elephant = ((ManageElephantActivity) getActivity()).getElephant();
    adapter = new ChildrenListAdapter(getContext(), elephant.children);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ManageElephantChildrenFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.manage_elephant_children_fragment, container, false);
    binding.setE(elephant);

    View view = binding.getRoot();
    ButterKnife.bind(this, view);

    setupChildrenList(inflater);

    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }

  private void setupChildrenList(LayoutInflater inflater) {

    View headerView = inflater.inflate(R.layout.add_button_footer_list, childrenList, false);
    TextView addChildButton = findById(headerView, R.id.add_button_footer);

    int color = ResourcesCompat.getColor(getResources(), R.color.primary_50, null);
    addChildButton.setCompoundDrawables(new IconicsDrawable(getActivity())
        .icon(MaterialDesignIconic.Icon.gmi_plus)
        .color(color).sizeDp(40), null, null, null);

    addChildButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchElephantActivity.class);
        // intent.setAction(ElephantPreview.SELECT);
        SearchElephantActivity.SetExtraAction(intent, ElephantPreview.SELECT);
        getActivity().startActivityForResult(intent, REQUEST_CHILD_SELECTED);
      }
    });

    childrenList.addHeaderView(headerView);
    childrenList.setAdapter(adapter);
  }

  public void addChild(@NonNull Elephant elephant) {
    adapter.add(elephant);
  }

}