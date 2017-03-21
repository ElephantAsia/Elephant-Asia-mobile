package fr.elephantasia.elephantasia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.utils.ElephantInfo;
import fr.elephantasia.elephantasia.utils.RefreshElephantPreview;

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

        RefreshElephantPreview.refresh(context, view, info);

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

