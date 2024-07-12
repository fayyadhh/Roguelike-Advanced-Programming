package com.example.roguelike;

public class Enemy {
    private String name;
    private int health;
    private int attackPower;
    private int defense;
    private String rarity;

    public Enemy(String name, int health, int attackPower, int defense, String rarity) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.rarity = rarity;
    }

    
    public void setLevel(int level){
        switch(level){
            case 1:
                Common_Enemy();
                break;
            case 2:
                Uncommon_Enemy();
                break;
            case 3:
                Rare_Enemy();
                break;
            case 4:
                Epic_Enemy();
                break;
            case 5:
                Legendary_Enemy();
                break;
        }

    }
    public void Common_Enemy() {
        this.name = "Common Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Common";
    }

    public void Uncommon_Enemy() {
        this.name = "Uncommon Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Uncommon";
    }

    public void Rare_Enemy() {
        this.name = "Rare Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Rare";
    }

    public void Epic_Enemy() {
        this.name = "Epic Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Epic";
    }

    public void Legendary_Enemy() {
        this.name = "Legendary Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Legendary";
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public String getRarity() {
        return rarity;
    }
}
