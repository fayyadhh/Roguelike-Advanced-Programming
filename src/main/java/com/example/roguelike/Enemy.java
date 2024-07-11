package com.example.roguelike;

public class Enemy {
    private String name;
    private int health;
    private int attackPower;
    private int defense;
    private String rarity;

    public void Common_Enemy() {
        this.name = "Normal Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Common";
    }

    public void Uncommon_Enemy() {
        this.name = "Normal Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Common";
    }

    public void Rare_Enemy() {
        this.name = "Normal Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Common";
    }

    public void Epic_Enemy() {
        this.name = "Normal Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Common";
    }

    public void Legendary_Enemy() {
        this.name = "Normal Enemy";
        this.health = 100;
        this.attackPower = 2;
        this.defense = 5;
        this.rarity = "Common";
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
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
