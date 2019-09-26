package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class PaymentUpdate extends AppCompatActivity {

    Button buttonPay;
    EditText date,houseId,phone,paymentID;
    DBConnect mydb;

    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_update);

        buttonPay = findViewById(R.id.btnUp);
        date = findViewById(R.id.date);
        houseId = findViewById(R.id.houseID);
        phone = findViewById(R.id.phone);
        paymentID = findViewById(R.id.PaymentID);
        mydb = new DBConnect(this);
        updatePay();


        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(PaymentUpdate.this,
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


    public  void updatePay()
    {
        buttonPay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = mydb.updateDataPay(
                                paymentID.getText().toString(),
                                houseId.getText().toString(),
                                date.getText().toString(),
                                phone.getText().toString()
                        );

                        if(isUpdated == true)
                        {
                            Toast.makeText(PaymentUpdate.this, "Update successful", Toast.LENGTH_SHORT).show();
                            openActivity();
                        }
                        else
                        {
                            Toast.makeText(PaymentUpdate.this, "Not successful", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void openActivity() {
        Intent intent = new Intent(this , PaymentList.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this , Home.class);
        startActivity(intent);
    }
}
