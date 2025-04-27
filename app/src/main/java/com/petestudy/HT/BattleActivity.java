package com.petestudy.HT;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.petestudy.myapplication.R;

public class BattleActivity extends AppCompatActivity {

    private Lutemon myLutemon;
    private Lutemon opponentLutemon;

    private TextView textBattleLog;
    private TextView textCurrentLutemon;
    private ScrollView scrollView;

    private Button attackButton, defendButton, specialButton, retreatButton, escapeButton, finishButton;

    private StringBuilder battleLog = new StringBuilder();

    private int specialCharge = 0;
    private boolean specialLoading = false;

    private int opponentSpecialCharge = 0;
    private boolean opponentLoadingSpecial = false;

    private boolean battleEnded = false;
    private boolean isTournament = false;
    private boolean isPlayerTurn = true;
    private boolean trainingVictory = false;
    private boolean trainingDefeat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        textBattleLog = findViewById(R.id.textBattleLog);
        textCurrentLutemon = findViewById(R.id.textCurrentLutemon);
        scrollView = findViewById(R.id.scrollView);

        attackButton = findViewById(R.id.buttonAttack);
        defendButton = findViewById(R.id.buttonDefend);
        specialButton = findViewById(R.id.buttonSpecial);
        retreatButton = findViewById(R.id.buttonRetreat);
        escapeButton = findViewById(R.id.buttonEscapeBattle);
        finishButton = findViewById(R.id.buttonFinishBattle);

        isTournament = getIntent().getBooleanExtra("isTournament", false);

        if (isTournament) {
            reloadFighters();
        } else {
            myLutemon = (Lutemon) getIntent().getSerializableExtra("myLutemon");
            opponentLutemon = createTrainingOpponent(myLutemon.getExperience());
        }

        if (myLutemon == null || opponentLutemon == null) {
            finish();
            return;
        }

        textCurrentLutemon.setText("Valittu: " + myLutemon.getName());
        logHealthStatus();
        updateBattleLog();

