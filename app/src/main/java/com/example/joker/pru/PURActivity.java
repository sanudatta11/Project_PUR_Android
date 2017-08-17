package com.example.joker.pru;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

public class PURActivity extends AppCompatActivity {

    private static final String TAG = PURActivity.class.getSimpleName();

    private String imageData;

    private ProgressDialog pd;

    public String hof_name, hof_aadhar, hof_dob, hof_bhamasa, hof_mid, hof_gender,
            spouse_name, spouse_aadhar, spouse_dob, spouse_mid, spouse_gender, date, mobile, hospital, doctor;

    public TextView mother_name, mother_aadhar, mother_dob, mother_bhamasa, mother_mid, mother_gender,
            father_name, father_aadhar, father_dob, father_mid, father_gender, datetv, mobiletv, hospitaltv, doctortv;

    private ImageView imageView;

    private String pur=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pur);


        Intent intent = getIntent();
        String pur = intent.getStringExtra("pur");

        Log.d(TAG ,"pur"+pur);

        mother_name = (TextView) findViewById(R.id.mother_name_textView);
        mother_aadhar = (TextView) findViewById(R.id.mother_aadhar);
        mother_dob = (TextView) findViewById(R.id.mother_DOB);
        mother_bhamasa = (TextView) findViewById(R.id.mother_bhamasha);
        mother_mid = (TextView) findViewById(R.id.mother_mid);
        mother_gender = (TextView) findViewById(R.id.mother_gender);

        father_name = (TextView) findViewById(R.id.father_name_textView);
        father_aadhar = (TextView) findViewById(R.id.father_aadhar);
        father_dob = (TextView) findViewById(R.id.father_DOB);
        father_mid = (TextView) findViewById(R.id.father_mid);
        father_gender = (TextView) findViewById(R.id.father_gender);

        datetv = (TextView) findViewById(R.id.child_date);
        mobiletv = (TextView) findViewById(R.id.child_mobile);
        hospitaltv = (TextView) findViewById(R.id.child_hospital);
        doctortv = (TextView) findViewById(R.id.child_doctor);

        imageView = (ImageView) findViewById(R.id.mother_image);
        String url = "http://192.168.43.18:8000/android/"+pur+"/";
        new AsyncTask().execute(url);
    }


    //Class to make AsyncTask()
    public class AsyncTask extends android.os.AsyncTask<String, Void, String> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(PURActivity.this);
            pd.setTitle("Fetching");
            pd.setMessage("Please wait...");
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String response = HttpHandler.makeServiceCall(url);
            //Log.d(TAG, " " + response);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ParseJSON(s);
            pd.dismiss();

        }
    }

    public void ParseJSON(String response) {

        try {
            JSONObject details = new JSONObject(response);

            //for female
            hof_name = details.getString("hof_name");
            hof_aadhar = details.getString("hof_aadhar");
            hof_dob = details.getString("hof_dob");
            hof_bhamasa = details.getString("hof_bhamasa");
            hof_mid = details.getString("hof_mid");
            hof_gender = details.getString("hof_gender");

            //for male
            spouse_name = details.getString("spouse_name");
            spouse_aadhar = details.getString("spouse_aadhar");
            spouse_dob = details.getString("spouse_dob");
            spouse_mid = details.getString("spouse_mid");
            spouse_gender = details.getString("spouse_gender");

            date = details.getString("date");
           // mobile = details.getString("mobile");
            hospital = details.getString("hospital");
            doctor = details.getString("doctor");
            imageData = details.getString("hof_picture");

            //setting value to textView
            mother_name.setText(hof_name);
            mother_aadhar.setText(hof_aadhar);
            mother_dob.setText(hof_dob);
            mother_bhamasa.setText(hof_bhamasa);
            mother_mid.setText(hof_mid);
            mother_gender.setText(hof_gender);

            father_name.setText(spouse_name);
            father_aadhar.setText(spouse_aadhar);
            father_dob.setText(spouse_dob);
            father_mid.setText(spouse_mid);
            father_gender.setText(spouse_gender);


            datetv.setText(date);
            //mobiletv.setText(mobile);
            hospitaltv.setText(hospital);
            doctortv.setText(doctor);

            byte[] imageAsBytes = Base64.decode(imageData, Base64.DEFAULT);
            Log.d(TAG, "check" + imageAsBytes);
            Glide.with(PURActivity.this)
                    .load(imageAsBytes)
                    .asBitmap()
                    .into(imageView);


        } catch (JSONException e) {
            Log.e(TAG, "JSONException " + e);
        }

    }

}
