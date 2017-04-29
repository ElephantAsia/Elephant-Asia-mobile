package fr.elephantasia.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import fr.elephantasia.R;

public class RefreshElephantPreview {

  static public void refresh(Context context, View view, ElephantInfo info) {
    String sex = (info.sex == ElephantInfo.Gender.FEMALE) ? context.getString(R.string.female) : context.getString(R.string.male);

    TextView name = (TextView) view.findViewById(R.id.search_overview_name);
    TextView registration = (TextView) view.findViewById(R.id.search_overview_registration);
    TextView location = (TextView) view.findViewById(R.id.search_overview_location);

    name.setText(String.format(context.getString(R.string.elephant_name_sex_age), info.name, sex, "-"));
    registration.setText(info.regID);
    location.setText(String.format(context.getString(R.string.elephant_location), info.regCity, info.regProvince));
  }

}
