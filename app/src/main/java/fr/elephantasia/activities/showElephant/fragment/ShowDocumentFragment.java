package fr.elephantasia.activities.showElephant.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.adapter.DocumentAdapter;
import fr.elephantasia.database.model.Document;
import io.realm.Realm;

public class ShowDocumentFragment extends Fragment {

  @BindView(R.id.list)
  ListView list;
  private DocumentAdapter adapter;
	// private List<Document> documents = new ArrayList<>();

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
		loadDocuments();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/* ShowDocumentFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_document_fragment, container, false);
    	View view = binding.getRoot();
   		binding.setE(((ShowElephantActivity) getActivity()).getElephant()); */
		View view = inflater.inflate(R.layout.show_document_fragment, container, false);
		ButterKnife.bind(this, view);
    list.setAdapter(adapter);
    return (view);
  }

  public void addDocument(Document document) {
  	if (adapter != null) {
  		adapter.add(document);
		}
	}

  private void loadDocuments() {
		Realm realm = Realm.getDefaultInstance();
		Integer id = ((ShowElephantActivity)getActivity()).getElephant().id;
		List<Document> documents = realm.copyFromRealm(realm.where(Document.class).equalTo(Document.ELEPHANT_ID, id).findAll());
		adapter = new DocumentAdapter(getContext(), documents, new DocumentAdapter.Listener() {
			@Override
			public void onDocumentClick(Document document) {
				((ShowElephantActivity)getActivity()).onDocumentClick(document);
			}
		});
	}
}
