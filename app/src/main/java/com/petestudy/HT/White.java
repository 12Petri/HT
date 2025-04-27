package com.petestudy.HT;

/**
 * White: atk 5, def 4, hp 20
 */

public class White extends Lutemon{
    public White (String nickname) {
        super(nickname, "white", 5, 4, 20);
    }

    @Override
    public String getImageName() {
        return "white_lutemon";
    }
}