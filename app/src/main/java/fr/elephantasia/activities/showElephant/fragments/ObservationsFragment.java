package fr.elephantasia.activities.showElephant.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.adapter.ObservationsAdapter;
import fr.elephantasia.database.model.Elephant;

public class ObservationsFragment extends Fragment {

  @BindView(R.id.list) ListView list;
  @BindView(R.id.no_item) View view;

  private ObservationsAdapter adapter;
  private Elephant elephant;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    elephant = ((ShowElephantActivity) getActivity()).getElephant();
    adapter = new ObservationsAdapter(getContext(), elephant.notes);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.elephant_observations_fragment, container, false);

    ButterKnife.bind(this, view);
    list.setAdapter(adapter);
    refresh();
    return (view);
  }

  public void refresh() {
    if (elephant.notes.size() == 0) {
      view.setVisibility(View.VISIBLE);
      list.setVisibility(View.GONE);
    } else {
      view.setVisibility(View.GONE);
      list.setVisibility(View.VISIBLE);
    }
  }

}
