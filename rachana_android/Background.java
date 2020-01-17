package com.example.librarysystem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.Buffer;

public class Background extends AsyncTask<String, Void, String> {

    AlertDialog dialog;
    Context context;
    public Boolean login = false;
    public Background(Context context)
    {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = new AlertDialog.Builder(context).create();
        dialog.setTitle("Login Status");
    }
    @Override
    protected void onPostExecute(String s) {

        //Toast.makeText(context, "You've reached beginning point", Toast.LENGTH_SHORT).show();
        dialog.setMessage(s);
        dialog.show();
        Toast.makeText(context, s + " this ", Toast.LENGTH_SHORT).show();
        Log.d("Background", "here in post");
//        if(s.contains("login successful"))
//        {
//            Intent intent_name = new Intent();
//            intent_name.setClass(context.getApplicationContext(),NavigateBooks.class);
//            context.startActivity(intent_name);
//        }
    }

    @Override
    protected String doInBackground(String... voids) {
        String results = "";
        String user = voids[0];
        String pass = voids[1];

        Log.d("Background", user+ " user and "+pass+ " passsword");
        String conn = "https://192.168.137.1/index.php";

        Log.d("Background", "here 1");

        try {
            URL url = new URL(conn);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);

            Log.d("Background", "here 6");

            OutputStream outputStream = httpURLConnection.getOutputStream();
            Log.d("Background", "here 3");
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            Log.d("Background", "here 2");

            String data = URLEncoder.encode("sname","UTF-8")+"="+URLEncoder.encode(user,"UTF-8")
                    +"&&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");

            Log.d("Background", "here 2.5");

            bufferedWriter.write(data);

            Log.d("Background", "here 3");

            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            Log.d("Background", "here 4");

            InputStream ips = httpURLConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(ips,"ISO-8859-1"));
            String line ="";
            while ((line = reader.readLine()) != null)
            {
                results += line;

                Log.d("Background", "results: "+results);

            }
            reader.close();
            ips.close();
            httpURLConnection.disconnect();
            return results;

        } catch (MalformedURLException e) {
            results = e.getMessage();
            Log.d("Background", results);
        } catch (IOException e) {
            results = e.getMessage();
        }
        return null;
    }
}
