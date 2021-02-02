package fr.univpau.android.quelpriximmo.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import fr.univpau.android.quelpriximmo.R;
import fr.univpau.android.quelpriximmo.ResultsActivity;
import fr.univpau.android.quelpriximmo.SearchActivity;
import fr.univpau.android.quelpriximmo.SettingsActivity;

public class ImageButtonListener implements View.OnClickListener {
    private Context context;
    public ImageButtonListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        ImageButton button_param = view.findViewById(R.id.imageButton_param);
        button_param.setClickable(false);
        Intent intent = new Intent(view.getContext(), SettingsActivity.class);
        view.getContext().startActivity(intent);
    }
}
