package fr.elefantasia.elefantasia.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elefantasia.elefantasia.R;

public class SplashScreenFragment extends Fragment {

    public SplashScreenFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.splash_screen_fragment, container, false);


        return view;
    }

}
