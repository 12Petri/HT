package com.petestudy.HT;

/**
 * Orange: atk 8, def 1, hp, 17
 */
public class Orange extends Lutemon{
    public Orange(String nickname) {
        super(nickname, "orange", 8, 1, 17);
    }

    @Override
    public String getImageName() {
        return "orange_lutemon";
    }
}
