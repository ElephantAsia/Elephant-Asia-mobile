package fr.elephantasia.activities.manageElephant.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.searchElephant.SearchElephantActivity;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.ManageElephantParentageFragmentBinding;
import fr.elephantasia.utils.KeyboardHelpers;

import static fr.elephantasia.activities.manageElephant.ManageElephantActivity.REQUEST_FATHER_SELECTED;
import static fr.elephantasia.activities.manageElephant.ManageElephantActivity.REQUEST_MOTHER_SELECTED;

public class ParentageFragment extends Fragment {

  // View binding
  @BindView(R.id.mother_add_button) ImageView motherButton;
  @BindView(R.id.father_add_button) ImageView fatherButton;
  @BindView(R.id.mother_preview) ElephantPreview motherPreview;
  @BindView(R.id.father_preview) ElephantPreview fatherPreview;

  // Attr
  private Elephant elephant;

  // Listener binding
  @OnClick(R.id.mother_add_button)
  public void searchMother() {
    Intent intent = new Intent(getActivity(), SearchElephantActivity.class);
    intent.setAction(ElephantPreview.SELECT);
    getActivity().startActivityForResult(intent, REQUEST_MOTHER_SELECTED);
  }

  @OnClick(R.id.father_add_button)
  public void searchFather() {
    Intent intent = new Intent(getActivity(), SearchElephantActivity.class);
    intent.setAction(ElephantPreview.SELECT);
    getActivity().startActivityForResult(intent, REQUEST_FATHER_SELECTED);
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    elephant = ((ManageElephantActivity) getActivity()).getElephant();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    ManageElephantParentageFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.manage_elephant_parentage_fragment, container, false);
    binding.setE(elephant);

    View view = binding.getRoot();
    ButterKnife.bind(this, view);

    displayMother();
    displayFather();

    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }

  public void setMother(@NonNull Elephant mother) {
//    Realm realm = ((ManageElephantActivity) getActivity()).getRealm();
//    elephant.mother = realm.where(Elephant.class).equalTo(ID, id).findFirst();
//    elephant.mother = realm.copyFromRealm(elephant.mother);
    elephant.mother = mother;
    displayMother();
  }

  public void setFather(Elephant father) {
//    Realm realm = ((ManageElephantActivity) getActivity()).getRealm();
//    elephant.father = realm.where(Elephant.class).equalTo(ID, id).findFirst();
//    elephant.father = realm.copyFromRealm(elephant.father);
    elephant.father = father;
    displayFather();
  }

  private void displayMother() {
    if (elephant.mother != null) {
      motherPreview.setElephant(elephant.mother);
      motherPreview.setPreviewContent();
      motherPreview.setActionListener(unsetMother(), getString(R.string.unselect));

      motherButton.setVisibility(View.GONE);
      motherPreview.setVisibility(View.VISIBLE);
    }
  }

  private void displayFather() {
    if (elephant.father != null) {
      fatherPreview.setElephant(elephant.father);
      fatherPreview.setPreviewContent();
      fatherPreview.setActionListener(unsetFather(), getString(R.string.unselect));

      fatherButton.setVisibility(View.GONE);
      fatherPreview.setVisibility(View.VISIBLE);
    }
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