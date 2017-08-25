package fr.elephantasia.activities.editElephant.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.editElephant.EditElephantActivity;
import fr.elephantasia.adapter.DocumentAdapter;
import fr.elephantasia.database.model.Document;
import io.realm.RealmList;

import static butterknife.ButterKnife.findById;

public class EditDocumentFragment extends Fragment {

  @BindView(R.id.list)
  ListView list;
  private DocumentAdapter adapter;
	private List<Document> documents = new ArrayList<>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
		adapter = new DocumentAdapter(getContext(), new RealmList<Document>());
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.add_elephant_document_fragment, container, false);

    ButterKnife.bind(this, view);
    setupFooter(inflater);
    list.setAdapter(adapter);

		if (documents.size() != 0) {
			adapter.addAll(documents);
			documents.clear();
		}
    return (view);
  }

  public void setupFooter(LayoutInflater inflater) {
    View footerView = inflater.inflate(R.layout.add_document_footer, list, false);
    TextView addButton = findById(footerView, R.id.add_contact);

    addButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View v) {
        ((EditElephantActivity) getActivity()).onAddDocumentClick();
      }
    });

    list.addFooterView(footerView);
  }

  public void addDocument(@NonNull Document doc) {
		if (adapter == null) {
			documents.add(doc);
		} else {
			adapter.add(doc);
		}
  }

  public void addDocuments(@NonNull List<Document> docs) {
		if (adapter == null) {
			documents.addAll(docs);
		} else {
			adapter.addAll(docs);
		}
	}

}