package com.example.sagun.inote;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    DBOpenHelper helper;
    SQLiteDatabase db;
    private EditText txtNote;
    private String action;
    int noteID;
    String noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        helper = new DBOpenHelper(this);
        db = helper.getReadableDatabase();

        txtNote = (EditText) findViewById(R.id.txtNote);

        Intent intent = getIntent();
        int requestCode = getIntent().getExtras().getInt("REQUEST_CODE");

        if (requestCode == 1001) {
            action = intent.ACTION_INSERT;
            setTitle("Add New Note");
            txtNote.setText(intent.getAction());

        } else {
            action = intent.ACTION_EDIT;
            noteID = intent.getExtras().getInt("NOTE_ID");
            noteText = intent.getExtras().getString("NOTE_TEXT");
            txtNote.setText(noteText);
            txtNote.requestFocus();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finishEditing();
                break;
            case R.id.action_delete_data:
                helper.deleteNote(noteID, db, this);
//                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (action.equals(Intent.ACTION_EDIT)) {
            getMenuInflater().inflate(R.menu.edit_menu, menu);
        }
        return true;
    }

    private void finishEditing() {

        String newText = txtNote.getText().toString().trim();

        switch (action) {
            case Intent.ACTION_INSERT:
                if (newText.length() == 0) {
                    setResult(RESULT_CANCELED);
                } else {
                    helper.insertNote(newText, db);
                    Toast.makeText(this, "Note Inserted", Toast.LENGTH_SHORT).show();
                }
                break;
            case Intent.ACTION_EDIT:
                if (newText.length() == 0) {
                    helper.deleteNote(noteID, db, this);
                    Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
                } else if (txtNote.toString().equals(newText)) {
                    setResult((RESULT_CANCELED));
                } else {
                    helper.updateNote(noteID, db, newText);
                    Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
                }
        }
        finish();
    }

    @Override
    public void onBackPressed() {
        finishEditing();
    }
}
