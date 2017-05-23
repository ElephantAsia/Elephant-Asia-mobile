package fr.elephantasia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.elephantasia.R;
import fr.elephantasia.refactor.interfaces.HomePageAdapterInterface;

/**
 * Created by Stephane on 15/03/2017.
 */

public class HomePageAdapter extends BaseAdapter {

  private Context context;
  private HomePageAdapterInterface listener;

  private List<String> titles;

  public HomePageAdapter(Context context, HomePageAdapterInterface listener) {
    this.context = context;
    this.listener = listener;

    this.titles = new ArrayList<>();

    this.titles = Arrays.asList("Drafts", "Pending Elephant");
  }

  private String getTitle(int index) {
    return titles.get(index);
  }

  @Override
  public int getCount() {
    return (titles.size());
  }

  @Override
  public Integer getItem(int index) {
    return (index);
  }

  @Override
  public long getItemId(int index) {
    return index;
  }

  @Override
  public View getView(final int index, View old, ViewGroup parent) {
    boolean creation = (old == null);
    View view;

    if (!creation) {
      view = old;
    } else {
      LayoutInflater inflater = LayoutInflater.from(context);
      view = inflater.inflate(R.layout.home_page_panel_preview, parent, false);
    }

    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //listener.onNewActivity(getIntent(index), getResultCode(index));
      }
    });

    TextView title = (TextView) view.findViewById(R.id.title);
    title.setText(getTitle(index));

    // ...

    return view;
  }
}
