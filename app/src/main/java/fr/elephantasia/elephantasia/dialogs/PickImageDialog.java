package fr.elephantasia.elephantasia.dialogs;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.io.File;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.PhotoAppsAdapter;
import fr.elephantasia.elephantasia.utils.ImageUtil;

public class PickImageDialog extends MaterialDialog {

    private ListView listView;
    private PhotoAppsAdapter adapter;

    private Listener listener;

    private int captureCode;
    private int importCode;

    public PickImageDialog(Builder builder) {
        super(builder);
    }

    public PickImageDialog setListener(Listener listener) {
        this.listener = listener;
        return this;
    }

    public PickImageDialog setCaptureCode(int captureCode) {
        this.captureCode = captureCode;
        return this;
    }

    public PickImageDialog setImportCode(int importCode) {
        this.importCode = importCode;
        return this;
    }

    public Intent getIntent(int position) {
        return adapter.getIntent(position);
    }

    public int getRequestCode(int position) {
        return adapter.getRequestCode(position);
    }

    public PickImageDialog load() {
        View view = getCustomView();
        if (view != null) {
            listView = (ListView) view.findViewById(R.id.list);

            File captureFile = ImageUtil.getCapturePhotoFile(getContext());
            adapter = new PhotoAppsAdapter(getContext(), captureFile, captureCode, importCode);
            setOnDismissListener(new OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    adapter.setPage(0);
                }
            });

            selectPhotoSource();

            listView.setAdapter(adapter);
        }
        return this;
    }

    private void selectPhotoSource() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (adapter.getPage() == 0) {
                    if (position == 0) {
                        listener.execute(adapter.getIntent(0), adapter.getRequestCode(0));
                        dismiss();
                    } else if (position == 1) {
                        adapter.nextPage();
                    } else {
                        adapter.setPage(2);
                    }
                } else if (adapter.getPage() == 1) {
                    listener.execute(adapter.getIntent(position), adapter.getRequestCode(position));
                    adapter.setPage(0);
                    dismiss();
                } else if (adapter.getPage() == 2) {
                    listener.execute(adapter.getIntent(position), adapter.getRequestCode(position));
                    adapter.setPage(0);
                    dismiss();
                }
            }
        });
    }

    public interface Listener {

        void execute(Intent intent, int requestCode);

    }
}
