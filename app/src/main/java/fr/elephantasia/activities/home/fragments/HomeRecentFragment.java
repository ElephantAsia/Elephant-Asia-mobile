package fr.elephantasia.activities.home.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.home.HomeActivity;
import fr.elephantasia.activities.home.adapters.RecentElephantsAdapter;
import fr.elephantasia.database.model.Elephant;

public class HomeRecentFragment extends Fragment {

  @BindView(R.id.recent_list) RecyclerView recentList;
  @BindView(R.id.no_recent_items) TextView noItemsYet;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View view = inflater.inflate(R.layout.home_recent_fragment, container, false);
    ButterKnife.bind(this, view);

    recentList.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
    return (view);
  }

  @Override
  public void onResume() {
    super.onResume();
    setLastVisitedElephant();
  }

  public void setLastVisitedElephant() {
    HomeActivity homeActivity = (HomeActivity)getActivity();

    if (homeActivity != null) {
      List<Elephant> elephants = homeActivity.getLastVisitedElephants();

      if (elephants.size() == 0) {
        noItemsYet.setVisibility(View.VISIBLE);
      } else {
        noItemsYet.setVisibility(View.GONE);
        recentList.setAdapter(new RecentElephantsAdapter(elephants, new RecentElephantsAdapter.Listener() {
          @Override
          public void onClick(Elephant e) {
            ((HomeActivity)getActivity()).showElephant(e.id);
          }
        }));
      }
    }

  }
}