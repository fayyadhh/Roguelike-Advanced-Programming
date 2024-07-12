
package com.example.roguelike;

public class Item {
    private String name;
    private double price;
    private String effect;
    private String rarity;

    public Item(String name,String rarity, double price,String effect) {
        this.name = name;
        this.price = price;
        this.effect = effect;
        this.rarity = rarity;
    }
    // Method to apply the effect of the item to the player
    public void applyEffect(Player player) {
        switch (name) {
            case "Leather Chestplate":
                player.setDefense(player.getDefense() + 5);
                break;
            case "Dull Dagger":
                player.setAttackPower(player.getAttackPower() + 5);
                break;
            case "Hearty Amulet":
                player.setHealth(player.getHealth() + 10);
                break;
            case "Glasses":
                player.setCriticalAreaSize(player.getCriticalAreaSize() + 10);
                break;
            case "Potion":
                player.setHealth(player.getHealth() + 20);
                break;
            case "Whetstone":
                player.setCriticalDamageMultiplier(player.getCriticalDamageMultiplier() + 0.05);
                break;
            case "Chainmail Armor":
                player.setDefense(player.getDefense() + 10);
                break;
            case "Knight Sword":
                player.setAttackPower(player.getAttackPower() + 10);
                break;
            case "Magic Charm":
                player.setDefense(player.getDefense() + 5);
                player.setHealth(player.getHealth() + 20);
                break;
            case "Ninja Belt":
                player.setCriticalAreaSize(player.getCriticalAreaSize() + 20);
                break;
            case "Gambler’s Dice":
                player.setItemSpawnRate(player.getItemSpawnRate() + 5);
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
                player.setCriticalAreaSize(player.getCriticalAreaSize() + 30);
                player.setCriticalDamageMultiplier(player.getCriticalDamageMultiplier() + 0.1);
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
                player.setHealth(player.getHealth() + 50);
                break;
            case "Totem of Rebirth":
                player.setCanRevive(true);
                break;
            default:
                System.out.println("Unknown item effect");
        }
    }
    @Override
    public String toString() {
        return "Item{" +
                "name='" + name + '\'' +
                ", effect='" + effect + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public String getRarity() {
        return rarity;
    }

    public String getEffect() {
        return effect;
    }


    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
}
