package fr.elephantasia.activities.home;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.database.model.Elephant;
import io.realm.Realm;

import static fr.elephantasia.database.model.Elephant.STATE_DRAFT;

public class HomePageDataFragment extends Fragment {

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.home_page_data_fragment, container, false);
    ButterKnife.bind(this, view);
    return (view);
  }


  @Override
  public void onResume() {
    super.onResume();
  }

  public void setOverviewData() {
    Realm realm = ((HomeActivity) getActivity()).getRealm();
    int total = realm.where(Elephant.class).findAll().size();
    int totalDraft = realm.where(Elephant.class).equalTo(STATE_DRAFT, true).findAll().size();
    int totalPending = realm.where(Elephant.class).equalTo(STATE_DRAFT, true).findAll().size();
  }
}