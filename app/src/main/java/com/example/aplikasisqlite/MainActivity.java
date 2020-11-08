package com.example.aplikasisqlite;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.aplikasisqlite.adapter.Adapter;
import com.example.aplikasisqlite.data.Data;
import com.example.aplikasisqlite.helper.DbHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemsList = new ArrayList<Data>();
    Adapter adapter;
    DbHelper SQLite = new DbHelper(this);
    public static final String TAG_ID = "id";
    public static final String TAG_NAMA = "nama";
    public static final String TAG_ALAMAT = "alamat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //-------------//
        SQLite = new DbHelper(getApplicationContext());
        FloatingActionButton fab = findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.listView);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddEdit.class);
                startActivity(intent);
            }
        });

        adapter = new Adapter(MainActivity.this, itemsList);
        listView.setAdapter(adapter);

        //list view bila ditekan lama
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String idx = itemsList.get(position).getId();
                final String nama = itemsList.get(position).getNama();
                final String alamat = itemsList.get(position).getAlamat();
                CharSequence[] dialogitem = {"Edit", "Delete"};

                dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0 : Intent intent = new Intent(MainActivity.this, AddEdit.class);
                                     intent.putExtra(TAG_ID, idx);
                                     intent.putExtra(TAG_NAMA, nama);
                                     intent.putExtra(TAG_ALAMAT, alamat);
                                     startActivity(intent);
                                     break;
                            case 1 : SQLite.delete(Integer.parseInt(idx));
                                     itemsList.clear();
                                     getAllData();
                                     break;
                        }
                    }
                }).show();
                return false;
            }
        });
    }

    private void getAllData() {
        ArrayList<HashMap<String,String>> row = SQLite.getAllData();
        for (int i = 0; i < row.size(); i++){
            String id = row.get(i).get(TAG_ID);
            String poster = row.get(i).get(TAG_NAMA);
            String title = row.get(i).get(TAG_ALAMAT);
            Data data = new Data();
            data.setId(id);
            data.setNama(poster);
            data.setAlamat(title);
            itemsList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemsList.clear();
        getAllData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}