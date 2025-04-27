package com.petestudy.HT;

import java.util.ArrayList;
import java.util.List;

public class Storage {
    private static Storage instance;
    private final List<Lutemon> lutemons = new ArrayList<>();
    private final List<Lutemon> selectedForTournament = new ArrayList<>();

    private int tournamentWins = 0;
    private int totalBattles = 0;
    private int totalXP = 0;
    private int unlockedTournaments = 1;

    private Storage() {
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public List<Lutemon> getLutemons() {
        return lutemons;
    }

    public void addLutemon(Lutemon l) {
        lutemons.add(l);
    }

    public void removeLutemon(int index) {
        if (index >= 0 && index < lutemons.size()) {
            lutemons.remove(index);
        }
    }

    public void updateLutemon(Lutemon updated) {
        for (int i = 0; i < lutemons.size(); i++) {
            if (lutemons.get(i).getId() == updated.getId()) {
                lutemons.set(i, updated);
                break;
            }
        }
    }

    public List<Lutemon> getSelectedForTournament() {
        return selectedForTournament;
    }

    public void addToTournamentSelection(Lutemon lutemon) {
        if (!selectedForTournament.contains(lutemon) && selectedForTournament.size() < 3) {
            selectedForTournament.add(lutemon);
        }
    }

    public void removeFromTournamentSelection(Lutemon lutemon) {
        selectedForTournament.remove(lutemon);
    }

    public int getTournamentWins() {
        return tournamentWins;
    }

    public void incrementTournamentWins() {
        tournamentWins++;
        unlockNextTournament();
    }

    public void addBattle() {
        totalBattles++;
    }

    public void addXP(int xp) {
        totalXP += xp;
    }

    public int getTotalBattles() {
        return totalBattles;
    }

    public int getTotalXP() {
        return totalXP;
    }

    public int getUnlockedTournaments() {
        return unlockedTournaments;
    }

    public void unlockNextTournament() {
        if (unlockedTournaments < 5) {
            unlockedTournaments++;
        }
    }
}