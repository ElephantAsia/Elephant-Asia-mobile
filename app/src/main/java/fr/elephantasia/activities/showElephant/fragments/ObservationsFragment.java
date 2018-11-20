package fr.elephantasia.activities.showElephant.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.adapter.ObservationsAdapter;
import fr.elephantasia.database.DatabaseController.SortOrder;
import fr.elephantasia.database.model.ElephantNote;

public class ObservationsFragment extends Fragment {

  @BindView(R.id.list) ListView list;
  @BindView(R.id.no_item) View view;

  private ObservationsAdapter adapter;
  private SortOrder dateOrder = SortOrder.Descending;
  private SortOrder priorityOrder = SortOrder.None;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    List<ElephantNote> notes = ((ShowElephantActivity) getActivity()).getElephantNotes();
    adapter = new ObservationsAdapter(getContext(), notes);
  }

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.elephant_observations_fragment, container, false);

    ButterKnife.bind(this, view);
    list.setAdapter(adapter);
    refresh();
    return (view);
  }

  public void sort(SortOrder dateOrder, SortOrder priorityOrder) {
    this.dateOrder = dateOrder;
    this.priorityOrder = priorityOrder;
    refresh();
  }

  public void refresh() {
    List<ElephantNote> notes = ((ShowElephantActivity) getActivity()).getElephantNotes();

    if (notes.size() == 0) {
      view.setVisibility(View.VISIBLE);
      list.setVisibility(View.GONE);
    } else {
      view.setVisibility(View.GONE);
      list.setVisibility(View.VISIBLE);
    }
    adapter.clear();
    adapter.addAll(notes);
  }

  public SortOrder getDateOrder() {
    return dateOrder;
  }

  public SortOrder getPriorityOrder() {
    return priorityOrder;
  }

}
