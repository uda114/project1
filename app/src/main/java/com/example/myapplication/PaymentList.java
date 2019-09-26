package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class PaymentList extends AppCompatActivity {

    ListView listView;
    SQLiteDatabase sqLiteDatabase;
    DBConnect dBhelper;
    Cursor cursor;
    ListPaymentAdaptor listCreditorAdaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        listView = findViewById(R.id.list_payment);

        dBhelper = new DBConnect(getApplicationContext());
        sqLiteDatabase = dBhelper.getReadableDatabase();
        listCreditorAdaptor = new ListPaymentAdaptor(getApplicationContext(),R.layout.crow_layout);
        listView.setAdapter(listCreditorAdaptor);

        cursor = dBhelper.getAllDataPay();

        if(cursor.moveToFirst()) {
            do{

                String PayID , HouseID , date, phone;
                PayID = cursor.getString(0);
                HouseID = cursor.getString(1);
                date = cursor.getString(2);
                phone = cursor.getString(3);

                PaymentProvoder paymentProvoder = new PaymentProvoder(PayID , HouseID , date, phone);

                listCreditorAdaptor.add(paymentProvoder);

            }while (cursor.moveToNext());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this , Home.class);
        startActivity(intent);
    }
}
