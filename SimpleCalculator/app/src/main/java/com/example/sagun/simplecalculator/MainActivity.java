package com.example.sagun.simplecalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //define variables to hold user input value and display output as edittext and textview
    //double variables to hold the entered value and calcualted value
    private Button button;
    EditText e1, e2=null;
    TextView t1, t2;
    double num1, num2;
    double area, perimeter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    // OnClickListener set for button onclick event
        button = (Button) findViewById(R.id.btnCalculate);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
    //assign value from textview and edittext to variables
        e1 = (EditText) findViewById(R.id.txtHeight);
        e2 = (EditText) findViewById(R.id.txtWidth);
        t1 = (TextView) findViewById(R.id.txtArea);
        t2 = (TextView) findViewById(R.id.txtPerimeter);

       try { //check if the input edittext field is greater than zero
           if ((e1.getText().toString().length() > 0) && (e2.getText().toString().length() > 0)) {
               //parse user input value to double and assign to variable
               num1 = Double.parseDouble(e1.getText().toString());
               num2 = Double.parseDouble(e2.getText().toString());

               //calculate area and perimeter
               area=num1*num2;
               perimeter=2*(num1+num2);

                //display area and perimeter to the textview with 2 places decimal format
               t1.setText(String.format("%.2f",area));
               t2.setText(String.format("%.2f",perimeter));

           }
           else //if the input field length is less than or equal to zero, display message
               Toast.makeText(getApplicationContext(),"Enter a number", Toast.LENGTH_LONG).show();
        //if there is exception, display message
       } catch (NumberFormatException e) {
           Toast.makeText(getApplicationContext(), "Please enter a number", Toast.LENGTH_LONG).show();
       }

    }
    //clear textview and edittext field
    public void clear(View v){
        e1.setText("");
        e2.setText("");
        t1.setText("");
        t2.setText("");
    }
}