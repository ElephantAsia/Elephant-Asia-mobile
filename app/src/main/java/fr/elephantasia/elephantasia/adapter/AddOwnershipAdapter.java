package fr.elephantasia.elephantasia.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.interfaces.AddOwnershipListener;
import fr.elephantasia.elephantasia.utils.UserInfo;

/**
 * Created by Stephane on 16/03/2017.
 */

public class AddOwnershipAdapter extends BaseAdapter {

    private Context context;
    private boolean showFooter;
    private AddOwnershipListener listener;
    private List<UserInfo> users;

    public AddOwnershipAdapter(Context context, boolean showFooter, AddOwnershipListener listener) {
        this.context = context;
        this.showFooter = showFooter;
        this.listener = listener;

        users = new ArrayList<>();
    }

    public void setData(List<UserInfo> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    public void addItem(UserInfo user) {
        users.add(user);
        notifyDataSetChanged();
    }

    public void clear() {
        this.users.clear();
    }

    @Override
    public int getCount() {
        return (users.size() + ((showFooter) ? 1 : 0));
    }

    @Override
    public UserInfo getItem(int index) {
        UserInfo ret = null;

        if (index < users.size()) {
            ret = users.get(index);
        }
        return (ret);
    }

    @Override
    public long getItemId(int index) {
        return index;
    }

    @Override
    public View getView(final int index, View old, ViewGroup parent) {
        View view;
        boolean creation = (old == null);
        boolean isFooter = isFooter(index);

        if (!creation) {
            view = old;
        } else {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.ownership_preview, parent, false);
        }

        UserInfo user = getItem(index);

        refreshView(view, user, isFooter);
        if (user != null) {
            refreshUser(view, user);
        }

        return view;
    }

    private void refreshView(View view, final UserInfo userInfo, boolean footer) {
        if (footer) {
            view.findViewById(R.id.footer_parent).setVisibility(View.VISIBLE);
            view.findViewById(R.id.owner_parent).setVisibility(View.GONE);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onAddClick();
                }
            });
        } else {
            view.findViewById(R.id.footer_parent).setVisibility(View.GONE);
            view.findViewById(R.id.owner_parent).setVisibility(View.VISIBLE);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(userInfo);
                }
            });
        }
    }

    private void refreshUser(View view, UserInfo user) {
        ((TextView)view.findViewById(R.id.username)).setText(user.name);
        ((TextView)view.findViewById(R.id.address)).setText(user.address);
        ((TextView)view.findViewById(R.id.email)).setText(user.email);
        ((TextView)view.findViewById(R.id.status)).setText(user.status.toString());
    }

    private boolean isFooter(int index) {
        return index > users.size() - 1 && showFooter;
    }

}
