package com.petestudy.HT;

public class Player {
    private int coins;
    private static Player instance;

    private Player() {
        coins = 15;
    }

    public static Player getInstance() {
        if (instance == null) {
            instance = new Player();
        }
        return instance;
    }

    public int getCoins() {
        return coins;
    }

    public boolean spendCoins(int amount) {
        if (coins >= amount) {
            coins -= amount;
            return true;
        }
        return false;
    }

    public boolean buyLutemon(Lutemon lutemon) {
        int price = lutemon.getPrice();
        if (spendCoins(price)) {
            Storage.getInstance().addLutemon(lutemon);
            return true;
        }
        return false;
    }

    public void addCoins(int amount) {
        coins += amount;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public static void resetInstance() {
        instance = null;
    }
}
