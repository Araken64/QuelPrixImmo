package fr.univpau.android.quelpriximmo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import fr.univpau.android.quelpriximmo.listeners.ButtonSearchListener;
import fr.univpau.android.quelpriximmo.listeners.ImageButtonListener;

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
    ImageButton button_param;
    Button button_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);

        button_param = findViewById(R.id.imageButton_param);
        button_search = findViewById(R.id.button_search);
        button_search.setOnClickListener(new ButtonSearchListener(this));

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        range = pref.getInt("distance", 500);
        Log.i("DIST", String.valueOf(range));
        Location position = getPositionViaGPS(this);
        Log.i("GPS", "Latitude = " + position.getLatitude());
        Log.i("GPS", "Longitude" + position.getLongitude());
        latitude = position.getLatitude();
        longitude = position.getLongitude();

        button_param.setOnClickListener(new ImageButtonListener(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        button_param = findViewById(R.id.imageButton_param);
        button_param.setClickable(true);
        button_search = findViewById(R.id.button_search);
        button_search.setClickable(true);
    }
}