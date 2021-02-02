package fr.univpau.android.quelpriximmo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fr.univpau.android.quelpriximmo.GoodContent.GoodItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GoodItem}.
 * TODO: Replace the implementation with code for your data type.
 */

public class MyGoodRecyclerViewAdapter extends RecyclerView.Adapter<MyGoodRecyclerViewAdapter.ViewHolder> {

    private final List<GoodItem> good_values;

    public MyGoodRecyclerViewAdapter(List<GoodItem> items) {
        good_values = items;
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.good, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.good_item = good_values.get(position);
        holder.surface.setText(holder.good_item.surface + " m²");
        String str_local_pieces = holder.good_item.type_local;
        if (holder.good_item.type_local.equals("Appartement")) {
            holder.image_good.setImageResource(R.drawable.appartement);
            str_local_pieces += " / " + holder.good_item.nb_pieces_principales + " p";
        } else if (holder.good_item.type_local.equals("Maison")) {
            holder.image_good.setImageResource(R.drawable.maison);
            str_local_pieces += " / " + holder.good_item.nb_pieces_principales + " p";
        } else
            holder.image_good.setImageResource(R.drawable.arbre);
        holder.local_pieces.setText(str_local_pieces);
    }

    @Override
    public int getItemCount() {
        return good_values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView image_good;
        public final TextView surface;
        public final TextView local_pieces;
        public GoodItem good_item;

        public ViewHolder(View view) {
            super(view);
            image_good = (ImageView) view.findViewById(R.id.imageView2);
            surface = (TextView) view.findViewById(R.id.surface);
            local_pieces = (TextView) view.findViewById(R.id.local_pieces);
        }
    }
}