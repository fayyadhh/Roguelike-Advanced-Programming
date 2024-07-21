
package com.example.roguelike;

public class Item {
    private String name;
    // private double price; 
    private String effect; 
    //this effect variable doesnt need to be used, since why do we have a string telling the effect when we're just searching for the item name to give the effect anyways
    //nvm i found a use for it :D

    private String rarity;
    private String filePath;

    public Item(String name,String rarity, String filePath, String effect) {
        this.name = name;
        this.rarity = rarity;
        this.filePath = filePath;
        this.effect = effect;
    }

    // Method to apply the effect of the item to the player
    //i think this is an ineffecient method, but we roll with it :100:
    public void applyEffect(Player player, Item item) {
        switch (item.getName()) {
            case "Leather Chestplate":
                player.setDefense(player.getDefense() + 5);
                break;
            case "Dull Dagger":
                player.setAttackPower(player.getAttackPower() + 5);
                break;
            case "Hearty Amulet":
                player.setMaxHealth(player.getMaxHealth() + 10);
                break;
            case "Glasses":
                player.setCritMultiplier(player.getCritMultiplier() + 0.1);
                break;
            case "Potion":
                player.heal(20);
                break;
            case "Whetstone":
                player.setCriticalDamageMultiplier(player.getCriticalDamageMultiplier() + 0.5);
                break;
            case "Chainmail Armor":
                player.setDefense(player.getDefense() + 10);
                break;
            case "Knight Sword":
                player.setAttackPower(player.getAttackPower() + 10);
                break;
            case "Magic Charm":
                player.setDefense(player.getDefense() + 5);
                player.setMaxHealth(player.getMaxHealth() + 20);
                break;
            case "Ninja Belt":
                player.setCritMultiplier(player.getCritMultiplier() + 20);
                break;
            case "Gambler’s Dice":
                player.setItemSpawnRate(player.getItemSpawnRate() + 5); //TODO implement increase percentage chance of item spawn
                break;
            case "Spikes":
                player.setReflectDamage(2);
                break;
            case "Holy Armor":
                player.setHealPerTurn(player.getHealPerTurn() + 10);
                player.setDefense(player.getDefense() + 5);
                break;
            case "Vampiric Sword":
                player.setAttackPower(player.getAttackPower() + 10);
                player.setLifeSteal(true);
                break;
            case "Sniper Lens":
                player.setCritMultiplier(player.getCritMultiplier() + 30);
                player.setCriticalDamageMultiplier(player.getCriticalDamageMultiplier() + 3);
                break;
            case "Spiky Shield":
                player.setReflectDamage(player.getAttackPower());
                break;
            case "Light Speed Bracelet":
                player.setExtraActionsTrue();
                break;
            case "After Image":
                player.setDoubleAttack(true);
                break;
            case "Holy Concoction":
                player.setDefense(player.getDefense() + 20);
                player.setAttackPower(player.getAttackPower() + 10);
                player.setMaxHealth(player.getMaxHealth() + 50);
                break;
            case "Totem of Rebirth":
                player.setCanRevive(true);
                break;
            default:
                System.out.println("Unknown item effect");
        }
    }

    // @Override
    // public String toString() {
    //     return "Item{" +
    //             "name='" + name + '\'' +
    //             ", effect='" + effect + '\'' +
    //             '}';
    // }

    public String getName() {
        return name;
    }

    // public double getPrice() {
    //     return price;
    // }

    public void setName(String name) {
        this.name = name;
    }

    // public void setPrice(double price) {
    //     this.price = price;
    // }


    public String getRarity() {
        return rarity;
    }

    public String getEffect() {
        return effect;
    }


    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    // public void setEffect(String effect) {
    //     this.effect = effect;
    // }

    public String getFilePath() {
        return filePath;
    }
}
