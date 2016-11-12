package fr.elefantasia.elefantasia.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.database.Elefant;
import fr.elefantasia.elefantasia.database.ElefantDatabase;


public class MainFragment extends Fragment {

    private ListView listView;
    private Adapter adapter;
    private ElefantDatabase database;

    public MainFragment() {
        adapter = new Adapter(getContext(), null);
    }

    private LinearLayout addLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);


        listView = (ListView) view.findViewById(R.id.main_list);
        listView.setAdapter(adapter);

        database = new ElefantDatabase(getActivity());
        database.open();

        Elefant elefant = createElefant("stephouuu");
        Elefant elefant1 = createElefant("robert");

        //database.insertElefant(elefant);
        //database.insertElefant(elefant1);

        adapter.addData(database.getElefantWithName("robert"));

        return (view);
    }

    private Elefant createElefant(String name) {
        Elefant elefant = new Elefant();
        elefant.name = name;

        return (elefant);
    }

    public static class Adapter extends BaseAdapter {

        private Context context;
        private Listener listener;

        List<Elefant> data = new ArrayList<>();

        public Adapter(@NonNull Context context, @Nullable Listener listener) {
            this.context = context;
            this.listener = listener;
        }

        public void addData(List<Elefant> list) {
            data.addAll(list);
            notifyDataSetChanged();
        }

        public void addItem(Elefant item) {
            data.add(item);
            notifyDataSetChanged();
        }

        public void resetData() {
            this.data.clear();
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Elefant getItem(int index) {
            return data.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup parent) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            boolean creation = view == null;

            if (creation) {
                view = inflater.inflate(R.layout.elefant_list_fragment, parent, false);
            }

            Elefant elefant = getItem(index);

            refreshView(view, creation, elefant);
            refreshContent(view, creation, elefant);

            return view;
        }

        private void refreshView(View view, boolean creation, final Elefant Elefant) {
            if (creation) {
                view.setFocusable(true);
            }
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        // ...
                    }
                }
            });
        }

        private void refreshContent(View view, boolean creation, Elefant elefant) {
            TextView id = (TextView) view.findViewById(R.id.elefant_id);
            TextView name = (TextView) view.findViewById(R.id.elefant_name);

            id.setText(String.valueOf(elefant.id));
            name.setText(elefant.name);
        }

        public interface Listener {
            /* some methods */
        }

    }

}