        attackButton.setOnClickListener(v -> attackTurn());
        defendButton.setOnClickListener(v -> defendTurn());
        specialButton.setOnClickListener(v -> specialTurn());
        retreatButton.setOnClickListener(v -> retreatBattle());
        escapeButton.setOnClickListener(v -> escapeBattle());
        finishButton.setOnClickListener(v -> finishBattle());
    }

    private void reloadFighters() {
        myLutemon = TournamentManager.getInstance().getCurrentPlayerLutemon();
        opponentLutemon = TournamentManager.getInstance().getCurrentOpponentLutemon();
    }

    private Lutemon createTrainingOpponent(int experience) {
        Lutemon opponent;
        int rand = (int) (Math.random() * 5);
        if (rand == 0) opponent = new White("Harjoitusvastus");
        else if (rand == 1) opponent = new Black("Harjoitusvastus");
        else if (rand == 2) opponent = new Pink("Harjoitusvastus");
        else if (rand == 3) opponent = new Green("Harjoitusvastus");
        else opponent = new Orange("Harjoitusvastus");

        opponent.setExperience(experience);
        opponent.setHealth(opponent.getMaxHealth());
        return opponent;
    }

    private void attackTurn() {
        if (battleEnded || !isPlayerTurn || specialLoading) return;
        int damage = myLutemon.attack();
        opponentLutemon.takeDamage(damage);
        logAction(myLutemon.getName() + " hyökkää!");
        isPlayerTurn = false;
        afterPlayerAction();
    }

    private void defendTurn() {
        if (battleEnded || !isPlayerTurn || specialLoading) return;
        myLutemon.startDefending();
        logDefense(myLutemon.getName() + " puolustautuu.");
        isPlayerTurn = false;
        afterPlayerAction();
    }

    private void specialTurn() {
        if (battleEnded || !isPlayerTurn) return;

        if (!specialLoading) {
            specialLoading = true;
            specialCharge = 1;
            logAction(myLutemon.getName() + " aloittaa erikoishyökkäyksen latauksen!");
            attackButton.setEnabled(false);
            defendButton.setEnabled(false);
        } else if (specialCharge == 1) {
            specialCharge++;
            logAction(myLutemon.getName() + " jatkaa erikoishyökkäyksen latausta...");
        } else if (specialCharge == 2) {
            logSpecial(myLutemon.getName() + " vapauttaa ERIKOISHYÖKKÄYKSEN!");
            opponentLutemon.takeDamage(myLutemon.attack() * 3);
            specialLoading = false;
            specialCharge = 0;
            attackButton.setEnabled(true);
            defendButton.setEnabled(true);
        }

        isPlayerTurn = false;
        afterPlayerAction();
    }

    private void afterPlayerAction() {
        checkBattleStatus();
        if (!battleEnded && !isPlayerTurn) {
            opponentTurn();
            checkBattleStatus();
            isPlayerTurn = true;
        }
        updateBattleLog();
    }

    private void opponentTurn() {
        if (battleEnded) return;

        if (opponentLoadingSpecial) {
            opponentSpecialCharge++;
            if (opponentSpecialCharge == 3) {
                logSpecial(opponentLutemon.getName() + " vapauttaa ERIKOISHYÖKKÄYKSEN!");
                myLutemon.takeDamage(opponentLutemon.attack() * 3);
                opponentLoadingSpecial = false;
                opponentSpecialCharge = 0;
            } else {
                logAction(opponentLutemon.getName() + " jatkaa erikoishyökkäyksen lataamista...");
            }
        } else {
            double random = Math.random();
            if (random < 0.05) {
                opponentLoadingSpecial = true;
                opponentSpecialCharge = 1;
                logAction(opponentLutemon.getName() + " aloittaa erikoishyökkäyksen latauksen!");
            } else if (random < 0.25) {
                opponentLutemon.startDefending();
                logDefense(opponentLutemon.getName() + " puolustautuu!");
            } else {
                myLutemon.takeDamage(opponentLutemon.attack());
                logAction(opponentLutemon.getName() + " hyökkää!");
            }
        }
    }

    private void checkBattleStatus() {
        logHealthStatus();

        if (opponentLutemon.getHealth() <= 0) {
            logAction("Vastustaja kaatui!");
            if (!isTournament) {
                trainingVictory = true;
            } else {
                TournamentManager.getInstance().opponentLutemonDefeated();
            }
            resetCharges();
            startNextBattle();
        } else if (myLutemon.getHealth() <= 0) {
            logAction(myLutemon.getName() + " kaatui!");
            if (!isTournament) {
                trainingDefeat = true;
            } else {
                TournamentManager.getInstance().playerLutemonDefeated();
            }
            resetCharges();
            startNextBattle();
        }
    }

    private void startNextBattle() {
        if (TournamentManager.getInstance().isBattleOver() || trainingVictory || trainingDefeat) {
            disableBattleButtons();
            finishButton.setVisibility(View.VISIBLE);
            finishButton.setEnabled(true);
            battleEnded = true;

            if (!isTournament) {
                myLutemon.setHealth(myLutemon.getMaxHealth()); // Palautetaan HP täyteen harjoituksessa
                Storage.getInstance().updateLutemon(myLutemon);
            }

            if (isTournament) {
                escapeButton.setEnabled(TournamentManager.getInstance().didPlayerWin() ? false : false);
            } else {
                escapeButton.setEnabled(false);
            }
        } else if (isTournament) {
            reloadFighters();
            resetCharges();
            battleEnded = false;
            isPlayerTurn = true;
            textCurrentLutemon.setText("Valittu: " + myLutemon.getName());
            battleLog.setLength(0);
            logInfo("Uusi taistelu alkaa!");
            logHealthStatus();
            updateBattleLog();
            enableAllBattleButtons();
        }
    }

    private void retreatBattle() {
        if (battleEnded) return;
        if (!isTournament) {
            myLutemon.setHealth(myLutemon.getMaxHealth());
            Storage.getInstance().updateLutemon(myLutemon);
            finish();
        } else {
            logAction(myLutemon.getName() + " perääntyy!");
            TournamentManager.getInstance().playerLutemonRetreated();
            resetCharges();
            startNextBattle();
        }
    }

    private void escapeBattle() {
        TournamentManager.getInstance().healPlayerTeam();
        for (Lutemon l : TournamentManager.getInstance().getSelectedPlayerLutemons()) {
            Storage.getInstance().updateLutemon(l);
        }
        Storage.getInstance().addBattle();
        TournamentManager.getInstance().resetTournament();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void finishBattle() {
        if (!isTournament) {
            if (trainingVictory) {
                int xpGained = opponentLutemon.getMaxHealth();
                myLutemon.gainExperience(xpGained);
                Storage.getInstance().addXP(xpGained);
                Storage.getInstance().addBattle();
            }
            myLutemon.setHealth(myLutemon.getMaxHealth());
            Storage.getInstance().updateLutemon(myLutemon);
            startActivity(new Intent(this, HomeActivity.class));
        } else {
            if (TournamentManager.getInstance().didPlayerWin()) {
                TournamentManager.getInstance().rewardTournamentWinner();
                Storage.getInstance().incrementTournamentWins();
                Player.getInstance().addCoins(3);
            } else {
                TournamentManager.getInstance().healPlayerTeam();
            }

            for (Lutemon l : TournamentManager.getInstance().getSelectedPlayerLutemons()) {
                Storage.getInstance().updateLutemon(l);
            }
            Storage.getInstance().addBattle();
            TournamentManager.getInstance().resetTournament();
            startActivity(new Intent(this, HomeActivity.class));
        }
        finish();
    }

    private void resetCharges() {
        specialCharge = 0;
        specialLoading = false;
        opponentSpecialCharge = 0;
        opponentLoadingSpecial = false;
    }

    private void disableBattleButtons() {
        attackButton.setEnabled(false);
        defendButton.setEnabled(false);
        specialButton.setEnabled(false);
        retreatButton.setEnabled(false);
    }

    private void enableAllBattleButtons() {
        attackButton.setEnabled(true);
        defendButton.setEnabled(true);
        specialButton.setEnabled(true);
        retreatButton.setEnabled(true);
        finishButton.setVisibility(View.GONE);
    }

    private void log(String msg) {
        battleLog.insert(0, "<br>" + msg + "<br>");
    }

    private void logAction(String msg) {
        battleLog.insert(0, "<br><b>" + msg + "</b><br>");
    }

    private void logDefense(String msg) {
        battleLog.insert(0, "<br><b><font color='#0000FF'>" + msg + "</font></b><br>");
    }

    private void logSpecial(String msg) {
        battleLog.insert(0, "<br><b><font color='#FF0000'>" + msg + "</font></b><br>");
    }

    private void logInfo(String msg) {
        battleLog.insert(0, "<br>" + msg + "<br>");
    }

    private void updateBattleLog() {
        textBattleLog.setText(Html.fromHtml(battleLog.toString(), Html.FROM_HTML_MODE_LEGACY));
        scrollView.fullScroll(View.FOCUS_UP);
    }

    private void logHealthStatus() {
        log(myLutemon.getName() + " HP: " + myLutemon.getHealth() + "/" + myLutemon.getMaxHealth());
        log(opponentLutemon.getName() + " HP: " + opponentLutemon.getHealth() + "/" + opponentLutemon.getMaxHealth());
    }
}