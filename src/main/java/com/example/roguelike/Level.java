package com.example.roguelike;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Level {
    public int level = 1;
    public int round = 0;
    public boolean isDead = false;
    public int rewardMoney = 0;

    public Enemy currentEnemy; //to use for when enemy data can be saved to level... which idk how to do as of yet... god save me

    public Level(){
        loadLevelData();
    }

    public int getLevel() {
        return level;
    }

    public void levelup() {
        level++;
        saveLevelData();
    }

    public void setDead(){
        this.isDead = true;
    }

    public boolean checkIfDead(){
        return isDead;
    }

    public void setRewardMoney(int rewardMoney){
        this.rewardMoney = rewardMoney;
    }

    public int getRewardMoney(){
        return rewardMoney;
    }

    // public int getRound(){
    //     return round;
    // }  I dont think we need a rounds variable, since the level is the main thing that needs to be tracked

    // public int roundUp() {
    //     return round++;
    // }

    //Adding implementation of level save data 
    //so that if the player wants to continue game they can just continue and it will load in the created level file created in this class
    //and just go from there

    //question is... how?
    //i think we can use the same method as the player class, but instead of saving the player data, we save the level data - copilot
    //dude hes on to something

    public void saveLevelData(){
        //Save the level data to a file
        String fileName = "levelData.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Level: " + level);
            writer.newLine();

            writer.write("IsDead: " + isDead);
            writer.newLine();

            writer.write("RewardMoney: " + rewardMoney);
            writer.newLine();

            //find a way to save the current enemy 4head
            
            //what else needs to be added to the level save data?
            //i think that's it, since the level is the only thing that needs to be saved
            //thats true but im afraid that if the player continues again, then theres gonna be a different enemy in the level, you know?
            //i think we can just save the level data, and then when the player continues, we can just spawn a new enemy
            //i think that's a good idea, BUT i want the save data to be exactly the same so that the player can continue where they left off EXACTLY
            //i think we can just save the level data, and then when the player continues, we can just spawn a new enemy
            //ok so now we've reached a loop in your responses and they are exactly the same.
            //i think we can just save the level data, and then when the player continues, we can just spawn a new enemy
            //whatever man... -my conversation with copilot without even using the copilot chat

            
        } catch (IOException e) {
            System.err.println("An error occurred while saving player data.");
            e.printStackTrace();
        }
    }

    public void loadLevelData(){
        //Load the level data from a file
        String fileName = "levelData.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                //Parse the line for level text
                String[] parts = line.split(": ");
                if (parts[0].equals("Level")) {
                    level = Integer.parseInt(parts[1]);
                }
                line = reader.readLine();
                if (parts[0].equals("IsDead")) {
                    isDead = Boolean.parseBoolean(parts[1]);
                }
                line = reader.readLine();
                if (parts[0].equals("RewardMoney")) {
                    rewardMoney = Integer.parseInt(parts[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while loading player data.");
            e.printStackTrace();
        }
    }

    public void resetForNewGame(){
        level = 1;
        isDead = false;
        saveLevelData();
    }
}
