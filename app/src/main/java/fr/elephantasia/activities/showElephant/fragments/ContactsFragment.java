package fr.elephantasia.activities.showElephant.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.elephantasia.R;
import fr.elephantasia.activities.showElephant.ShowElephantActivity;
import fr.elephantasia.adapter.ContactPreviewAdapter;
import fr.elephantasia.database.model.Elephant;

public class ContactsFragment extends Fragment {

    // View binding
    @BindView(R.id.list) ListView list;
    @BindView(R.id.no_item) FrameLayout noItem;

    // Attr
    private ContactPreviewAdapter adapter;
    private Elephant elephant;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        elephant = ((ShowElephantActivity) getActivity()).getElephant();
        adapter = new ContactPreviewAdapter(getContext(), elephant.contacts, false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manage_elephant_contact_fragment, container, false);

        ButterKnife.bind(this, view);
        list.setAdapter(adapter);

        if (elephant.contacts != null && !elephant.contacts.isEmpty()) {
            noItem.setVisibility(View.GONE);
            list.setVisibility(View.VISIBLE);
        } else {
            noItem.setVisibility(View.VISIBLE);
            list.setVisibility(View.GONE);
        }
        return (view);
    }

}
