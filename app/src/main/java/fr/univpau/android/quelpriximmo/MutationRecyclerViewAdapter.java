package fr.univpau.android.quelpriximmo;

import android.widget.ImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fr.univpau.android.quelpriximmo.MutationContent.MutationItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MutationItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MutationRecyclerViewAdapter extends RecyclerView.Adapter<MutationRecyclerViewAdapter.ViewHolder> {

    private final List<MutationItem> mutation_values;

    public MutationRecyclerViewAdapter(List<MutationItem> items) {
        mutation_values = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.mutation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mutation_item = mutation_values.get(position);

        String str_valeur_nature = holder.mutation_item.valeur_fonciere + " / " + holder.mutation_item.nature_mutation;
        String str_adresse = holder.mutation_item.numero_voie + holder.mutation_item.suffixe_numero + " " + holder.mutation_item.type_voie + " " + holder.mutation_item.voie;
        MyGoodRecyclerViewAdapter adapter = new MyGoodRecyclerViewAdapter(holder.mutation_item.good_list);

        holder.valeur_nature.setText(str_valeur_nature);
        holder.date_mutation.setText(holder.mutation_item.date_mutation);
        holder.adresse.setText(str_adresse);
        holder.image_mutation.setImageResource(R.drawable.crayon);
        holder.good_list.setLayoutManager(new LinearLayoutManager(holder.good_list.getContext()));
        holder.good_list.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mutation_values.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image_mutation;
        public final TextView valeur_nature;
        public final TextView date_mutation;
        public final TextView adresse;
        public final RecyclerView good_list;
        public MutationItem mutation_item;

        public ViewHolder(View view) {
            super(view);
            image_mutation = (ImageView) view.findViewById(R.id.image_mutation);
            valeur_nature = (TextView) view.findViewById(R.id.valeur_nature);
            date_mutation = (TextView) view.findViewById(R.id.date_mutation);
            adresse = (TextView) view.findViewById(R.id.adresse);
            good_list = (RecyclerView) view.findViewById(R.id.good_list);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + valeur_nature.getText() + "'";
        }
    }
}