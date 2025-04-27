package com.petestudy.HT;

/**
 * Pink: atk 7, def 2, hp 18
 */

public class Pink extends Lutemon {
    public Pink(String nickname) {
        super(nickname, "pink", 7, 2, 18);
    }

    @Override
    public String getImageName() {
        return "pink_lutemon";
    }
}
