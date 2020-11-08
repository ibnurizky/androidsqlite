package com.example.aplikasisqlite;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.aplikasisqlite.helper.DbHelper;

public class AddEdit extends AppCompatActivity {
    EditText et_id, et_nama, et_alamat;
    Button submit, cancel;
    DbHelper SQLite = new DbHelper(this);
    String id, nama, alamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_id = (EditText) findViewById(R.id.et_id);
        et_nama = (EditText) findViewById(R.id.et_nama);
        et_alamat = (EditText) findViewById(R.id.et_alamat);
        submit = (Button) findViewById(R.id.btn_submit);
        cancel = (Button) findViewById(R.id.btn_cancel);
        id = getIntent().getStringExtra(MainActivity.TAG_ID);
        nama = getIntent().getStringExtra(MainActivity.TAG_NAMA);
        alamat = getIntent().getStringExtra(MainActivity.TAG_ALAMAT);

        if (id == null || id == ""){
            setTitle("Add Data");
        } else {
            setTitle("Edit Data");
            et_id.setText(id);
            et_nama.setText(nama);
            et_alamat.setText(alamat);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               try {
                   if (et_id.getText().toString().equals("")){
                       save();
                   } else {
                       edit();
                   }
               } catch (Exception e) {
                   Log.e("Submit", e.toString());
               }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank() {
        et_nama.requestFocus();
        et_id.setText(null);
        et_nama.setText(null);
        et_alamat.setText(null);
    }

    private void save() {
        if (String.valueOf(et_nama.getText()).equals(null) ||
                String.valueOf(et_nama.getText()).equals("") ||
                        String.valueOf(et_alamat.getText()).equals(null) ||
                                String.valueOf(et_alamat.getText()).equals("")) {
            Toast.makeText(getApplicationContext(), "Mohon masukkan nama dan alamat..", Toast.LENGTH_SHORT).show();
        } else {
                   SQLite.insert(et_nama.getText().toString().trim(),
                           et_alamat.getText().toString().trim());
                   blank();
                   finish();
        }
    }

    private void edit() {
        if(String.valueOf(et_nama.getText()).equals(null) ||
           String.valueOf(et_nama.getText()).equals("") ||
           String.valueOf(et_alamat.getText()).equals(null) ||
           String.valueOf(et_alamat.getText()).equals("")) {
            Toast.makeText(getApplicationContext(), "Mohon masukkan nama dan alamat",
                    Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(et_id.getText().toString().trim()),
                    et_nama.getText().toString().trim(),
                    et_alamat.getText().toString().trim());
            blank();
            finish();
        }
    }
}
