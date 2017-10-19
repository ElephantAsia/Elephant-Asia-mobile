package fr.elephantasia.activities.addElephant.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.searchElephant.SearchElephantActivity;
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.customView.ElephantPreview;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.AddElephantParentageFragmentBinding;
import fr.elephantasia.utils.KeyboardHelpers;
import io.realm.Realm;

import static butterknife.ButterKnife.findById;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_CHILD_SELECTED;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_FATHER_SELECTED;
import static fr.elephantasia.activities.addElephant.AddElephantActivity.REQUEST_MOTHER_SELECTED;
import static fr.elephantasia.database.model.Elephant.ID;

public class AddParentageFragment extends Fragment {

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
    elephant = ((AddElephantActivity) getActivity()).getElephant();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    AddElephantParentageFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_elephant_parentage_fragment, container, false);
    binding.setE(elephant);

    View view = binding.getRoot();
    ButterKnife.bind(this, view);

    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    return (view);
  }

  public void setMother(final int id) {
    Realm realm = ((AddElephantActivity) getActivity()).getRealm();
    elephant.mother = realm.where(Elephant.class).equalTo(ID, id).findFirst();
    elephant.mother = realm.copyFromRealm(elephant.mother);

    motherPreview.setElephant(elephant.mother);
    motherPreview.setPreviewContent();
    motherPreview.setActionListener(unsetMother(), getString(R.string.unselect));

    motherButton.setVisibility(View.GONE);
    motherPreview.setVisibility(View.VISIBLE);
  }

  public void setFather(final int id) {
    Realm realm = ((AddElephantActivity) getActivity()).getRealm();
    elephant.father = realm.where(Elephant.class).equalTo(ID, id).findFirst();
    elephant.father = realm.copyFromRealm(elephant.father);

    fatherPreview.setElephant(elephant.father);
    fatherPreview.setPreviewContent();
    motherPreview.setActionListener(unsetFather(), getString(R.string.unselect));

    fatherButton.setVisibility(View.GONE);
    fatherPreview.setVisibility(View.VISIBLE);
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