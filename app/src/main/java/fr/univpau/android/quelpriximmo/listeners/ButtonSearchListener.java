package fr.univpau.android.quelpriximmo.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import fr.univpau.android.quelpriximmo.R;
import fr.univpau.android.quelpriximmo.ResultsActivity;
import fr.univpau.android.quelpriximmo.SearchActivity;

public class ButtonSearchListener implements View.OnClickListener {
    private Context context;
    public ButtonSearchListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent results_intent = new Intent(context, ResultsActivity.class);
        View root = (View) v.getRootView();
        if(root != null) {
            Spinner type_local = (Spinner) root.findViewById(R.id.type_local);
            String str_type_local = type_local.getSelectedItem().toString();

            Spinner nature_mutation = (Spinner) root.findViewById(R.id.nature_mutation);
            String str_nature_mutation = nature_mutation.getSelectedItem().toString();

            EditText nombre_pieces_principales = (EditText) root.findViewById(R.id.nombre_pieces_principales);
            String str_nombre_pieces_principales = nombre_pieces_principales.getText().toString();

            String strUrl = "https://api.cquest.org/dvf?lat=" + SearchActivity.latitude + "&lon=" + SearchActivity.longitude + "&dist=" + SearchActivity.range + "&nature_mutation=" + str_nature_mutation;
            if (!str_type_local.equals(context.getResources().getString(R.string.spinner_choice_tout)))
                strUrl += "&type_local=" + str_type_local;

            results_intent.putExtra("strUrl", strUrl);
            results_intent.putExtra("nombre_pieces_principales", str_nombre_pieces_principales);
        }
        context.startActivity(results_intent);
    }
}
