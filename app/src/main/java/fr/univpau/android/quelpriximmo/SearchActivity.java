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
    URL url = null;
    AsyncTask<URL, Void, JSONObject> task = null;

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

        // Button button_search = findViewById(R.id.button_search);
        /* button_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Spinner spinner_type_local = findViewById(R.id.type_local);
                String str_type_local = spinner_type_local.getSelectedItem().toString();
                Spinner spinner_nature_mutation = findViewById(R.id.nature_mutation);
                String str_nature_mutation = spinner_nature_mutation.getSelectedItem().toString();
                EditText et_nb_piece = findViewById(R.id.nombre_pieces_principales);
                String str_nb_piece = et_nb_piece.getText().toString();
                Log.i("TEST", str_nb_piece);
                try {
                    if (str_type_local.equals("Tout")) {
                        url = new URL("https://api.cquest.org/dvf?lat=" + latitude + "&lon=" + longitude + "&dist=" + range + "&nature_mutation=" + str_nature_mutation);
                    } else {
                        url = new URL("https://api.cquest.org/dvf?lat=" + latitude + "&lon=" + longitude + "&dist=" + range + "&nature_mutation=" + str_nature_mutation + "&type_local=" + str_type_local);
                    }
//                    TODO A faire en début de l'activité result avec un petit waiting screen
                    task = new AsyncDataTask().execute(url);
                    JSONObject datas = task.get();
                    JSONArray array_json_objects1 = (JSONArray) datas.get("features");
                    JSONArray array_json_filtre_piece = new JSONArray();
                    for (int i = 0; i < array_json_objects1.length() - 1; i++) {
                        JSONObject o = array_json_objects1.getJSONObject(i);
                        if (((JSONObject) o.get("properties")).has("type_local")) {
                            String type_local = ((JSONObject) o.get("properties")).get("type_local").toString();
                            if (type_local.equals("Maison") || type_local.equals("Appartement")) {
                                if (str_nb_piece.equals("")) {
                                    array_json_filtre_piece.put(o);
                                } else if (((JSONObject) o.get("properties")).get("nombre_pieces_principales").toString().equals(str_nb_piece)) {
                                    array_json_filtre_piece.put(o);
                                }
                            }
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }); */

    }
}