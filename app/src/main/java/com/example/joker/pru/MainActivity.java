package com.example.joker.pru;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.LocaleDisplayNames;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pd;
    private CardView purDetails, certificate, hospital;
    private static String responseJSON = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        purDetails = (CardView) findViewById(R.id.cardView_PURDetails);
        certificate = (CardView) findViewById(R.id.cardView_certificates);
        hospital = (CardView) findViewById(R.id.cardView_hospital);


        //when user clicks of PUR Details.
        purDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Uri.Builder builder = new Uri.Builder();
//                builder.scheme("https")
//                        .authority("www.google.com");
//                String url = builder.build().toString();
//
//                new AsyncTask().execute(url);

                //get prompt.xml


                View promptView = LayoutInflater.from(MainActivity.this).inflate(R.layout.prompt, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(promptView);

                final EditText userInput = (EditText) promptView.findViewById(R.id.editTextDialogUserInput);

                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(MainActivity.this, PURActivity.class);
                                String pur = userInput.getText().toString();
                                if (pur == null) {
                                    Toast.makeText(MainActivity.this, "Please input PUR no.", Toast.LENGTH_SHORT).show();
                                } else {

                                    intent.putExtra("pur", userInput.getText().toString());
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("CANCLE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Cancled", Toast.LENGTH_SHORT).show();

                            }
                        });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                // show it
                alertDialog.show();

            }
        });


    }


    //to save text In pdf format and open it in Implica


}
