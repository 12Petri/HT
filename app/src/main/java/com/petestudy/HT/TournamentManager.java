package com.petestudy.HT;

import java.util.ArrayList;
import java.util.List;

public class TournamentManager {

    private static TournamentManager instance;

    private List<Lutemon> playerTeam = new ArrayList<>();
    private List<Lutemon> opponentTeam = new ArrayList<>();
    private List<Lutemon> originalPlayerTeam = new ArrayList<>();

    private int playerIndex = 0;
    private int opponentIndex = 0;

    private TournamentManager() {}

    public static TournamentManager getInstance() {
        if (instance == null) {
            instance = new TournamentManager();
        }
        return instance;
    }

    public void startFriendsTournament(List<Lutemon> selectedTeam) {
        playerTeam.clear();
        playerTeam.addAll(selectedTeam);

        originalPlayerTeam.clear();
        originalPlayerTeam.addAll(selectedTeam);

        opponentTeam.clear();
        for (int i = 0; i < 3; i++) {
            opponentTeam.add(createOpponentLutemon());
        }

        playerIndex = 0;
        opponentIndex = 0;
    }

    private Lutemon createOpponentLutemon() {
        Lutemon opponent;
        int rand = (int) (Math.random() * 5);

        if (rand == 0) {
            opponent = new White("Vastustaja");
        } else if (rand == 1) {
            opponent = new Black("Vastustaja");
        } else if (rand == 2) {
            opponent = new Pink("Vastustaja");
        } else if (rand == 3) {
            opponent = new Green("Vastustaja");
        } else {
            opponent = new Orange("Vastustaja");
        }

        opponent.setExperience(1);
        opponent.setHealth(opponent.getMaxHealth());
        return opponent;
    }

    public Lutemon getCurrentPlayerLutemon() {
        if (playerIndex < playerTeam.size()) {
            return playerTeam.get(playerIndex);
        }
        return null;
    }

    public Lutemon getCurrentOpponentLutemon() {
        if (opponentIndex < opponentTeam.size()) {
            return opponentTeam.get(opponentIndex);
        }
        return null;
    }

    public void playerLutemonDefeated() {
        playerIndex++;
    }

    public void playerLutemonRetreated() {
        if (playerIndex < playerTeam.size()) {
            playerTeam.remove(playerIndex);
            // Ei siirretä indeksiä, koska uusi Lutemon tulee tilalle
        }
    }

    public void opponentLutemonDefeated() {
        opponentIndex++;
    }

    public boolean isBattleOver() {
        return playerIndex >= playerTeam.size() || opponentIndex >= opponentTeam.size();
    }

    public boolean didPlayerWin() {
        return opponentIndex >= opponentTeam.size();
    }

    public void rewardTournamentWinner() {
        for (Lutemon l : originalPlayerTeam) {
            l.gainExperience(50); // Kaikki alkuperäiset saavat XP:t
            l.setHealth(l.getMaxHealth()); // Palautetaan täysi HP
        }
    }

    public void healPlayerTeam() {
        for (Lutemon l : originalPlayerTeam) {
            l.setHealth(l.getMaxHealth()); // Palautetaan täysi HP
        }
    }

    public List<Lutemon> getSelectedPlayerLutemons() {
        return new ArrayList<>(originalPlayerTeam);
        // Palautetaan kopio, ettei ulkopuolella voi vahingossa muuttaa listaa suoraan
    }

    public void resetTournament() {
        playerTeam.clear();
        opponentTeam.clear();
        originalPlayerTeam.clear();
        playerIndex = 0;
        opponentIndex = 0;
    }

    public void startVillageTournament(List<Lutemon> selectedTeam) {
        startGenericTournament(selectedTeam, 4);
    }

    public void startHomeLeagueTournament(List<Lutemon> selectedTeam) {
        startGenericTournament(selectedTeam, 5);
    }

    public void startWorldLeagueTournament(List<Lutemon> selectedTeam) {
        startGenericTournament(selectedTeam, 6);
    }

    public void startChampionshipTournament(List<Lutemon> selectedTeam) {
        startGenericTournament(selectedTeam, 7);
    }

    private void startGenericTournament(List<Lutemon> selectedTeam, int enemyCount) {
        playerTeam.clear();
        playerTeam.addAll(selectedTeam);

        originalPlayerTeam.clear();
        originalPlayerTeam.addAll(selectedTeam);

        opponentTeam.clear();
        for (int i = 0; i < enemyCount; i++) {
            opponentTeam.add(createOpponentLutemon());
        }

        playerIndex = 0;
        opponentIndex = 0;
    }
}