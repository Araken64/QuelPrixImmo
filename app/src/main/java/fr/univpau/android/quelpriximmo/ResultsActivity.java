package fr.univpau.android.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class ResultsActivity extends AppCompatActivity {
    public static String nombre_pieces_principales;
    public static String strUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        nombre_pieces_principales = this.getIntent().getStringExtra("nombre_pieces_principales");
        strUrl = this.getIntent().getStringExtra("strUrl");
    }
}