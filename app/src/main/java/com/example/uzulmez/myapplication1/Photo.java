package com.example.uzulmez.myapplication1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Uzulmez on 2/13/2018.
 */

public class Photo extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private String photo_url;
    private ImageView img, imagedownload,imgflickr;
    private   FeedItem Fitem;

    private boolean first=true;
    private ProgressBar progressBar;
    private TextView textview;
    private Shark shark;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.photolayout);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    this,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        textview = (TextView) findViewById(R.id.textView);
        imagedownload = (ImageView) findViewById(R.id.imagedownload);



        textview.setText("");
        img = (ImageView) findViewById(R.id.imageView) ;

        imgflickr = (ImageView) findViewById(R.id.imgflickr);
        Bundle extra = getIntent().getBundleExtra("extra");
         Fitem = (FeedItem) extra.getSerializable("feeditem");
         shark = new Shark();
         shark.setID(Fitem.getID());

        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getInfo&api_key=949e98778755d1982f537d56236bbb42&photo_id="+shark.getID() +"&format=json&nojsoncallback=1";

        new DownloadTask().execute(url);



    Showphoto(Fitem.getHQ_Thumbnail());

        imagedownload.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             savetostorage();
         }
     });

        imgflickr.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             Intent i = new Intent(Intent.ACTION_VIEW);
             i.setData(Uri.parse(shark.getFlickerLink() ));
             startActivity(i);
         }
     });


    }




    public class DownloadTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Integer doInBackground(String... params) {
            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                if (shark.getContent().length()>100)
                textview.setText( (shark.getContent()).substring(1,100) +"..." );
                else
                    textview.setText(shark.getContent());
            } else {
                Toast.makeText(getBaseContext(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            JSONObject sh = response.getJSONObject("photo");
            JSONObject desc = sh.getJSONObject("description");
            JSONArray url = sh.getJSONObject("urls").getJSONArray("url");

            shark.setContent(desc.optString("_content"));


            shark.setFlickerLink( url.optJSONObject(0).optString("_content") );

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void savetostorage() {


        Bitmap  finalBitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/camera/";
        File myDir = new File(root );

        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String fname = "Image-" + n + ".jpg";
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {

            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            Toast.makeText(getBaseContext(),"Picture Saved",Toast.LENGTH_SHORT);
        }
        catch (Exception e) {
          Toast.makeText(this,"Couldnt Saved!!!!",Toast.LENGTH_SHORT);
            e.printStackTrace();
        }

        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
}

    private void Showphoto(String url) {
        photo_url = url;
        progressBar.setVisibility(View.VISIBLE);
        if (photo_url!="") {
            Picasso.with(getBaseContext())
                    .load(photo_url)
                    .error(R.drawable.placeholder)
                    .into(img, new Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onError() {
                                    progressBar.setVisibility(View.INVISIBLE);
                                }
                            }
                    );}


    }


}