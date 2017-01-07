package fr.elefantasia.elefantasia.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.database.Elefant;
import fr.elefantasia.elefantasia.interfaces.AddElephantInterface;


public class AddElephantCivilStatusFragment extends Fragment {

    //private ListView listView;
    //private Adapter adapter;
//    private ElefantDatabase database;
//    private LinearLayout addLayout;

    FloatingActionButton fabNext;


    public AddElephantCivilStatusFragment() {
        //adapter = new Adapter(getContext());
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_elephant_civil_status_fragment, container, false);

        /*listView = (ListView) view.findViewById('R.id.main_list);
        listView.setAdapter(adapter);

        database = new ElefantDatabase(getActivity());
        database.open();

        Elefant elefant = createElefant("stephouuu");
        Elefant elefant1 = createElefant("robert");

        database.insertElefant(elefant);
        database.insertElefant(elefant1);

        adapter.addData(database.getElefantWithName("robert"));*/

        fabNext = (FloatingActionButton)view.findViewById(R.id.elephant_civil_status_fab);
        fabNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((AddElephantInterface) getActivity()).next();
            }
        });

        return (view);
    }


    private Elefant createElefant(String name) {
        Elefant elefant = new Elefant();
        elefant.name = name;

        return (elefant);
    }

    private static class Adapter extends BaseAdapter {

        private Context context;

        List<Elefant> data = new ArrayList<>();

        public Adapter(@NonNull Context context) {
            this.context = context;
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

//            if (creation) {
//                view = inflater.inflate(R.layout.elefant_list_fragment, parent, false);
//            }

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
                }
            });

        }

        private void refreshContent(View view, boolean creation, Elefant elefant) {
            //TextView id = (TextView) view.findViewById(R.id.etRegistrationNumber);
            //TextView name = (TextView) view.findViewById(R.id.etName);

            //id.setText(String.valueOf(elefant.id));
            //name.setText(elefant.name);
        }

    }

}
