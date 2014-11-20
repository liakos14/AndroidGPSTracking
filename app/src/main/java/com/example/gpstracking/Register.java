package com.example.gpstracking;

/**
 * Created by liakos on 18/11/2014.
 */


        import java.util.ArrayList;
        import java.util.List;
        import org.apache.http.NameValuePair;
        import org.apache.http.message.BasicNameValuePair;
        import org.json.JSONException;
        import org.json.JSONObject;
        import android.app.Activity;
        import android.app.ProgressDialog;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.content.Intent;

public class Register extends Activity {

    // Progress Dialog
//    private ProgressDialog pDialog;

    // JSON parser class
    JSONParser jsonParser = new JSONParser();



    private static final String LOGIN_URL = "http://192.168.1.65/netplace/gpstest/read.php";

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        new CreateUser().execute();
    }


    class CreateUser extends AsyncTask<String, String, String> {


      //  boolean failure = false;



        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub
            // Check for success tag
            int success;
            Intent intent = getIntent();
            double username = intent.getDoubleExtra("lat",0);
            double password = intent.getDoubleExtra("lng",0);


            try {
                // Building Parameters
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username",Double.toString(username)));
                params.add(new BasicNameValuePair("password", Double.toString(password)));

                Log.d("request!", "starting");

                //Posting user data to script
                JSONObject json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "POST", params);


                // full json response
                Log.d("Login attempt", json.toString());

                // json success element
                success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    Log.d("User Created!", json.toString());

                   finish();
                  //  return json.getString(TAG_MESSAGE);
                }else{
                    Log.d("Login Failure!", json.getString(TAG_MESSAGE));
                  //  return json.getString(TAG_MESSAGE);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

    }

}