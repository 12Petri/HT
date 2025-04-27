package com.petestudy.HT;

public class GameManager {
    private static GameManager instance;

    private Player player;

    public GameManager() {
        player = Player.getInstance();
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Player getPlayer() {
        return player;
    }
}
