package com.petestudy.HT;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.petestudy.myapplication.R;

public class TournamentMenuActivity extends AppCompatActivity {

    private Button buttonFriends, buttonVillage, buttonHomeLeague, buttonWorldLeague, buttonChampionship;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournament_menu);

        setupButtons();
    }

    private void setupButtons() {
        buttonFriends = findViewById(R.id.buttonFriends);
        buttonVillage = findViewById(R.id.buttonVillage);
        buttonHomeLeague = findViewById(R.id.buttonHomeLeague);
        buttonWorldLeague = findViewById(R.id.buttonWorldLeague);
        buttonChampionship = findViewById(R.id.buttonChampionship);

        int unlocked = Storage.getInstance().getUnlockedTournaments();

        buttonFriends.setEnabled(unlocked >= 1);
        buttonVillage.setEnabled(unlocked >= 2);
        buttonHomeLeague.setEnabled(unlocked >= 3);
        buttonWorldLeague.setEnabled(unlocked >= 4);
        buttonChampionship.setEnabled(unlocked >= 5);
    }

    private boolean checkSelection() {
        if (Storage.getInstance().getSelectedForTournament().isEmpty()) {
            return false;
        }
        return true;
    }

    public void startFriendsTournament(View view) {
        if (!checkSelection()) return;

        TournamentManager.getInstance().startFriendsTournament(Storage.getInstance().getSelectedForTournament());
        goToBattle("Kavereiden kesken", 3);
    }

    public void startVillageTournament(View view) {
        if (!checkSelection()) return;

        TournamentManager.getInstance().startVillageTournament(Storage.getInstance().getSelectedForTournament());
        goToBattle("Kylätappelut", 4);
    }

    public void startHomeLeagueTournament(View view) {
        if (!checkSelection()) return;

        TournamentManager.getInstance().startHomeLeagueTournament(Storage.getInstance().getSelectedForTournament());
        goToBattle("Koti-Liiga", 5);
    }

    public void startWorldLeagueTournament(View view) {
        if (!checkSelection()) return;

        TournamentManager.getInstance().startWorldLeagueTournament(Storage.getInstance().getSelectedForTournament());
        goToBattle("Maailman-Liiga", 6);
    }

    public void startChampionshipTournament(View view) {
        if (!checkSelection()) return;

        TournamentManager.getInstance().startChampionshipTournament(Storage.getInstance().getSelectedForTournament());
        goToBattle("Championship", 7);
    }

    private void goToBattle(String tournamentName, int enemyCount) {
        Intent intent = new Intent(this, BattleActivity.class);
        intent.putExtra("isTournament", true);
        intent.putExtra("tournamentName", tournamentName);
        intent.putExtra("enemyCount", enemyCount);
        startActivity(intent);
        finish();
    }

    public void goToMainMenu(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}