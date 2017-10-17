package fr.elephantasia.activities.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

import static fr.elephantasia.database.model.Elephant.STATE;
import static fr.elephantasia.database.model.Elephant.StateValue;

public class HomeOverviewFragment extends Fragment {

  // View binding
  @BindView(R.id.total_icon) ImageView totalIcon;
  @BindView(R.id.total_value) TextView totalValue;
  @BindView(R.id.pending_icon) ImageView pendingIcon;
  @BindView(R.id.pending_value) TextView pendingValue;
  @BindView(R.id.draft_icon) ImageView draftIcon;
  @BindView(R.id.draft_value) TextView draftValue;
  @BindView(R.id.saved_icon) ImageView savedIcon;
  @BindView(R.id.saved_value) TextView savedValue;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.home_overview_fragment, container, false);
    ButterKnife.bind(this, view);
    initIcon();
    setOverviewData();
    return (view);
  }

  private void initIcon() {
    totalIcon.setImageDrawable(new IconicsDrawable(getContext()).
        icon(MaterialDesignIconic.Icon.gmi_collection_text)
        .color(ContextCompat.getColor(getContext(), R.color.md_indigo)).sizeDp(42)
    );

    pendingIcon.setImageDrawable(new IconicsDrawable(getContext()).
        icon(MaterialDesignIconic.Icon.gmi_refresh_sync)
        .color(ContextCompat.getColor(getContext(), R.color.md_light_blue)).sizeDp(42)
    );

    draftIcon.setImageDrawable(new IconicsDrawable(getContext()).
        icon(MaterialDesignIconic.Icon.gmi_archive)
        .color(ContextCompat.getColor(getContext(), R.color.md_teal)).sizeDp(42)
    );

    savedIcon.setImageDrawable(new IconicsDrawable(getContext()).
        icon(MaterialDesignIconic.Icon.gmi_check)
        .color(ContextCompat.getColor(getContext(), R.color.md_green)).sizeDp(42)
    );
  }


  @Override
  public void onResume() {
    super.onResume();
    setOverviewData();
  }

  public void setOverviewData() {
    Realm realm = ((HomeActivity) getActivity()).getRealm();
    int total = realm.where(Elephant.class).findAll().size();

    int pending = realm.where(Elephant.class)
        .equalTo(STATE, StateValue.pending.name())
        .or().equalTo(STATE, StateValue.rejected.name())
        .or().equalTo(STATE, StateValue.validated.name())
        .findAll().size();

    int saved = realm.where(Elephant.class)
        .equalTo(STATE, StateValue.saved.name())
        .or().equalTo(STATE, StateValue.deleted.name())
        .findAll().size();

    int draft = realm.where(Elephant.class).equalTo(STATE, StateValue.draft.name()).findAll().size();

    totalValue.setText(String.valueOf(total));
    pendingValue.setText(String.valueOf(pending));
    savedValue.setText(String.valueOf(saved));
    draftValue.setText(String.valueOf(draft));
  }
}