package fr.univpau.android.quelpriximmo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {
    int distance;
    SeekBar bar;
    TextView distance_display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.settings_activity);

        bar = (SeekBar) findViewById(R.id.distance_seekbar);
        distance_display = (TextView) findViewById(R.id.distance_display);
        bar.setMax(1500);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        distance = pref.getInt("distance", 500);
        distance_display.setText(String.valueOf(distance) + "m");
        bar.setProgress(distance - 500);

        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                           @Override
                                           public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                                               distance = bar.getProgress() + 500;
                                               distance_display.setText(String.valueOf(distance) + "m");
                                           }

                                           @Override
                                           public void onStartTrackingTouch(SeekBar seekBar) {

                                           }

                                           @Override
                                           public void onStopTrackingTouch(SeekBar seekBar) {
                                               distance = bar.getProgress() + 500;
                                               distance_display.setText(String.valueOf(distance) + "m");
                                           }
                                       }
        );

        Button button_valider = findViewById(R.id.button_valider);
        button_valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(view.getContext());
                SharedPreferences.Editor ed = pref.edit();
                ed.putInt("distance", distance);
                ed.apply();
                finish();
            }
        });
    }
}