package fr.univpau.android.quelpriximmo.listeners;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import fr.univpau.android.quelpriximmo.R;
import fr.univpau.android.quelpriximmo.ResultsActivity;

public class ButtonSearchListener implements View.OnClickListener {
    private Context context;
    public ButtonSearchListener(Context context) {
        this.context = context;
    }

    @Override
    public void onClick(View v) {
        Intent results_intent = new Intent(context, ResultsActivity.class);
        View root = /*(View)*/ v.getRootView();
        if(root != null) {
//            Spinner type_local = (Spinner) root.findViewById(R.id.type_local);
//            String str_type_local = type_local.getSelectedItem().toString();
//
//            Spinner nature_mutation = (Spinner) root.findViewById(R.id.valeur_nature);
//            String str_nature_mutation = nature_mutation.getSelectedItem().toString();
//
//            EditText nombre_pieces_principales = (EditText) root.findViewById(R.id.nombre_pieces_principales);
//            String str_nombre_pieces_principales = nombre_pieces_principales.toString(); */
//
//
//            results_intent.putExtra("type_local", str_type_local);
//            results_intent.putExtra("nature_mutation", str_nature_mutation);
//            results_intent.putExtra("nombre_pieces_principales", str_nombre_pieces_principales);
        }
        context.startActivity(results_intent);
    }
}
