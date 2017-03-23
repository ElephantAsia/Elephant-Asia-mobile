package fr.elephantasia.elephantasia.fragment.addElephant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.interfaces.AddElephantInterface;

public class AddElephantDocumentFragment extends Fragment {

    private View addDocumentButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_elephant_document_fragment, container, false);

        addDocumentButton = view.findViewById(R.id.add_document_button);

        addDocumentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AddElephantInterface)getActivity()).onAddDocumentClick();
            }
        });

        return (view);
    }

}