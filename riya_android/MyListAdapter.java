package com.example.libmannav;

import android.app.Activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AppCompatActivity;


public class MyListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] maintitle;
    private final String[] subtitle;
    private final String[] imgid;

    public class Image extends AppCompatActivity {



        public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(String... strings) {
                try {
                    URL url = new URL(strings[0]);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();

                    InputStream inputStream = connection.getInputStream();

                    Bitmap myBitmap = BitmapFactory.decodeStream(inputStream);

                    return myBitmap;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }


        public class DownloadTask extends AsyncTask<String, Void, String>{


            @Override
            protected String doInBackground(String... urls) {

                String result = "";
                URL url;
                HttpURLConnection urlConnection = null;

                try{

                    url = new URL(urls[0]);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    InputStream in = urlConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(in);

                    int data = reader.read();

                    while(data!= -1){

                        char current = (char) data;
                        result += current;
                        data = reader.read();
                    }

                    return result;

                } catch (Exception e){
                    e.printStackTrace();
                }

                return null;
            }
        }

        public Bitmap getImage(String imgId) {

            ImageDownloader imageDownloader = new ImageDownloader();
            Bitmap bitmap;

            try {
                bitmap =imageDownloader.execute(imgId).get();
                return bitmap;

            } catch (ExecutionException e) {
                e.printStackTrace();
                return null;

            } catch (InterruptedException e) {
                e.printStackTrace();
                return null;

            }

        }
}


    public MyListAdapter(Activity context, String[] maintitle,String[] subtitle, String[] imgid) {
        super(context, R.layout.mylist, maintitle);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.maintitle=maintitle;
        this.subtitle=subtitle;
        this.imgid=imgid;




    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.title);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.subtitle);

        titleText.setText(maintitle[position]);
        Image image = new Image();
        Bitmap bitmap = image.getImage(imgid[position]);
        imageView.setImageBitmap(bitmap);
        subtitleText.setText(subtitle[position]);

        return rowView;

    };
}

