package ca.hwlo.mrkook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Henry on 2015-07-02.
 */
public class Food2ForkAPI extends AsyncTask<String, String, String> {
//
//    private ProgressDialog progressDialog = new ProgressDialog();
    InputStream inputStream = null;
    String result = "";

    Exception exception = null;

    private Context mContext;

    public Food2ForkAPI(Context context){
        mContext = context;
    }

    @Override
    protected String doInBackground(String... urls) {
        try{
            APIHELPER apihelp = new APIHELPER();
            return apihelp.readJSON(urls[0]);
        }catch(IOException e){
            exception = e;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        //super.onPostExecute(s);
        Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();
        try {
            JSONObject jsonObject = new JSONObject(s);
            Toast.makeText(mContext, jsonObject.getString("title"), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            Log.d("Food Error", e.getLocalizedMessage());
        }
    }

    //    protected void onPreExecute(){
//        progressDialog.setMessage("Downloading recipe...");
//        progressDialog.show();
//        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                Food2ForkAPI.this.cancel(true);
//            }
//        });
//    }

//    @Override
//    protected String doInBackground(String... params) {
//
//        String apiKEY = "612e30069cbd11c599326ae490d735d5";
//        String apiURL = "http://food2fork.com/api/search?key=" + apiKEY + "&q=fish";
//
//        URL url;
//        HttpURLConnection conn;
//
//
//        try {
//            url = new URL(apiURL);
//            conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(10000 /*milliseconds*/);
//            conn.setConnectTimeout(15000 /* milliseconds */);
//            conn.setRequestMethod("GET");
//
//            //open
//            conn.connect();
//
//            //setup send
//            InputStream is = conn.getInputStream();
//            BufferedReader bReader = new BufferedReader(new InputStreamReader(is), 8);
//            StringBuilder sBuilder = new StringBuilder();
//
//            String line;
//            while((line = bReader.readLine()) != null){
//                sBuilder.append(line + "\n");
//            }
//
//            is.close();
//            result = sBuilder.toString();
//
//            Log.d("LOL", result);
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (ProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
