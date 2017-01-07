package fr.elefantasia.elefantasia.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fr.elefantasia.elefantasia.R;
import fr.elefantasia.elefantasia.adapter.ElephantAdapter;
import fr.elefantasia.elefantasia.utils.ElephantInfo;

/**
 * Created by care_j on 15/11/16.
 */

public class SearchActivity extends AppCompatActivity {

        ListView mListView;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.search_activity);

            mListView = (ListView) findViewById(R.id.listViewSearch);

            List<ElephantInfo> elephants = generateElephants();
            ElephantAdapter adapter = new ElephantAdapter(SearchActivity.this, elephants);
            mListView.setAdapter(adapter);
        }


        private List<ElephantInfo> generateElephants(){
            List<ElephantInfo> elephants = new ArrayList<ElephantInfo>();
            elephants.add(new ElephantInfo("José", "MS001", "ELVESPA", "17/09/1993", ElephantInfo.Legallity.ILLEGAL, ElephantInfo.Gender.MALE));
            elephants.add(new ElephantInfo("Julien", "MS002", "JUTIN", "19/06/1996", ElephantInfo.Legallity.LEGAL, ElephantInfo.Gender.MALE));
            elephants.add(new ElephantInfo("Joffrey", "MS003", "SPACEINVADER", "10/05/1995", ElephantInfo.Legallity.ILLEGAL, ElephantInfo.Gender.MALE));
            elephants.add(new ElephantInfo("Josiane", "MS004", "JOSIANETKT", "14/01/1993", ElephantInfo.Legallity.ILLEGAL, ElephantInfo.Gender.FEMALE));
            return elephants;
        }
}
