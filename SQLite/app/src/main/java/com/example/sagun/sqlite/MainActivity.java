package com.example.sagun.sqlite;

import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    dataBaseHelper myDb;
    EditText etName, etSurName, etMarks, etID;
    Button btnAdd, btnView, btnUpdate, btnDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new dataBaseHelper(this);

        etID = (EditText) findViewById(R.id.etID);
        etName = (EditText) findViewById(R.id.txtName);
        etSurName = (EditText) findViewById(R.id.txtSurName);
        etMarks = (EditText) findViewById(R.id.txtMarks);
        btnAdd = (Button) findViewById(R.id.btnADD);
        btnView = (Button) findViewById(R.id.btnView);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        btnDelete =(Button) findViewById(R.id.btnDelete);
        addDdata();
        showData();
        updateData();
        deleteData();
    }
    public void addDdata() {
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (etName.getText().length() > 0 && etMarks.getText().length() > 0) {
                            boolean isInserted = myDb.insertData(etName.getText().toString(),
                                    etSurName.getText().toString(),
                                    etMarks.getText().toString());

                            if (isInserted = true)
                                Toast.makeText(MainActivity.this, "Data Inserted Successfully", Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this, "Data Insertion Failure!!!", Toast.LENGTH_LONG).show();

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Name and marks field cannot be empty", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
    }

    public void showData() {
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor result = myDb.showData();

                        if (result.getCount() == 0)
                        {
                            //show message
                            displayData("Error","Data Not Found!");
                            return;
                        }
                        else
                        {
                            StringBuffer stringBuffer = new StringBuffer();
                            while (result.moveToNext())
                            {
                                stringBuffer.append("ID: " + result.getString(0) + "\n");
                                stringBuffer.append("Name: " + result.getString(1) + "\n");
                                stringBuffer.append("SurName: " + result.getString(2) + "\n");
                                stringBuffer.append("Marks: " + result.getString(3) + "\n");
                            }
                            //Display data
                            displayData("Data", stringBuffer.toString());
                        }
                    }
                }

        );
    }
    public  void updateData() {
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isUpdaated = myDb.updateData(etID.getText().toString(), etName.getText().toString(),
                                etSurName.getText().toString(), etMarks.getText().toString());
                        if(isUpdaated == true)
                            Toast.makeText(MainActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Update Failed", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void deleteData() {
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int deletedRecords = myDb.deleteData(etID.getText().toString());
                        if (deletedRecords > 0)
                            Toast.makeText(MainActivity.this, deletedRecords + "Data Deleted!", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Data Not Found!", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void displayData(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
