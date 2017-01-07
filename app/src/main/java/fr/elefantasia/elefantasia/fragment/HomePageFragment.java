package fr.elefantasia.elefantasia.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.HomePageFragmentGridViewAdapter;
import fr.elefantasia.elefantasia.interfaces.HomePageInterface;


public class HomePageFragment extends Fragment {

    private GridView gridView;
    private HomePageInterface homePageInterface;
    private HomePageFragmentGridViewAdapter homePageFragmentGridViewAdapter;


    public HomePageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment, container, false);

        gridView = (GridView)view.findViewById(R.id.home_page_gridview);

        homePageInterface = new HomePageInterface() {
            @Override
            public void onItemClick(Intent intent) {
                ((HomePageInterface)getActivity()).onItemClick(intent);
            }
        };

        homePageFragmentGridViewAdapter = new HomePageFragmentGridViewAdapter(getContext(), homePageInterface);
        gridView.setAdapter(homePageFragmentGridViewAdapter);

        return (view);
    }

}
