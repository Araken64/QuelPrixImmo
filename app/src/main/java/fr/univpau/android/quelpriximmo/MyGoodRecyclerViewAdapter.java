package fr.univpau.android.quelpriximmo;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
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

    private final List<GoodItem> mValues;

    public MyGoodRecyclerViewAdapter(List<GoodItem> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_good, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if (mValues.get(position).type_local.equals("Appartement"))
            holder.mImage.setImageResource(R.drawable.appartement);
        else if (mValues.get(position).type_local.equals("Maison"))
            holder.mImage.setImageResource(R.drawable.maison);
        holder.mSurface.setText(mValues.get(position).surface_relle_bati);
        holder.mLocal_Pieces.setText(mValues.get(position).nb_pieces_principales);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final ImageView mImage;
        public final TextView mSurface;
        public final TextView mLocal_Pieces;

        public ViewHolder(View view) {
            super(view);
            mImage = (ImageView) view.findViewById(R.id.imageView2);
            mSurface = (TextView) view.findViewById(R.id.surface);
            mLocal_Pieces = (TextView) view.findViewById(R.id.local_pieces);
        }
    }
}