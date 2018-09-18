package fr.elephantasia.activities.searchElephant.fragments;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;
import fr.elephantasia.databinding.SearchElephantFragmentBinding;

public class SearchElephantFragment extends Fragment {

  @BindView(R.id.name) EditText nameEditText;
  @BindView(R.id.male_only) CheckBox maleCb;
  @BindView(R.id.female_only) CheckBox femaleCb;
  @BindView(R.id.microchip) EditText microchipEditText;
  @BindView(R.id.mte) EditText mteEditText;
  @BindView(R.id.registration_province) EditText provinceEditText;
  @BindView(R.id.registration_district) EditText districtEditText;
  @BindView(R.id.registration_city) EditText cityEditText;

  private SearchElephantFragmentBinding binding;
  private Elephant elephant = new Elephant();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.inflate(inflater, R.layout.search_elephant_fragment, container, false);
    binding.setE(elephant);

    View view = binding.getRoot();
    ButterKnife.bind(this, view);
    return view;
  }

  @OnClick(R.id.male_only)
  void setMaleOnly() {
    elephant.sex = maleCb.isChecked() ? "M" : null;

    if (femaleCb.isChecked()) {
      femaleCb.toggle();
    }
  }

  @OnClick(R.id.female_only)
  void setFemaleOnly() {
    elephant.sex = femaleCb.isChecked() ? "F" : null;

    if (maleCb.isChecked()) {
      maleCb.toggle();
    }
  }

  public void reset() {
    femaleCb.setChecked(false);
    maleCb.setChecked(false);

    elephant = new Elephant();
    binding.setE(elephant);
  }

  public boolean isFieldsValid() {
    return nameEditText.length() + microchipEditText.length() + mteEditText.length()
      + provinceEditText.length() + districtEditText.length() + cityEditText.length()
      + ((femaleCb.isChecked())?1:0) + ((maleCb.isChecked())?1:0) > 0;
  }

  public Elephant getElephant() {
    return elephant;
  }

}
