package com.example.sagun.inote;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Html;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBOpenHelper dbOpenHelper;
    SQLiteDatabase db;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapter adapter;
    ArrayList<Note> arrayList = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //create database and table
        dbOpenHelper = new DBOpenHelper(this);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        // Load notes to the recyclerview
        loadNotes();

        //attach swipe to delete action to recycler view
        ItemTouchHelper.Callback callback = new SwipeHelper(adapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        helper.attachToRecyclerView(recyclerView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.drawable.ic_action_add);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("REQUEST_CODE", 1001);
                startActivity(intent);
            }
        });
    }

    public void loadNotes() {
        //open database
        db = dbOpenHelper.getReadableDatabase();

        arrayList.clear();

        Cursor cursor = dbOpenHelper.getNotes(db);
        while (cursor.moveToNext()) {
            Note note = new Note(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
            arrayList.add(note);
        }

        adapter = new RecyclerAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //close database
        // db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.action_sample_data:
                insertSampleNotes();
                break;

            case R.id.action_delete_all_data:
                deleteAllNotes();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllNotes() {
        db = dbOpenHelper.getWritableDatabase();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbOpenHelper.deleteAllNotes(db);
                loadNotes();

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        });
        builder.setTitle("Are you sure you want to delete ?");
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void insertSampleNotes() {
        db = dbOpenHelper.getWritableDatabase();

        dbOpenHelper.insertNote("This is simple note", db);
        dbOpenHelper.insertNote("This is an example of long note which does not fit in the screen", db);
        loadNotes();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            loadNotes();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();
    }
}
