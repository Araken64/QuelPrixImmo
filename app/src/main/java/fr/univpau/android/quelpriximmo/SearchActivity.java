package fr.univpau.android.quelpriximmo;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import fr.univpau.android.quelpriximmo.listeners.ButtonSearchListener;
import fr.univpau.android.quelpriximmo.listeners.ImageButtonListener;

import static fr.univpau.android.quelpriximmo.PositionManager.getPositionViaGPS;

public class SearchActivity extends AppCompatActivity {
    public static double latitude;
    public static double longitude;
    public static int range = 500;
    ImageButton button_param;
    Button button_search;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_search);
        requestPermissions(new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        button_param = findViewById(R.id.imageButton_param);
        button_search = findViewById(R.id.button_search);
        button_search.setOnClickListener(new ButtonSearchListener(this));

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        range = pref.getInt("distance", 500);
        Log.i("DIST", String.valueOf(range));

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Location position = getPositionViaGPS(this);
                Log.i("GPS", "Latitude = " + position.getLatitude());
                Log.i("GPS", "Longitude" + position.getLongitude());
                latitude = position.getLatitude();
                longitude = position.getLongitude();
            } else {
                Toast.makeText(this, "Permission denied to use location", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private boolean hasAlreadyPermission(String permission) {
        int result = ContextCompat.checkSelfPermission(this, permission);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}