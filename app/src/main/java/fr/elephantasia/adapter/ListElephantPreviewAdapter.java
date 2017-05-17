package fr.elephantasia.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.Toolbar;
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
import fr.elephantasia.realm.model.Elephant;
import io.realm.RealmList;


public class ListElephantPreviewAdapter extends ArrayAdapter<Elephant> {

  private RealmList<Elephant> elephants;

  // Views Binding
  @BindView(R.id.profil) TextView profil;

  public ListElephantPreviewAdapter(Context context, RealmList<Elephant> elephants) {
    super(context, R.layout.elephant_preview , elephants);
    this.elephants = elephants;
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

    profil.setText(formatProfil(position));

    binding.setE(this.getItem(position));
    view.setTag(binding);
    return view;
  }

  private String formatProfil(int pos) {
    Elephant e = this.getItem(pos);

    String res = e.name + ", " + e.getSex();

    if (!TextUtils.isEmpty(e.birthDate)) {
      res += ", " + e.getAge() + " y/o";
    }
    return res;
  }
}


