package fr.elefantasia.elefantasia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.utils.ElephantInfo;


/**
 * Created by care_j on 15/11/16.
 */

public class ElephantAdapter extends BaseAdapter {

    private Context context;
    private List<ElephantInfo> elephants;

    public ElephantAdapter(Context context) {
        this.context = context;
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

        //TextView registration = (TextView) view.findViewById(R.id.ovName);
        TextView name = (TextView) view.findViewById(R.id.ovName);
        TextView age = (TextView) view.findViewById(R.id.age);
        ImageView sex = (ImageView) view.findViewById(R.id.sex);
        ImageView legal = (ImageView) view.findViewById(R.id.legal);

        //getItem(position) va récupérer l'item [position] de la List<ElephantInfo> tweets
        ElephantInfo elephant = getItem(index);

        //il ne reste plus qu'à remplir notre vue
        name.setText(elephant.name);
        age.setText(elephant.birthDate);
        //viewHolder.registration.setText(elephant.getDatabaseNumber());
        if (elephant.sex == ElephantInfo.Gender.MALE) {
            sex.setImageResource(R.drawable.male);
        }
        else if (elephant.sex == ElephantInfo.Gender.FEMALE){
            sex.setImageResource(R.drawable.female);
        }
        else {
            sex.setImageResource(R.drawable.unknown);
        }
        /*ElephantInfo.Legallity legal = elephant.getLegallyRegistered();
        if (legal == ElephantInfo.Legallity.LEGAL) {
            viewHolder.legal.setImageResource(R.drawable.unknown);
        }
        else if (legal == ElephantInfo.Legallity.ILLEGAL) {
            viewHolder.legal.setImageResource(R.drawable.unknown);
        }
        else {*/
            legal.setImageResource(R.drawable.unknown);
        //}
        return view;
    }
}

