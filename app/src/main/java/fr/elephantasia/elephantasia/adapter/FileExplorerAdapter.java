package fr.elephantasia.elephantasia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.interfaces.FileExplorerInterface;

/**
 * Created by Stephane on 22/03/2017.
 */

public class FileExplorerAdapter extends BaseAdapter {

    private Context context;
    private FileExplorerInterface listener;

    private List<String> files;

    public FileExplorerAdapter(Context context, FileExplorerInterface listener) {
        this.context = context;
        this.listener = listener;

        this.files = new ArrayList<>();
    }

    public void setData(List<String> files) {
        this.files = files;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (files.size());
    }

    @Override
    public String getItem(int index) {
        return (files.get(index));
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
            view = inflater.inflate(R.layout.file_explorer_overview, parent, false);
        }

        String item = getItem(index);

        refreshView(view, item);
        refreshContent(view, item);

        return view;
    }

    private void refreshView(View view, final String item) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onFileClick(item);
            }
        });
    }

    private void refreshContent(View view, final String item) {
        TextView title = (TextView)view.findViewById(R.id.file_name);
        title.setText(item);
    }
}
