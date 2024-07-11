package com.example.roguelike;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int health;
    private int attackPower;
    private int defense;
    private List<Item> inventory;
    private Item equippedKeepsake;

    // Attributes to support item effects
    private int criticalAreaSize;
    private double criticalDamageMultiplier;
    private int reflectDamage;
    private int healPerTurn;
    private boolean lifeSteal;
    private boolean hasExtraActions; //CHANGED to a boolean, because the user can only have 1 extra action anyway
    private boolean doubleAttack;
    private boolean canRevive;
    private int itemSpawnRate;

    // Constructor
    public Player(String name, int health, int attackPower, int defense) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.inventory = new ArrayList<>();
        this.equippedKeepsake = null;

        this.criticalAreaSize = 0; //TODO set this to a good value
        this.criticalDamageMultiplier = 1.0;
        this.reflectDamage = 0;
        this.healPerTurn = 0;
        this.lifeSteal = false;
        this.hasExtraActions = false;
        this.doubleAttack = false;
        this.canRevive = false;
        this.itemSpawnRate = 0;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(List<Item> inventory) {
        this.inventory = inventory;
    }

    public Item getEquippedKeepsake() {
        return equippedKeepsake;
    }

    public void setEquippedKeepsake(Item equippedKeepsake) {
        this.equippedKeepsake = equippedKeepsake;
    }

    public int getCriticalAreaSize() {
        return criticalAreaSize;
    }

    public void setCriticalAreaSize(int criticalAreaSize) {
        this.criticalAreaSize = criticalAreaSize;
    }

    public double getCriticalDamageMultiplier() {
        return criticalDamageMultiplier;
    }

    public void setCriticalDamageMultiplier(double criticalDamageMultiplier) {
        this.criticalDamageMultiplier = criticalDamageMultiplier;
    }

    public int getReflectDamage() {
        return reflectDamage;
    }

    public void setReflectDamage(int reflectDamage) {
        this.reflectDamage = reflectDamage;
    }

    public int getHealPerTurn() {
        return healPerTurn;
    }

    public void setHealPerTurn(int healPerTurn) {
        this.healPerTurn = healPerTurn;
    }

    public boolean isLifeSteal() {
        return lifeSteal;
    }

    public void setLifeSteal(boolean lifeSteal) {
        this.lifeSteal = lifeSteal;
    }

    public boolean checkExtraActions() {
        return hasExtraActions;
    }

    public void setExtraActionsTrue() {
        this.hasExtraActions = true;
    }

    public boolean isDoubleAttack() {
        return doubleAttack;
    }

    public void setDoubleAttack(boolean doubleAttack) {
        this.doubleAttack = doubleAttack;
    }

    public boolean isCanRevive() {
        return canRevive;
    }

    public void setCanRevive(boolean canRevive) {
        this.canRevive = canRevive;
    }   public int getItemSpawnRate() {
        return itemSpawnRate;
    }

    public void setItemSpawnRate(int itemSpawnRate) {
        this.itemSpawnRate = itemSpawnRate;
    }

    //  Methods
    public void attack(Enemy enemy) {
        int damage = attackPower - enemy.getDefense();
        if (damage > 0) {
            //enemy.takeDamage(damage); TODO implement enemy.takedamage
        }
    }

    public void takeDamage(int amount) {
        int damage = amount - defense;
        if (damage > 0) {
            health -= damage;
            if (health < 0) {
                health = 0;
            }
        }
    }

    public void useItem(Item item) {
        if (inventory.contains(item)) {
            item.applyEffect(this);
            inventory.remove(item);
        }
    }

    public void equipKeepsake(Item item) {
        equippedKeepsake = item;
    }

    public void completeSkillCheck(boolean success, Enemy enemy) {
        if (success) {
            // Double damage to the enemy on successful skill check
            int criticalDamage = attackPower * 2 - enemy.getDefense();
            if (criticalDamage > 0) {
                //enemy.takeDamage(criticalDamage); TODO implement enemy.takedamage
            }
        } else {
            // Double damage taken by player on failed skill check
            int criticalDamageTaken = attackPower * 2 - defense;
            if (criticalDamageTaken > 0) {
                health -= criticalDamageTaken;
                if (health < 0) {
                    health = 0;
                }
            }
        }
    }
    public void DisplayPlayer(Player p1){
        System.out.println("Player Name: " + p1.getName());
        System.out.println("Player Health: " + p1.getHealth());
        System.out.println("Player Attack Power: " + p1.getAttackPower());
        System.out.println("Player Defense: " + p1.getDefense());
        System.out.println("Player Inventory: " + p1.getInventory());
        System.out.println("Player Equipped Keepsake: " + p1.getEquippedKeepsake());
    }

    //Methods created by Parishad:
    public void addItemToInventory(Item item) {
        inventory.add(item);
    }



}

