package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView txt1,txt2;
    DBConnect mydb;
    Button b2,btnUp,btnUpPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b2 = (Button) findViewById(R.id.btnAll);
        btnUp = (Button) findViewById(R.id.btnUp1);
        btnUpPay = findViewById(R.id.btnUpPay);


        mydb = new DBConnect(this);

        txt1 = findViewById(R.id.credit);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFirstActivity();
            }
        });

        txt2 = findViewById(R.id.paypal);
        txt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSecondActivity();
            }
        });

        btnUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivityThree();
                    }
                }
        );

        btnUpPay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivityFour();
                    }
                }
        );




    }

    private void openActivityFour() {
        Intent intent = new Intent(this, PaymentUpdate.class);
        startActivity(intent);
    }

    private void openActivityThree() {
        Intent intent = new Intent(this, PaymentList.class);
        startActivity(intent);
    }


    public void openFirstActivity(){
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }
    public void openSecondActivity(){
        Intent intent = new Intent(this, PayPalPay.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this , Home.class);
        startActivity(intent);
    }
}
