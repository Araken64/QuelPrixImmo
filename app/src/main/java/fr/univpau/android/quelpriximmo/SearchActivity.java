package fr.univpau.android.quelpriximmo;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;

import fr.univpau.android.quelpriximmo.listeners.ButtonSearchListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Log.i("GPS", "Latitude = " + position.getLatitude());
        Log.i("GPS", "Longitude" + position.getLongitude());
        latitude = position.getLatitude();
        longitude = position.getLongitude();
    }
}