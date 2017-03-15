package fr.elefantasia.elefantasia.utils;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
    }

}
