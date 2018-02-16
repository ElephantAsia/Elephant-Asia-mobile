package fr.elephantasia.activities.sync.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.utils.KeyboardHelpers;

public class UploadFragment extends Fragment {

  // View binding
  @BindView(R.id.list) ListView childrenList;


  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.home_activity, container, false);
    KeyboardHelpers.hideKeyboardListener(view, getActivity());
    ButterKnife.bind(this, view);
    return view;
  }
}