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
import fr.elephantasia.adapter.RecentElephantAdapter;
import fr.elephantasia.database.model.Elephant;
import io.realm.RealmResults;

public class HomeRecentFragment extends Fragment {

  // View binding
  @BindView(R.id.recent_list) RecyclerView recentList;
  @BindView(R.id.no_recent_items) TextView noItemsYet;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.home_recent_fragment, container, false);
    ButterKnife.bind(this, view);

    recentList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

    setLastVisitedElephant();

    return (view);
  }

  @Override
  public void onResume() {
    super.onResume();
    setLastVisitedElephant();
  }

  public void setLastVisitedElephant() {
//    Realm realm = ((HomeActivity) getActivity()).getRealm();
//    RealmResults<Elephant> elephants = realm.where(Elephant.class)
//        .greaterThan(LAST_VISITED, DateHelpers.getLastWeek())
//        .findAllSorted(LAST_VISITED, Sort.DESCENDING);
    HomeActivity homeActivity = (HomeActivity)getActivity();

    if (homeActivity != null) {
      RealmResults<Elephant> elephants = homeActivity.getLastVisitedElephants();

      if (elephants.size() == 0) {
        noItemsYet.setVisibility(View.VISIBLE);
      } else {
        noItemsYet.setVisibility(View.GONE);
        recentList.setAdapter(new RecentElephantAdapter(elephants));
      }
    }

  }
}