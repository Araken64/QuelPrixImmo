package fr.univpau.android.quelpriximmo;

import android.widget.ImageView;
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
            .inflate(R.layout.fragment_mutation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mutation_item = mutation_values.get(position);
        String str_valeur_nature;
        // holder.valeur_nature.setText(mutation_values.get(position));
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