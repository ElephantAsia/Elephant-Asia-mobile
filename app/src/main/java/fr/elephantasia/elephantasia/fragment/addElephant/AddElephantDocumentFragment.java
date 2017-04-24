package fr.elephantasia.elephantasia.fragment.addElephant;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.DocumentAdapter;
import fr.elephantasia.elephantasia.interfaces.AddElephantInterface;
import fr.elephantasia.elephantasia.interfaces.DocumentInterface;

public class AddElephantDocumentFragment extends Fragment {

    private ListView list;
    private DocumentAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new DocumentAdapter(getContext(), true, new DocumentInterface() {
            @Override
            public void onAddClick() {
                ((AddElephantInterface)getActivity()).onAddDocumentClick();
            }

            @Override
            public void onDocumentClick() {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_elephant_document_fragment, container, false);

        list = (ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);

        return (view);
    }

    public void addDocument(@NonNull String path) {
        adapter.addItem(path);
    }

}