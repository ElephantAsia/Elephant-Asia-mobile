package fr.elephantasia.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.R;
import fr.elephantasia.activities.AddContactActivity;
import fr.elephantasia.activities.manageElephant.ManageElephantActivity;
import fr.elephantasia.activities.searchElephant.SearchElephantActivity;
import fr.elephantasia.auth.AuthActivity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


/**
 * Created by Stephane on 06/01/2017.
 */

public class HomeDrawerListAdapter extends BaseAdapter {

  private int selection;
  private List<NavItem> navItems;
  private Context ctx;

  class NavItem {
    String title;
    IconicsDrawable icon;
    View.OnClickListener listener;

    public NavItem(String title, IconicsDrawable icon, View.OnClickListener listener) {
      this.title = title;
      this.icon = icon;
      this.listener = listener;
    }
  }

  public HomeDrawerListAdapter(final Context ctx) {
    this.navItems = new ArrayList<>();
    this.ctx = ctx;

    navItems.add(
        new NavItem(ctx.getString(R.string.favorites),
            new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_star)
                .color(ContextCompat.getColor(ctx, R.color.primary_light))
                .sizeDp(16),
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
//                Intent intent = new Intent(ctx, SearchElephantActivity.class);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(intent);
              }
            }));

    navItems.add(
        new NavItem(ctx.getString(R.string.search),
            new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_search)
                .color(ContextCompat.getColor(ctx, R.color.primary_light))
                .sizeDp(16),
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                Intent intent = new Intent(ctx, SearchElephantActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
              }
            }));


    navItems.add(
        new NavItem(ctx.getString(R.string.sync_db),
            new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_refresh_sync)
                .color(ContextCompat.getColor(ctx, R.color.primary_light))
                .sizeDp(16),
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
//                Intent intent = new Intent(ctx, SearchElephantActivity.class);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(intent);
              }
            }));

    navItems.add(
        new NavItem(ctx.getString(R.string.locations),
            new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_pin)
                .color(ContextCompat.getColor(ctx, R.color.primary_light))
                .sizeDp(16),
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
//                Intent intent = new Intent(ctx, SearchElephantActivity.class);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(intent);
              }
            }));


    navItems.add(
        new NavItem(ctx.getString(R.string.add_elephant),
            new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_plus)
                .color(ContextCompat.getColor(ctx, R.color.primary_light))
                .sizeDp(16),
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                Intent intent = new Intent(ctx, ManageElephantActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
              }
            }));

    navItems.add(
        new NavItem(ctx.getString(R.string.add_contact),
            new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_account_add)
                .color(ContextCompat.getColor(ctx, R.color.primary_light))
                .sizeDp(16),
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                Intent intent = new Intent(ctx, AddContactActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(intent);
              }
            }));

    navItems.add(
        new NavItem(ctx.getString(R.string.settings),
            new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_settings)
                .color(ContextCompat.getColor(ctx, R.color.primary_light))
                .sizeDp(16),
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
//                Intent intent = new Intent(ctx, SearchElephantActivity.class);
//                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
//                ctx.startActivity(intent);
              }
            }));

    navItems.add(
        new NavItem(ctx.getString(R.string.logout),
            new IconicsDrawable(ctx).icon(MaterialDesignIconic.Icon.gmi_sign_in)
                .color(ContextCompat.getColor(ctx, R.color.primary_light))
                .sizeDp(16),
            new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                Intent intent = new Intent(ctx, AuthActivity.class);
                intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
                // Preferences.setUsername(ctx.getApplicationContext(), null);
                // Preferences.setPassword(ctx.getApplicationContext(), null);
                ctx.startActivity(intent);
              }
            }));
  }

  public void setSelection(int selection) {
    this.selection = selection;
    notifyDataSetChanged();
  }

  @Override
  public int getCount() {
    return (navItems.size());
  }


  @Override
  public NavItem getItem(int index) {
    return (navItems.get(index));
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
      LayoutInflater inflater = LayoutInflater.from(ctx);
      view = inflater.inflate(R.layout.list_item_drawer, parent, false);

      TextView item = view.findViewById(R.id.nav_item);
      item.setText(getItem(index).title);
      item.setCompoundDrawables(getItem(index).icon, null, null, null);
      item.setOnClickListener(getItem(index).listener);
    }

    if (index == selection) {
//      image.setImageResource(R.drawable.menu_icon_home_enabled);
//      label.setTextColor(context.getResources().getColor(R.color.list_selection));
    } else {
//      label.setTextColor(ContextCompat.getColor(context, R.color.list_indice));
    }
    return view;
  }


}
