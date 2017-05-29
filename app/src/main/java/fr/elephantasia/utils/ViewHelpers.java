package fr.elephantasia.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * Created by seb on 29/05/2017.
 */

public class ViewHelpers {

  /**
   * Used when  we need to nest a ListView inside a ScrollView.
   * eg: ShowParentageFragment.
   * more info: https://stackoverflow.com/questions/27646209/disable-scrolling-for-listview-and-enable-for-whole-layout
   * @param listView the listView that we want to extends
   */
  public static void extendListView(ListView listView) {
    ListAdapter listAdapter =  listView.getAdapter();

    if (listAdapter == null) return;

    int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
    int totalHeight = 0;
    View view = null;

    for (int i = 0; i < listAdapter.getCount(); i++) {
      view = listAdapter.getView(i, view, listView);

      if (i == 0) {
        view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.MATCH_PARENT));
      }

      view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
      totalHeight += view.getMeasuredHeight();
    }

    ViewGroup.LayoutParams params = listView.getLayoutParams();
    params.height = totalHeight + ((listView.getDividerHeight()) * (listAdapter.getCount()));

    listView.setLayoutParams(params);
    listView.requestLayout();

  }
}
