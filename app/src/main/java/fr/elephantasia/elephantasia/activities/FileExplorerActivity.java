package fr.elephantasia.elephantasia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.elephantasia.elephantasia.R;
import fr.elephantasia.elephantasia.adapter.FileExplorerAdapter;
import fr.elephantasia.elephantasia.interfaces.FileExplorerInterface;

public class FileExplorerActivity extends AppCompatActivity implements FileExplorerInterface {

    private ListView listView;
    private FileExplorerAdapter adapter;

    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_explorer_activity);

        listView = (ListView)findViewById(R.id.list);

        adapter = new FileExplorerAdapter(this, this);
        listView.setAdapter(adapter);

        path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
        Log.i("path", path);
        if (getIntent().hasExtra("path")) {
            path = getIntent().getStringExtra("path");
        }
        setTitle(path);

        setData();
    }

    @Override
    public void onFileClick(String filename) {
        Log.i("click", filename);
        if (path.endsWith(File.separator)) {
            filename = path + filename;
        } else {
            filename = path + File.separator + filename;
        }
        Log.i("goto", filename);
        if (new File(filename).isDirectory()) {
            Intent intent = new Intent(this, FileExplorerActivity.class);
            intent.putExtra("path", filename);
            startActivity(intent);
        } else {
            Toast.makeText(this, filename + " is not a directory", Toast.LENGTH_LONG).show();
        }
    }

    private void setData() {
        List values = new ArrayList();
        File dir = new File(path);
        if (!dir.canRead()) {
            setTitle(getTitle() + " (inaccessible)");
        }
        String[] list = dir.list();
        if (list != null) {
            for (String file : list) {
                if (!file.startsWith(".")) {
                    values.add(file);
                    Log.i("fileexplorer", "add " + file);
                }
            }
        }
        Collections.sort(values);
        adapter.setData(values);
    }
}
