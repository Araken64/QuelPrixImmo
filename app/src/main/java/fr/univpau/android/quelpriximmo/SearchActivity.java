package fr.univpau.android.quelpriximmo;

import android.os.AsyncTask;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import fr.univpau.android.quelpriximmo.listeners.ButtonSearchListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import static fr.univpau.android.quelpriximmo.PositionManager.getPositionViaGPS;

public class SearchActivity extends AppCompatActivity {
    public static double latitude;
    public static double longitude;
    public static int range = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Button button_search = (Button) findViewById(R.id.button_search);
        button_search.setOnClickListener(new ButtonSearchListener(this));


        Location position = getPositionViaGPS(this);
        Log.i("GPS","Latitude = " + position.getLatitude());
        Log.i("GPS", "Longitude" + position.getLongitude());
        latitude = position.getLatitude();
        longitude = position.getLongitude();
        URL url = null;
        try {
            url = new URL("https://api.cquest.org/dvf?lat=" + latitude + "&lon=" + longitude + "&dist=" + range);
            AsyncTask<URL, Void, JSONObject> task = new AsyncDataTask().execute(url);
            JSONObject datas = task.get();
            Log.i("RES", datas.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}