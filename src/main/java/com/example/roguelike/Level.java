package com.example.roguelike;

public class Level {
    public int level = 0;
    public int round = 0;

    public int getLevel() {
        return level;
    }

    public void levelup() {
        level++;
    }

    public int getRound(){
        return round;
    }

    public int roundUp() {
        return round++;
    }

    public void resetRound(){
        round = 0;
    }
}
