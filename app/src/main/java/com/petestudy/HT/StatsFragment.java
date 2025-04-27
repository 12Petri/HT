package com.petestudy.HT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;

import com.petestudy.myapplication.R;

public class StatsFragment extends Fragment {

    private TextView textStats;

    public StatsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_stats, container, false);

        textStats = rootView.findViewById(R.id.textStats);
        updateStatsView();

        return rootView;
    }

    private void updateStatsView() {
        Storage storage = Storage.getInstance();
        Player player = Player.getInstance();

        String statsText = "🏆 Turnausvoitot: " + storage.getTournamentWins() +
                "\n⚔️ Käytyjä taisteluita: " + storage.getTotalBattles() +
                "\n✨ Kerätty XP: " + storage.getTotalXP() + //Not working completely correctly
                "\n💰 Kolikot: " + player.getCoins();

        textStats.setText(statsText);
    }
}