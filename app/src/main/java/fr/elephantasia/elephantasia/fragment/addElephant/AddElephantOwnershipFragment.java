package fr.elephantasia.elephantasia.fragment.addElephant;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.AddOwnershipAdapter;
import fr.elephantasia.elephantasia.interfaces.AddElephantInterface;
import fr.elephantasia.elephantasia.interfaces.AddOwnershipListener;
import fr.elephantasia.elephantasia.utils.UserInfo;

public class AddElephantOwnershipFragment extends Fragment {

    private ListView list;

    private AddOwnershipAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new AddOwnershipAdapter(getContext(), true, new AddOwnershipListener() {
            @Override
            public void onAddClick() {
                ((AddElephantInterface)getActivity()).addOwner();
            }

            @Override
            public void onItemClick(UserInfo userInfo) {
                Log.i("click", userInfo.name);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_elephant_ownership_fragment, container, false);

        list = (ListView)view.findViewById(R.id.list);
        list.setAdapter(adapter);

        return (view);
    }

    public void refreshOwner(UserInfo user) {
        adapter.addItem(user);
    }

}