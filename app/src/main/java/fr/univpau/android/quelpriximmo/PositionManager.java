package fr.univpau.android.quelpriximmo;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

public class PositionManager {

    public static Location getPositionViaGPS(Context context) throws SecurityException {
        LocationManager lm;
        Location position = null;
        String provider;

        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        provider = lm.getBestProvider(criteria, true);
        if (provider != null) {
            position = lm.getLastKnownLocation(provider);
        }
        return position;
    }
}
