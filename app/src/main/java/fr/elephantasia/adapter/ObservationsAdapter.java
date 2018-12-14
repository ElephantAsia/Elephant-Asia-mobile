package fr.elephantasia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.elephantasia.R;
import fr.elephantasia.database.model.ElephantNote;
import fr.elephantasia.utils.DateHelpers;

public class ObservationsAdapter extends ArrayAdapter<ElephantNote> {

  public ObservationsAdapter(@NonNull Context context, @NonNull List<ElephantNote> objects) {
    super(context, 0, objects);
  }

  @Override
  @NonNull
  public View getView(int position, View convertView, @NonNull ViewGroup parent) {
    ElephantNote note = getItem(position);

    if (convertView == null) {
      convertView = LayoutInflater.from(getContext()).inflate(R.layout.elephant_observation_item, parent, false);
    }

    if (note != null) {
      refreshCategory(convertView, note);
      refreshPriority(convertView, note);
      refreshDate(convertView, note);
      refreshDescription(convertView, note);
    }
    return convertView;
  }

  private void refreshCategory(View v, @NonNull ElephantNote note) {
    TextView textView = v.findViewById(R.id.category);

    textView.setText(note.getCategory());
  }

  private void refreshPriority(View v, @NonNull ElephantNote note) {
    TextView textView = v.findViewById(R.id.priority);

    if (note.getPriority().equals(ElephantNote.Priority.Low.getValue())) {
      textView.setTextColor(getContext().getResources().getColor(R.color.md_green));
    } else if (note.getPriority().equals(ElephantNote.Priority.Medium.getValue())) {
      textView.setTextColor(getContext().getResources().getColor(R.color.md_orange));
    } else if (note.getPriority().equals(ElephantNote.Priority.High.getValue())) {
      textView.setTextColor(getContext().getResources().getColor(R.color.md_red));
    }
    textView.setText(ElephantNote.Priority.valueOf(note.getPriority()).toString());
  }

  private void refreshDate(View v, @NonNull ElephantNote note) {
    TextView textView = v.findViewById(R.id.date);
    textView.setText(DateHelpers.FriendlyUserStringDate(note.getCreatedAt()));
  }

  private void refreshDescription(View v, @NonNull ElephantNote note) {
    TextView textView = v.findViewById(R.id.description);
    textView.setText(note.getDescription());
  }

}
