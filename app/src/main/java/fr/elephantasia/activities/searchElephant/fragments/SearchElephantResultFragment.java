package fr.elephantasia.activities.searchElephant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
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
import fr.elephantasia.activities.searchElephant.adapters.ElephantsAdapter;

public class SearchElephantResultFragment extends Fragment {

  @BindView(R.id.no_result) TextView noResult;
  @BindView(R.id.result_view) RecyclerView resultList;

  private boolean viewInitialized = false;
  private boolean empty = true;
  private ElephantsAdapter adapter;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    View view = inflater.inflate(R.layout.search_elephant_result_fragment, container, false);
    ButterKnife.bind(this, view);
    viewInitialized = true;
    init();

    return view;
  }

  public void setAdapter(ElephantsAdapter adapter) {
    this.adapter = adapter;
    if (viewInitialized && adapter != null) {
      resultList.setAdapter(adapter);
    }
  }

  public void contentUpdated(boolean empty) {
    this.empty = empty;
    if (viewInitialized) {
      if (!empty) {
        noResult.setVisibility(View.GONE);
        resultList.setVisibility(View.VISIBLE);
      } else {
        noResult.setVisibility(View.VISIBLE);
        resultList.setVisibility(View.GONE);
      }
    }
  }

  private void init() {
    resultList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

    setAdapter(this.adapter);
    contentUpdated(this.empty);
  }

}
