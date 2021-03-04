package fr.univpau.android.quelpriximmo;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import fr.univpau.android.quelpriximmo.listeners.ButtonSearchListener;
import fr.univpau.android.quelpriximmo.listeners.ImageButtonListener;

public class SearchActivity extends AppCompatActivity {
    public static double latitude;
    public static double longitude;
    public static int range = 500;
    ImageButton button_param;
    Button button_search;

    private LocationListener locationListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        button_param = findViewById(R.id.imageButton_param);
        button_search = findViewById(R.id.button_search);
        button_search.setOnClickListener(new ButtonSearchListener(this));

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        range = pref.getInt("distance", 500);
        Log.i("DIST", String.valueOf(range));

        button_param.setOnClickListener(new ImageButtonListener(this));
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                latitude=location.getLatitude();
                longitude=location.getLongitude();
                Log.i("GPS", "Latitude = " + latitude);
                Log.i("GPS", "Longitude" + longitude);
            }
            @Override
            public void onProviderDisabled(@NonNull String provider) { launchAlertLocation(); }
            @Override
            public void onProviderEnabled(@NonNull String provider) {}
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        button_param = findViewById(R.id.imageButton_param);
        button_param.setClickable(true);
        button_search = findViewById(R.id.button_search);
        button_search.setClickable(true);

        LocationManager lm =(LocationManager) getSystemService(LOCATION_SERVICE);
        if(!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){ launchAlertLocation(); }
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String provider = lm.getBestProvider(criteria, true);
        // lm.requestLocationUpdates(provider, 0, 0, locationListener);
        lm.requestSingleUpdate(provider, locationListener, null);
    }

    private void launchAlertLocation() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(this);
        alertDialog.setTitle("Activer la géolocalisation");
        alertDialog.setMessage("Le service de géolocalisation n'est pas activé. Veuillez l'activer dans les paramètres.");
        alertDialog.setPositiveButton("Paramètres", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Fermer", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        AlertDialog alert=alertDialog.create();
        alert.show();
    }
}