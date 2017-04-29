package fr.elephantasia.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.R;


/**
 * Created by Stephane on 06/01/2017.
 */

public class MainActivityDrawerListAdapter extends BaseAdapter {

  private Context context;
  private int selection;
  private List<String> content;

  public MainActivityDrawerListAdapter(Context context) {
    this.context = context;
    this.content = new ArrayList<>();

    content.add(context.getString(R.string.home_page));
    content.add(context.getString(R.string.current_animal));
    content.add(context.getString(R.string.locations));
    content.add(context.getString(R.string.reports));
    content.add(context.getString(R.string.favorites));
    content.add(context.getString(R.string.settings));
    content.add(context.getString(R.string.officials));
    content.add(context.getString(R.string.support));
    content.add(context.getString(R.string.logout));
  }

  public void setSelection(int selection) {
    this.selection = selection;
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return (content.size());
  }


  @Override
  public String getItem(int index) {
    return (content.get(index));
  }

  @Override
  public long getItemId(int index) {
    return index;
  }

  @Override
  public View getView(int index, View old, ViewGroup parent) {
    View view;

    if (old != null) {
      view = old;
    } else {
      LayoutInflater inflater = LayoutInflater.from(context);
      view = inflater.inflate(R.layout.list_item_drawer, parent, false);
    }

    TextView label = (TextView) view.findViewById(R.id.listitem_drawer_label);
    //ImageView image = (ImageView) view.findViewById(R.id.listitem_drawer_image);
    label.setText(getItem(index));

    if (index == selection) {
      //image.setImageResource(R.drawable.menu_icon_home_enabled);
      //label.setTextColor(context.getResources().getColor(R.color.list_selection));
    } else {
      label.setTextColor(ContextCompat.getColor(context, R.color.list_indice));
    }
    return view;
  }

}
