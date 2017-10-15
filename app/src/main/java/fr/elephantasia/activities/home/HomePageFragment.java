package fr.elephantasia.activities.home;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.adapter.ElephantRecentAdapter;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

import static fr.elephantasia.database.model.Elephant.LAST_VISITED;

public class HomePageFragment extends Fragment {

  // View binding
  @BindView(R.id.recent_list) RecyclerView recentList;
  @BindView(R.id.no_recent_items) TextView noItemsYet;

  // Attr
  private RecyclerView.Adapter mAdapter;
  private RecyclerView.LayoutManager mLayoutManager;

  private Realm realm;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.home_page_fragment, container, false);
    ButterKnife.bind(this, view);

//    recentList.setHasFixedSize(true);
    mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    recentList.setLayoutManager(mLayoutManager);

    setLastVisitedElephant();

    return (view);
  }

  @Override
  public void onResume() {
    super.onResume();
    setLastVisitedElephant();
  }

  public void setLastVisitedElephant() {
    realm = Realm.getDefaultInstance();
    RealmResults<Elephant> elephants = realm.where(Elephant.class).findAllSorted(LAST_VISITED, Sort.DESCENDING);
    RealmResults<Elephant> elephantsall = realm.where(Elephant.class).findAll();

    if (elephants.size() == 0) {
      noItemsYet.setVisibility(View.VISIBLE);
    } else {
      noItemsYet.setVisibility(View.GONE);
      mAdapter = new ElephantRecentAdapter(elephants);
      recentList.setAdapter(mAdapter);
    }
  }
}