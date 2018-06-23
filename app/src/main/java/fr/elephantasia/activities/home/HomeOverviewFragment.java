package fr.elephantasia.activities.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.elephantasia.R;
import fr.elephantasia.activities.searchElephant.SearchElephantResultActivity;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

import static fr.elephantasia.activities.searchElephant.SearchElephantResultActivity.SEARCH_ALL;
import static fr.elephantasia.activities.searchElephant.SearchElephantResultActivity.SEARCH_DRAFT;
import static fr.elephantasia.activities.searchElephant.SearchElephantResultActivity.SEARCH_PENDING;
import static fr.elephantasia.activities.searchElephant.SearchElephantResultActivity.SEARCH_SAVED;
import static fr.elephantasia.database.model.Elephant.DB_STATE;
import static fr.elephantasia.database.model.Elephant.DRAFT;
import static fr.elephantasia.database.model.Elephant.SYNC_STATE;

public class HomeOverviewFragment extends Fragment {

  // View binding
  @BindView(R.id.total_value) TextView totalValue;
  @BindView(R.id.pending_value) TextView pendingValue;
  @BindView(R.id.draft_value) TextView draftValue;
  @BindView(R.id.saved_value) TextView savedValue;

  // Listeners Binding
  @OnClick(R.id.total_card)
  public void searchElephantAll() {
    Intent intent = new Intent(getContext(), SearchElephantResultActivity.class);
    intent.setAction(SEARCH_ALL);
    startActivity(intent);
  }

  @OnClick(R.id.pending_card)
  public void searchElephantPending() {
    Intent intent = new Intent(getContext(), SearchElephantResultActivity.class);
    intent.setAction(SEARCH_PENDING);
    startActivity(intent);
  }

  @OnClick(R.id.draft_card)
  public void searchElephantDraft() {
    Intent intent = new Intent(getContext(), SearchElephantResultActivity.class);
    intent.setAction(SEARCH_DRAFT);
    startActivity(intent);
  }

  @OnClick(R.id.saved_card)
  public void searchElephantSaved() {
    Intent intent = new Intent(getContext(), SearchElephantResultActivity.class);
    intent.setAction(SEARCH_SAVED);
    startActivity(intent);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.home_overview_fragment, container, false);
    ButterKnife.bind(this, view);
    initIcon();
    setOverviewData();
    return (view);
  }

  private void initIcon() {

    totalValue.setCompoundDrawables(new IconicsDrawable(getContext()).
        icon(MaterialDesignIconic.Icon.gmi_collection_text)
        .color(ContextCompat.getColor(getContext(), R.color.md_indigo)).sizeDp(28), null, null, null);

    pendingValue.setCompoundDrawables(new IconicsDrawable(getContext()).
        icon(MaterialDesignIconic.Icon.gmi_refresh_sync)
        .color(ContextCompat.getColor(getContext(), R.color.md_light_blue)).sizeDp(28), null, null, null);

    draftValue.setCompoundDrawables(new IconicsDrawable(getContext()).
        icon(MaterialDesignIconic.Icon.gmi_archive)
        .color(ContextCompat.getColor(getContext(), R.color.md_teal)).sizeDp(28), null, null, null);

    savedValue.setCompoundDrawables(new IconicsDrawable(getContext()).
        icon(MaterialDesignIconic.Icon.gmi_check)
        .color(ContextCompat.getColor(getContext(), R.color.md_green)).sizeDp(28), null, null, null);
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
        .equalTo(SYNC_STATE, Elephant.SyncState.Pending.name())
        .findAll().size();

    int readyToSync = realm.where(Elephant.class)
        .isNotNull(DB_STATE)
        .equalTo(DRAFT, false)
        .findAll().size();

    int draft = realm.where(Elephant.class).equalTo(DRAFT, true).findAll().size();

    totalValue.setText(String.valueOf(total));
    pendingValue.setText(String.valueOf(pending));
    savedValue.setText(String.valueOf(readyToSync));
    draftValue.setText(String.valueOf(draft));
  }
}