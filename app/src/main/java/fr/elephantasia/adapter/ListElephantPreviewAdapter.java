package fr.elephantasia.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.databinding.ElephantPreviewBinding;
import fr.elephantasia.database.model.Elephant;
import io.realm.RealmList;


public class ListElephantPreviewAdapter extends ArrayAdapter<Elephant> {

  // Attr
  private Elephant elephant;

  // Views Binding
  @BindView(R.id.profil) TextView profil;
  @BindView(R.id.chip1) TextView chip1;
  @BindView(R.id.location) TextView location;
  @BindView(R.id.state_local) TextView stateLocal;

  public ListElephantPreviewAdapter(Context context, RealmList<Elephant> elephants) {
    super(context, R.layout.elephant_preview , elephants);
  }

  public View getView(int position, View view, ViewGroup parent) {
    ElephantPreviewBinding binding;

    if(view == null) {
      binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.elephant_preview, parent, false);
      view = binding.getRoot();
    } else {
      binding = (ElephantPreviewBinding) view.getTag();
    }

    ButterKnife.bind(this, view);

    elephant = this.getItem(position);
    profil.setText(formatProfil());
    chip1.setText(formatChip());
    location.setText(elephant.currentLoc.format());

    if (elephant.state.local || elephant.state.draft) {
      stateLocal.setVisibility(View.VISIBLE);
    }

    binding.setE(this.getItem(position));
    view.setTag(binding);
    return view;
  }

  private String formatProfil() {
    String res = elephant.name + ", " + elephant.getSex() + ", ";

    if (!TextUtils.isEmpty(elephant.birthDate)) {
      res += elephant.getAge() + " y/o";
    } else {
      res += "Age N/A";
    }
    return res;
  }

  private String formatChip() {
    String res;

    if (!TextUtils.isEmpty(elephant.chips1)) {
      res = "#" + elephant.chips1;
    } else {
      res = "N/A";
    }
    return res;
  }
}


