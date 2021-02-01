package fr.univpau.android.quelpriximmo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        String type_local = this.getIntent().getStringExtra("type_local");
        String date_nature = this.getIntent().getStringExtra("date_nature");
        String nombre_pieces_principales = this.getIntent().getStringExtra("nombre_pieces_principales");

        this.findViewById(R.id.mutation_list);
    }
}