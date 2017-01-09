package fr.elefantasia.elefantasia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

public class SearchElephantAdapter extends BaseAdapter {

    private Context context;
    private Listener listener;
    private List<ElephantInfo> elephants;

    public SearchElephantAdapter(Context context, Listener listener) {
        this.context = context;
        this.listener = listener;
        this.elephants = new ArrayList<>();
    }

    public void addList(List<ElephantInfo> elephants) {
        this.elephants.addAll(elephants);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (elephants.size());
    }

    @Override
    public ElephantInfo getItem(int index) {
        return (elephants.get(index));
    }

    @Override
    public long getItemId(int index) {
        return index;
    }


    @Override
    public View getView(final int index, View old, ViewGroup parent) {

        View view;

        if (old != null) {
            view = old;
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.search_overview, parent, false);
        }

        final ElephantInfo info = getItem(index);
        String sex = (info.sex == ElephantInfo.Gender.FEMALE) ? context.getString(R.string.female) : context.getString(R.string.male);

        TextView name = (TextView)view.findViewById(R.id.search_overview_name);
        TextView registration = (TextView)view.findViewById(R.id.search_overview_registration);
        TextView location = (TextView)view.findViewById(R.id.search_overview_location);

        name.setText(String.format(context.getString(R.string.elephant_name_sex_age), info.name, sex, "-"));
        registration.setText(info.registrationID);
        location.setText(String.format(context.getString(R.string.elephant_location), info.registrationVillage, info.registrationProvince));

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(info);
            }
        });

        return view;
    }

    public interface Listener
    {
        void onItemClick(ElephantInfo info);
    }
}

