package fr.elephantasia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import fr.elephantasia.R;
import fr.elephantasia.database.model.ElephantNote;

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
      refreshPriority(convertView, note);
      refreshCategory(convertView, note);
      refreshDescription(convertView, note);
    }
    return convertView;
  }

  private void refreshPriority(View v, @NonNull ElephantNote note) {
    TextView textView = v.findViewById(R.id.priority);

    if (note.getPriority().equals(ElephantNote.Priority.Low.name())) {
      textView.setTextColor(getContext().getResources().getColor(R.color.md_green));
    } else if (note.getPriority().equals(ElephantNote.Priority.Medium.name())) {
      textView.setTextColor(getContext().getResources().getColor(R.color.md_orange));
    } else if (note.getPriority().equals(ElephantNote.Priority.High.name())) {
      textView.setTextColor(getContext().getResources().getColor(R.color.md_red));
    }
    textView.setText(note.getPriority());
  }

  private void refreshCategory(View v, @NonNull ElephantNote note) {
    TextView textView = v.findViewById(R.id.category);

    textView.setText(note.getCategory());
  }

  private void refreshDescription(View v, @NonNull ElephantNote note) {
    TextView textView = v.findViewById(R.id.description);

    textView.setText(note.getDescription());
  }

}
