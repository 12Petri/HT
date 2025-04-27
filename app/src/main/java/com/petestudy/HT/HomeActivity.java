package com.petestudy.HT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.petestudy.myapplication.R;

public class HomeActivity extends AppCompatActivity {

    private static HomeActivity instance;
    private RecyclerView homeRecyclerView;
    private LutemonAdapter adapter;
    private int selectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_home);

        homeRecyclerView = findViewById(R.id.recyclerViewHomeLutemons);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new LutemonAdapter(this, Storage.getInstance().getLutemons(), pos -> selectedPosition = pos);
        homeRecyclerView.setAdapter(adapter);

        updateSelectedInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
        updateSelectedInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    private void refreshList() {
        if (adapter == null) {
            adapter = new LutemonAdapter(this, Storage.getInstance().getLutemons(), pos -> selectedPosition = pos);
            homeRecyclerView.setAdapter(adapter);
        } else {
            adapter = new LutemonAdapter(this, Storage.getInstance().getLutemons(), pos -> selectedPosition = pos);
            homeRecyclerView.setAdapter(adapter);
        }
        updateSelectedInfo();
    }

    private void updateSelectedInfo() {
        TextView selectedInfo = findViewById(R.id.textSelectedTournamentCount);
        selectedInfo.setText("Valittu turnaukseen: " + Storage.getInstance().getSelectedForTournament().size() + "/3");
    }

    public void onSellLutemon(View view) {
        if (selectedPosition >= 0 && selectedPosition < Storage.getInstance().getLutemons().size()) {
            Lutemon l = Storage.getInstance().getLutemons().get(selectedPosition);
            int price = l.getPrice() / 2;
            Player.getInstance().addCoins(price);
            Storage.getInstance().removeLutemon(selectedPosition);
            selectedPosition = -1;
            refreshList();
        }
    }

    public void selectForTournament(View v) {
        if (selectedPosition < 0 || selectedPosition >= Storage.getInstance().getLutemons().size()) {
            return;
        }

        Lutemon l = Storage.getInstance().getLutemons().get(selectedPosition);

        if (Storage.getInstance().getSelectedForTournament().contains(l)) {
            Storage.getInstance().removeFromTournamentSelection(l);
        } else {
            if (Storage.getInstance().getSelectedForTournament().size() >= 3) {
                return;
            }
            Storage.getInstance().addToTournamentSelection(l);
        }

        updateSelectedInfo();
    }

    public void startTraining(View v) {
        if (selectedPosition < 0 || selectedPosition >= Storage.getInstance().getLutemons().size()) {
            return;
        }

        Lutemon l = Storage.getInstance().getLutemons().get(selectedPosition);

        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra("myLutemon", l);
        intent.putExtra("isTournament", false);
        startActivity(intent);

        selectedPosition = -1;
        adapter.notifyDataSetChanged();
    }

    public void switchToMain(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}