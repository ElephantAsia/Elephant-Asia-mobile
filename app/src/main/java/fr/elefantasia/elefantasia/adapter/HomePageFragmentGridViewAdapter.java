package fr.elefantasia.elefantasia.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.activities.AddElephantActivity;
import fr.elefantasia.elefantasia.interfaces.HomePageInterface;

/**
 * Created by Stephane on 07/01/2017.
 */

public class HomePageFragmentGridViewAdapter extends BaseAdapter {

    private Context context;
    private HomePageInterface listener;
    private List<String> content;
    private List<Intent> intents;

    public HomePageFragmentGridViewAdapter(Context context, HomePageInterface listener) {
        this.context = context;
        this.listener = listener;
        this.content = new ArrayList<>();
        this.intents = new ArrayList<>();

        content.add(context.getString(R.string.add_elephant));

        intents.add(new Intent(context, AddElephantActivity.class));
    }

    @NonNull
    public Intent getIntent(int index) {
        return (intents.get(index));
    }

    @Override
    public int getCount() {
        return (content.size());
    }

    @Override
    public String getItem(int index) {
        return (content.get(index));
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View old, ViewGroup parent) {
        boolean creation = (old == null);
        View view;

        if (!creation) {
            view = old;
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.grid_item_home, parent, false);

        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(getIntent(index));
            }
        });

        TextView textView = (TextView)view.findViewById(R.id.grid_item_label);
        textView.setText(getItem(index));

        // ...

        return view;
    }

}
