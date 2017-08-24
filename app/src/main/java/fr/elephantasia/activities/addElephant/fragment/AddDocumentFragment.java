package fr.elephantasia.activities.addElephant.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.addElephant.AddElephantActivity;
import fr.elephantasia.adapter.DocumentAdapter;
import fr.elephantasia.database.model.Document;
import io.realm.RealmList;

import static butterknife.ButterKnife.findById;

public class AddDocumentFragment extends Fragment {

    @BindView(R.id.list)
    ListView list;
    private DocumentAdapter adapter;

      @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DocumentAdapter(getContext() , new RealmList<Document>());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_elephant_document_fragment, container, false);

        ButterKnife.bind(this, view);
        setupFooter(inflater);
        list.setAdapter(adapter);
        return (view);
    }

    public void setupFooter(LayoutInflater inflater) {
        View footerView = inflater.inflate(R.layout.add_document_footer, list, false);
        TextView addButton = findById(footerView, R.id.add_contact);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((AddElephantActivity) getActivity()).onAddDocumentClick();
            }
        });

        list.addFooterView(footerView);
    }

  public void addDocument(@NonNull Document doc) {
      adapter.add(doc);
  }

}