package com.petestudy.HT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.petestudy.myapplication.R;

public class TournamentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament);

        setupButtons();
    }

    private void setupButtons() {
        Button button1 = findViewById(R.id.buttonFriends);
        Button button2 = findViewById(R.id.buttonVillage);
        Button button3 = findViewById(R.id.buttonHomeLeague);
        Button button4 = findViewById(R.id.buttonWorldLeague);
        Button button5 = findViewById(R.id.buttonChampionship);

        int unlocked = Storage.getInstance().getUnlockedTournaments();

        button1.setEnabled(unlocked >= 1);
        button2.setEnabled(unlocked >= 2);
        button3.setEnabled(unlocked >= 3);
        button4.setEnabled(unlocked >= 4);
        button5.setEnabled(unlocked >= 5);
    }

    public void startTournament(View view) {
        Intent intent = new Intent(this, BattleActivity.class);

        if (view.getId() == R.id.buttonFriends) {
            intent.putExtra("tournamentName", "Kavereiden kesken");
            intent.putExtra("enemyCount", 3);
        } else if (view.getId() == R.id.buttonVillage) {
            intent.putExtra("tournamentName", "Kylätappelut");
            intent.putExtra("enemyCount", 4);
        } else if (view.getId() == R.id.buttonHomeLeague) {
            intent.putExtra("tournamentName", "Koti-Liiga");
            intent.putExtra("enemyCount", 5);
        } else if (view.getId() == R.id.buttonWorldLeague) {
            intent.putExtra("tournamentName", "Maailman-Liiga");
            intent.putExtra("enemyCount", 6);
        } else if (view.getId() == R.id.buttonChampionship) {
            intent.putExtra("tournamentName", "Championship");
            intent.putExtra("enemyCount", 7);
        }

        startActivity(intent);
    }
}