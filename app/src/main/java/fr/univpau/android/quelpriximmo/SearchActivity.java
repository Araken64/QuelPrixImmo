package fr.univpau.android.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import static fr.univpau.android.quelpriximmo.PositionManager.getPositionViaGPS;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Location position = getPositionViaGPS(this);
        Log.i("GPS","Latitude = " + position.getLatitude());
        Log.i("GPS", "Longitude" + position.getLongitude());
        /*
        // http://api.cquest.org/dvf?lat=48.85&lon=2.35&dist=100
        URL url = new URL("https://openclassrooms.com/");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            readStream(in);
        } finally {
            urlConnection.disconnect();
        }
         */
    }
}