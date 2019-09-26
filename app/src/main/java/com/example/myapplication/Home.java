package com.example.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {

    DBConnect mydb;
    Button Pay,PayUp,PayList,Card,CardUp,CardDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mydb = new DBConnect(this);

        Pay = findViewById(R.id.buttonPayment);
        PayUp = findViewById(R.id.buttonPayUpdate);
        PayList = findViewById(R.id.buttonPayList);
        Card = findViewById(R.id.buttonCard);
        CardUp = findViewById(R.id.buttonCardUpdate);
        CardDetails = findViewById(R.id.buttonCardDetails);

        AllData();

        Pay.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity();
                    }
                }
        );

        PayUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity1();
                    }
                }
        );

        PayList.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity2();
                    }
                }
        );

        Card.setOnClickListener(
                new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                openActivity3();
                            }
                        }
                );
        CardUp.setOnClickListener(
                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        openActivity4();
                                    }
                                }
                        );

    }

    private void openActivity4() {
        Intent intent = new Intent(this,CardUpdate.class);
        startActivity(intent);
    }

    private void openActivity3() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    private void openActivity2() {
        Intent intent = new Intent(this,PaymentList.class);
        startActivity(intent);
    }

    private void openActivity1() {
        Intent intent = new Intent(this,PaymentUpdate.class);
        startActivity(intent);
    }

    private void openActivity() {
        Intent intent = new Intent(this,Payment.class);
        startActivity(intent);
    }

    public void AllData()
    {
        CardDetails.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res= mydb.getAllData();
                        if(res.getCount() == 0)
                        {
                            ShowMessage("Error","Nothing Found");
                            //show message
                        }
                        else
                        {
                            StringBuffer buffer = new StringBuffer();
                            while (res.moveToNext())
                            {
                                buffer.append("CID :" +res.getString(0)+"\n");
                                buffer.append("CNumber :" +res.getString(1)+"\n");
                                buffer.append("CName :" +res.getString(2)+"\n");
                                buffer.append("CKey :" +res.getString(3)+"\n\n");

                            }

                            ShowMessage("Payments",buffer.toString());
                        }
                    }
                }
        );
    }

    public void ShowMessage(String title, String message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();

        }



}
