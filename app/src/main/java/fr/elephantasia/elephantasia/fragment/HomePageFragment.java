package fr.elephantasia.elephantasia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.HomePageAdapter;
import fr.elephantasia.elephantasia.interfaces.HomePageAdapterInterface;


public class HomePageFragment extends Fragment {

    private ListView listView;
    private HomePageAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page_fragment, container, false);

        listView = (ListView)view.findViewById(R.id.list);
        adapter = new HomePageAdapter(getContext(), new HomePageAdapterInterface() {
            @Override
            public void onClick(int index) {
               Log.i("panel", "click " + index);
            }
        });
        listView.setAdapter(adapter);

        return (view);
    }

}