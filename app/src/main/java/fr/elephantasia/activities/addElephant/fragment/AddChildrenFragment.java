package fr.elephantasia.activities.addElephant.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
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
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.activities.searchElephant.SearchElephantActivity;
import fr.elephantasia.adapter.ChildrenListAdapter;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.AddElephantChildrenFragmentBinding;
import fr.elephantasia.utils.KeyboardHelpers;
import io.realm.Realm;

import static butterknife.ButterKnife.findById;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_CHILD_SELECTED;
import static fr.elephantasia.database.model.Elephant.ID;

public class AddChildrenFragment extends Fragment {

  // View binding
  @BindView(R.id.list) ListView childrenList;

  // Attr
  private Elephant elephant;
  private ChildrenListAdapter adapter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    elephant = ((AddElephantActivity) getActivity()).getElephant();
    adapter = new ChildrenListAdapter(getContext(), elephant.children);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    AddElephantChildrenFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_children_fragment, container, false);
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
        intent.setAction(ElephantPreview.SELECT);
        getActivity().startActivityForResult(intent, REQUEST_CHILD_SELECTED);
      }
    });

    childrenList.addHeaderView(headerView);
    childrenList.setAdapter(adapter);
  }

  public void setChild(final int id) {
    Realm realm = ((AddElephantActivity) getActivity()).getRealm();
    Elephant child = realm.where(Elephant.class).equalTo(ID, id).findFirst();
    adapter.add(realm.copyFromRealm(child));
  }

}