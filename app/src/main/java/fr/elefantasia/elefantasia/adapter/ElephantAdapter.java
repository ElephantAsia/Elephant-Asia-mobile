package fr.elefantasia.elefantasia.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

/**
 * Created by care_j on 15/11/16.
 */

public class ElephantAdapter extends ArrayAdapter<ElephantInfo> {
    public ElephantAdapter(Context context, List<ElephantInfo> elephants) {
        super(context, 0, elephants);
    }

    @Override
    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_overview,parent, false);
        }

        TweetViewHolder viewHolder = (TweetViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new TweetViewHolder();
            viewHolder.registration = (TextView) convertView.findViewById(R.id.ovName);
            viewHolder.name = (TextView) convertView.findViewById(R.id.text);
            viewHolder.age = (TextView) convertView.findViewById(R.id.age);
            viewHolder.sex = (ImageView) convertView.findViewById(R.id.sex);
            viewHolder.legal = (ImageView) convertView.findViewById(R.id.legal);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<ElephantInfo> tweets
        ElephantInfo elephant = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.name.setText(elephant.getName());
        viewHolder.age.setText(elephant.getBornDate());
        viewHolder.registration.setText(elephant.getDatabaseNumber());
        ElephantInfo.Gender sex = elephant.getSex();
        if (sex == ElephantInfo.Gender.MALE) {
            viewHolder.sex.setImageResource(R.drawable.male);
        }
        else if (sex == ElephantInfo.Gender.FEMALE){
            viewHolder.sex.setImageResource(R.drawable.female);
        }
        else {
            viewHolder.sex.setImageResource(R.drawable.unknown);
        }
        ElephantInfo.Legallity legal = elephant.getLegallyRegistered();
        if (legal == ElephantInfo.Legallity.LEGAL) {
            viewHolder.legal.setImageResource(R.drawable.unknown);
        }
        else if (legal == ElephantInfo.Legallity.ILLEGAL) {
            viewHolder.legal.setImageResource(R.drawable.unknown);
        }
        else {
            viewHolder.legal.setImageResource(R.drawable.unknown);
        }
        return convertView;
    }

    private class TweetViewHolder {
        public TextView registration;
        public TextView name;
        public TextView age;
        public ImageView sex;
        public ImageView legal;
    }
}

