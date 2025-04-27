package com.petestudy.HT;

/**
 * Green: atk 6, def 3, hp 19
 */
public class Green extends Lutemon {
    public Green(String nickname) {
            super(nickname, "green", 6, 3, 19);
        }

        @Override
        public String getImageName() {
            return "green_lutemon";
        }
}
