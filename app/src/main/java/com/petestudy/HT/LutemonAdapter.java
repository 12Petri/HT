package com.petestudy.HT;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.petestudy.myapplication.R;

import java.util.List;

public class LutemonAdapter extends RecyclerView.Adapter<LutemonAdapter.ViewHolder> {

    private final Context context;
    private final List<Lutemon> lutemons;
    private final OnLutemonClickListener clickListener;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public interface OnLutemonClickListener {
        void onLutemonClick(int position);
    }

    public LutemonAdapter(Context context, List<Lutemon> lutemons, OnLutemonClickListener clickListener) {
        this.context = context;
        this.lutemons = lutemons;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_lutemon, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Lutemon l = lutemons.get(position);

        holder.nameText.setText(l.getName() + " (" + l.getColor() + ")");
        holder.statsText.setText("Lv: " + l.getLevel() + ", XP: " + l.getExperience() + ", HP: " + l.getHealth() + "/" + l.getMaxHealth() + "\n" +
                        "Atk: " + l.getAttackStat() + ", Def: " + l.getDefenceStat()
        );

        String imageName = l.getImageName();
        int resId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        holder.image.setImageResource(resId != 0 ? resId : R.drawable.ic_launcher_foreground);

        if (position == selectedPosition) {
            holder.card.setCardBackgroundColor(0xFFDDFFDD);
        } else if (Storage.getInstance().getSelectedForTournament().contains(l)) {
            holder.card.setCardBackgroundColor(0xFFCCE5FF);
        } else {
            holder.card.setCardBackgroundColor(0xFFFFFFFF);
        }

        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            selectedPosition = pos;
            if (clickListener != null) {
                clickListener.onLutemonClick(pos);
            }
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return lutemons.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView nameText, statsText;
        CardView card;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.LutemonImage);
            nameText = itemView.findViewById(R.id.txtLutemonName);
            statsText = itemView.findViewById(R.id.txtLutemonStats);
            card = (CardView) itemView;
        }
    }
}