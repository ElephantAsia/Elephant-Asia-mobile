package fr.elephantasia.activities.addElephant.fragment;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.SearchElephantActivity;
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.adapter.ListElephantPreviewAdapter;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.RealmManager;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.AddElephantParentageFragmentBinding;
import fr.elephantasia.utils.KeyboardHelpers;
import io.realm.Realm;

import static butterknife.ButterKnife.findById;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_CHILD_SELECTED;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_FATHER_SELECTED;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_MOTHER_SELECTED;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.SELECT_ELEPHANT;
import static fr.elephantasia.database.model.Elephant.ID;

public class ParentageFragment extends Fragment {

  // View binding
  @BindView(R.id.mother_add_button) ImageView motherButton;
  @BindView(R.id.mother_preview) ElephantPreview motherPreview;
  @BindView(R.id.father_add_button) ImageView fatherButton;
  @BindView(R.id.father_preview) ElephantPreview fatherPreview;
  @BindView(R.id.list) ListView childrenList;

  // Attr
  private Elephant elephant;
  private AddElephantParentageFragmentBinding binding;
  private ListElephantPreviewAdapter adapter;
  private RealmManager realmManager = RealmManager.getInstance();

  // Listener binding
  @OnClick(R.id.mother_add_button)
  public void searchMother() {
    Intent intent = new Intent(getActivity(), SearchElephantActivity.class);
    intent.setAction(SELECT_ELEPHANT);
    getActivity().startActivityForResult(intent, REQUEST_MOTHER_SELECTED);
  }

  @OnClick(R.id.father_add_button)
  public void searchFather() {
    Intent intent = new Intent(getActivity(), SearchElephantActivity.class);
    intent.setAction(SELECT_ELEPHANT);
    getActivity().startActivityForResult(intent, REQUEST_FATHER_SELECTED);
  }


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    elephant = ((AddElephantActivity) getActivity()).getElephant();
    adapter = new ListElephantPreviewAdapter(getContext(), elephant.children, true, false);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_parentage_fragment, container, false);
    binding.setE(elephant);
    View view = binding.getRoot();
    ButterKnife.bind(this, view);
    motherPreview.setRemoveListener(unsetMother());
    fatherPreview.setRemoveListener(unsetFather());
    setupChildrenList(inflater);
    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }

  private void setupChildrenList(LayoutInflater inflater) {
    View headerView = inflater.inflate(R.layout.add_button_footer_list, childrenList, false);
    TextView addChildButton = findById(headerView, R.id.add_button_footer);
    addChildButton.setHint(getString(R.string.add_child));
    addChildButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), SearchElephantActivity.class);
        intent.setAction(SELECT_ELEPHANT);
        getActivity().startActivityForResult(intent, REQUEST_CHILD_SELECTED);
      }
    });
    childrenList.addHeaderView(headerView);
    childrenList.setAdapter(adapter);
  }

  public void setMother(final String id) {
    Realm realm = Realm.getDefaultInstance();
    elephant.mother = realm.where(Elephant.class).equalTo(ID, id).findFirst();
    elephant.mother = realm.copyFromRealm(elephant.mother);
    motherButton.setVisibility(View.GONE);
    motherPreview.setElephant(elephant.mother);
    motherPreview.setVisibility(View.VISIBLE);
    realm.close();
  }

  public void setFather(String id) {
    Realm realm = Realm.getDefaultInstance();
    elephant.father = realm.where(Elephant.class).equalTo(ID, id).findFirst();
    elephant.father = realm.copyFromRealm(elephant.father);
    fatherButton.setVisibility(View.GONE);
    fatherPreview.setElephant(elephant.father);
    fatherPreview.setVisibility(View.VISIBLE);
    realm.close();
  }

  public void setChild(String id) {
    Realm realm = Realm.getDefaultInstance();
    Elephant child = realm.where(Elephant.class).equalTo(ID, id).findFirst();
    adapter.add(realm.copyFromRealm(child));
    realm.close();
  }

  private View.OnClickListener unsetMother() {
    return new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        elephant.mother = null;
        motherPreview.setElephant(null);
        motherPreview.setVisibility(View.GONE);
        motherButton.setVisibility(View.VISIBLE);
      }
    };
  }

  private View.OnClickListener unsetFather() {
    return new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        elephant.father = null;
        fatherPreview.setElephant(null);
        fatherPreview.setVisibility(View.GONE);
        fatherButton.setVisibility(View.VISIBLE);
      }
    };
  }
}