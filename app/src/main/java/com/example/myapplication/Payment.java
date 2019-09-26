package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

public class Payment extends AppCompatActivity {

    Button buttonPay;
    EditText date, houseId, phone;
    DBConnect mydb;

    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        buttonPay = findViewById(R.id.btnPay);
        date = findViewById(R.id.date);
        houseId = findViewById(R.id.houseID);
        phone = findViewById(R.id.phone);
        mydb = new DBConnect(this);
        AddPayment();


        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(Payment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });


    }

    public void AddPayment()
    {
        buttonPay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       boolean isInserted =  mydb.insertDataPay(
                                houseId.getText().toString(),
                                date.getText().toString(),
                                phone.getText().toString());
                        if(isInserted == true)
                        {
                            openActivity();
                        }

                    }
                }
        );
    }

    private void openActivity() {
        Intent intent = new Intent(this , MainActivity.class);
        startActivity(intent);
    }



}
