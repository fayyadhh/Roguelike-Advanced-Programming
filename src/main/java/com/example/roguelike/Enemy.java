package com.example.roguelike;

import java.util.Random;

enum EnemyType {
    REGULAR,
    STRONG,
    BOSS
}

public class Enemy {   
    //Attributes
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int attackPower;
    private int defense;
    private EnemyType type;

    private int blockPreference; //How much the enemy will block if block is chosen

    private Level level;
    private Player player;

    //Constructor
    public Enemy(EnemyType type, String name) {
        this.type = type;
        this.name = name;
        configureEnemy();
    }

    //Methods
    public void spawnRandomEnemy(EnemyType type){
        //List of possible enemies that could spawn
        String[] possibleEnemies = {"Slime", "Goblin", "Skeleton", "Worm"};

        //Randomly choose an enemy from the list
        Random random = new Random();
        int randomIndex = random.nextInt(possibleEnemies.length);
        String randomEnemy = possibleEnemies[randomIndex];
        
        //Construct new enemy
        Enemy newEnemy = new Enemy(type, randomEnemy);
    }

    public void spawnRandomBoss(){
        //List of possible bosses that could spawn
        String[] possibleBosses = {"Slime King", "Goblin King", "Skeleton King", "Worm King"}; //Change values in this boss list
        
        //Randomly choose a boss from the list
        Random random = new Random();
        int randomIndex = random.nextInt(possibleBosses.length);
        String randomBoss = possibleBosses[randomIndex];

        //Construct new boss
        Enemy newBoss = new Enemy(EnemyType.BOSS, randomBoss);
    }



    private void configureEnemy() {
        switch (type) {
            case REGULAR:
                configureRegularEnemy();
                break;
            case STRONG:
                StrongEnemyIncreaseStats(level.getLevel());
                break;
            case BOSS:
                configureBossEnemy();
                break;
        }
    }

    private void configureRegularEnemy() {
        switch (this.name.toLowerCase()){
            case "slime":
                this.currentHealth = 10;
                this.maxHealth = 10;
                this.attackPower = 2;
                this.blockPreference = 1;
                // this.rarity = "Common";
                break;
            case "goblin":
                this.currentHealth = 15;
                this.maxHealth = 15;
                this.attackPower = 3;
                this.blockPreference = 0;
                // this.rarity = "Common";
                break;
            case "skeleton":
                this.currentHealth = 10;
                this.maxHealth = 10;
                this.attackPower = 3;
                this.blockPreference = 0;
                // this.rarity = "Common";
                break;
            case "worm":
                this.currentHealth = 10;
                this.maxHealth = 10;
                this.attackPower = 2;
                this.blockPreference = 0;
                // this.rarity = "Common";
                break;
        }
    }
    private void StrongEnemyIncreaseStats(int level){
        //Strong enemies are just Regular enemies but with increased stats
        //Therefore we can just increase the health and attack power by a certain modifier
        double healthIncreaseModifier = 1.05; //5% per level
        double attackPowerIncreaseModifier = 1.03; //3 per level

        this.currentHealth = (int) (this.currentHealth * Math.pow(healthIncreaseModifier, level));
        this.maxHealth = (int) (this.maxHealth * Math.pow(healthIncreaseModifier, level));
        this.attackPower = (int) (this.attackPower * Math.pow(attackPowerIncreaseModifier, level));
    }

    private void configureBossEnemy(){

    }

    //Methods for the enemy's turns (i have no idea what im doing im just freeballing rn)
    public void enemyTurn(){
        //Start of enemy's turn, reset the block
        resetBlockAmount();

        //Chose action
        double chosenAction = Math.random();

        //Made it so that enemies that have 0 block preference will always attack because it just means they don't prefer to block. duh
        if(blockPreference > 0){
            if (chosenAction < 0.5){
                attack();
            } else {
                block(blockPreference);
            }
        } else {
            attack();
        }
    }

    public void attack(){
        player.takeDamage(attackPower);
    }

    public void block(int block){
        this.defense = block; //Just make defense equal to how much the enemy is blocking for
    }

    public void resetBlockAmount(){
        this.defense = 0;
    }

    //Getters and Setters

    public String getName() {
        return name;
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public boolean isDead(){
        if(currentHealth <= 0){
            return true;
        } else {
            return false;
        }
    }
}
