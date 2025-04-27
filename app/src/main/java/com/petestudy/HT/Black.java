package com.petestudy.HT;

/**
 * Black: atk 9, def 1, hp 16
 */

public class Black extends Lutemon{
    public Black(String nickname) {
        super(nickname, "black", 9, 1, 16);
    }

    @Override
    public String getImageName() {
        return "black_lutemon";
    }
}
