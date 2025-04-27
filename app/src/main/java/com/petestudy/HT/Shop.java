package com.petestudy.HT;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private static Shop instance;
    private final List<Lutemon> availableLutemons;

    private  Shop() {
        availableLutemons = new ArrayList<>();
        availableLutemons.add(new White("Lumi"));
        availableLutemons.add(new Black("Noctis"));
        availableLutemons.add(new Green("Moss"));
        availableLutemons.add(new Pink("Ember"));
        availableLutemons.add(new Orange("Blossom"));
    }

    public static Shop getInstance() {
        if (instance == null) {
            instance = new Shop();
        }
        return instance;
    }

    public List<Lutemon> getAvailableLutemons() {
        return availableLutemons;
    }
}
