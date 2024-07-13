package com.example.roguelike;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int attackPower;
    private int defense;
    private List<Item> inventory;
    private Item equippedKeepsake;

    // Attributes to support item effects
    private double critMultiplier;
    private double criticalDamageMultiplier;
    private int reflectDamage;
    private int healPerTurn;
    private boolean lifeSteal;
    private boolean hasExtraActions; //CHANGED to a boolean, because the user can only have 1 extra action anyway
    private boolean doubleAttack;
    private boolean canRevive;
    private int itemSpawnRate;

    // Constructor
    public Player(String fileName) {
        loadPlayerData(fileName);
    }

    public Player(String name, int health, int attackPower, int defense) {
        this.name = name;
        this.currentHealth = health;
        this.maxHealth = health;
        this.attackPower = attackPower;
        this.defense = defense;
        this.inventory = new ArrayList<>();
        this.equippedKeepsake = null;

        this.critMultiplier = 0.05; //TODO set this to a good value
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

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getMaxHealth(){
        return maxHealth;
    }

    public void setCurrentHealth(int health) {
        this.currentHealth = health;
    }

    public void setMaxHealth(int health){
        this.maxHealth = health;
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

    public double getCritMultiplier() {
        return critMultiplier;
    }

    public void setCritMultiplier(double critMultiplier) {
        this.critMultiplier = critMultiplier;
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
    public void attack(Enemy enemy, double attackMultiplier) {
        int damage =  (int) ((attackPower * attackMultiplier) - enemy.getDefense());
        if (damage > 0) {
            enemy.takeDamage(damage); //This is done in the combat screen
            //WRONG THIS ISNT DONE IN ATTACK SCREEN BC COMBATSCREEN DOESNT HAVE A USE :SOB:
        } else if (damage <= 0) {
            //Miss lol
            //TODO implement a message for when the player misses
        }
    }

    public void takeDamage(int amount) {
        int damage = amount - defense;
        if (damage > 0) {
            setCurrentHealth(currentHealth - 5);;
            if (currentHealth < 0) {
                currentHealth = 0;
            }
        }
        checkPlayerDead();
    }

    public boolean checkPlayerDead(){
        if(currentHealth <= 0){
            return true;
        }
        return false;
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

    // public void completeSkillCheck(boolean success, Enemy enemy) {
    //     if (success) {
    //         // Double damage to the enemy on successful skill check
    //         int criticalDamage = attackPower * 2 - enemy.getDefense();
    //         if (criticalDamage > 0) {
    //             //enemy.takeDamage(criticalDamage); 
    //         }
    //     } else {
    //         // Double damage taken by player on failed skill check
    //         int criticalDamageTaken = attackPower * 2 - defense;
    //         if (criticalDamageTaken > 0) {
    //             health -= criticalDamageTaken;
    //             if (health < 0) {
    //                 health = 0;
    //             }
    //         }
    //     }
    // } this is also done in the combat screen

    public void DisplayPlayer(Player p1){
        System.out.println("Player Name: " + p1.getName());
        System.out.println("Player Health: " + p1.getCurrentHealth());
        System.out.println("Player Attack Power: " + p1.getAttackPower());
        System.out.println("Player Defense: " + p1.getDefense());
        System.out.println("Player Inventory: " + p1.getInventory());
        System.out.println("Player Equipped Keepsake: " + p1.getEquippedKeepsake());
    }

    //Methods created by Parishad:
    public void addItemToInventory(Item item) {
        inventory.add(item);
    }

    //Save to file
    public void savePlayerData() {
        String fileName = "playerData.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("Name: " + this.name + "\n");
            writer.write("Current Health: " + this.currentHealth + "\n");
            writer.write("Max Health: " + this.maxHealth + "\n");
            writer.write("Attack Power: " + this.attackPower + "\n");
            writer.write("Defense: " + this.defense + "\n");
            writer.write("Crit Multiplier: " + this.critMultiplier + "\n");
            writer.write("Critical Damage Multiplier: " + this.criticalDamageMultiplier + "\n");
            writer.write("Reflect Damage: " + this.reflectDamage + "\n");
            writer.write("Heal Per Turn: " + this.healPerTurn + "\n");
            writer.write("Life Steal: " + this.lifeSteal + "\n");
            writer.write("Has Extra Actions: " + this.hasExtraActions + "\n");
            writer.write("Double Attack: " + this.doubleAttack + "\n");
            writer.write("Can Revive: " + this.canRevive + "\n");
            writer.write("Item Spawn Rate: " + this.itemSpawnRate + "\n");
            writer.write("Inventory: " + inventoryToString(this.inventory) + "\n");
            writer.write("Equipped Keepsake: " + (this.equippedKeepsake != null ? this.equippedKeepsake.toString() : "None") + "\n");
        } catch (IOException e) {
            System.err.println("An error occurred while saving player data.");
            e.printStackTrace();
        }
    }

    private String inventoryToString(List<Item> inventory) {
        StringBuilder sb = new StringBuilder();
        for (Item item : inventory) {
            sb.append(item.toString()).append(", ");
        }
        return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "Empty"; // Remove last comma and space
    }

    // Method to read player data from a file
    public void loadPlayerData(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length < 2) continue; // Skip lines that don't have the expected format
                String key = parts[0].trim();
                String value = parts[1].trim();
                switch (key) {
                    case "Name":
                        this.name = value;
                        break;
                    case "Current Health":
                        this.currentHealth = Integer.parseInt(value);
                        break;
                    case "Max Health":
                        this.maxHealth = Integer.parseInt(value);
                        break;
                    case "Attack Power":
                        this.attackPower = Integer.parseInt(value);
                        break;
                    case "Defense":
                        this.defense = Integer.parseInt(value);
                        break;
                    case "Crit Multiplier":
                        this.critMultiplier = Double.parseDouble(value);
                        break;
                    case "Critical Damage Multiplier":
                        this.criticalDamageMultiplier = Double.parseDouble(value);
                        break;
                    case "Reflect Damage":
                        this.reflectDamage = Integer.parseInt(value);
                        break;
                    case "Heal Per Turn":
                        this.healPerTurn = Integer.parseInt(value);
                        break;
                    case "Life Steal":
                        this.lifeSteal = Boolean.parseBoolean(value);
                        break;
                    case "Has Extra Actions":
                        this.hasExtraActions = Boolean.parseBoolean(value);
                        break;
                    case "Double Attack":
                        this.doubleAttack = Boolean.parseBoolean(value);
                        break;
                    case "Can Revive":
                        this.canRevive = Boolean.parseBoolean(value);
                        break;
                    case "Item Spawn Rate":
                        this.itemSpawnRate = Integer.parseInt(value);
                        break;
                    case "Inventory":
                        this.inventory = stringToInventory(value);
                        break;
                    case "Equipped Keepsake":
                        // Assuming you have a method to parse or find an item by its string representation
                        this.equippedKeepsake = value.equals("None") ? null : findItemByName(value);
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("An error occurred while loading player data.");
            e.printStackTrace();
        }
    }

    private List<Item> stringToInventory(String inventoryStr) {
        List<Item> inventory = new ArrayList<>();
        if (!inventoryStr.equals("Empty")) {
            String[] items = inventoryStr.split(", ");
            for (String itemName : items) {
                // Assuming you have a method to parse or find an item by its string representation
                Item item = findItemByName(itemName.trim());
                if (item != null) {
                    inventory.add(item);
                }
            }
        }
        return inventory;
    }

    private Item findItemByName(String name) {
        // Implementation to find and return the item by its name
        return null; // Placeholder return
    }
}

