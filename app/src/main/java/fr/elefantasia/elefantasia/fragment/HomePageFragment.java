package fr.elefantasia.elefantasia.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.interfaces.HomePageInterface;


public class HomePageFragment extends Fragment {

    //private GridView gridView;
    //private HomePageInterface homePageInterface;
    //private HomePageFragmentGridViewAdapter homePageFragmentGridViewAdapter;

    private FloatingActionButton addFab;

    public HomePageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_page_fragment, container, false);

        /*gridView = (GridView)view.findViewById(R.id.home_page_gridview);
        homePageInterface = new HomePageInterface() {
            @Override
            public void onNewActivity(Intent intent, Integer code) {
                ((HomePageInterface)getActivity()).onNewActivity(intent, code);
            }
        };

        homePageFragmentGridViewAdapter = new HomePageFragmentGridViewAdapter(getContext(), homePageInterface);
        gridView.setAdapter(homePageFragmentGridViewAdapter);*/

        addFab = (FloatingActionButton)view.findViewById(R.id.home_page_fab);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomePageInterface) getActivity()).addElephant();
            }
        });

        return (view);
    }

}
