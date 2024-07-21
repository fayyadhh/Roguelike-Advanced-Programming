package com.example.roguelike;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;

public class ItemManager {
    private List<Item> commonItems;
    private List<Item> uncommonItems;
    private List<Item> rareItems;
    private List<Item> legendaryItems;

    private List<Item> allItems;
    private String filePath = "items.txt";
    private File file = new File(filePath);

    public ItemManager(){
        commonItems = new ArrayList<>();
        uncommonItems = new ArrayList<>();
        rareItems = new ArrayList<>();
        legendaryItems = new ArrayList<>();

        if(file.exists() && file.length() > 0){
            loadItems();

            allItems = new ArrayList<>();
            allItems.addAll(commonItems);
            allItems.addAll(uncommonItems);
            allItems.addAll(rareItems);
            allItems.addAll(legendaryItems);
        } else {
            initializeItems();
        }

        
    }

    public void initializeItems(){
        //Adding the common items and stuff
        commonItems.add(new Item("Leather Chestplate", "Common", "/ItemAssets/ChainmailArmor.png", "Defense +5"));
        commonItems.add(new Item("Dull Dagger", "Common", "/ItemAssets/DullDagger.png", "Attack Power +5"));
        commonItems.add(new Item("Hearty Amulet", "Common", "/ItemAssets/HeartyAmulet.png", "Max Health +10"));
        commonItems.add(new Item("Glasses", "Common", "/ItemAssets/Glasses.png", "Makes critical hits easier to land"));
        commonItems.add(new Item("Potion", "Common", "/ItemAssets/Potion.png", "Heal 20 health"));
        commonItems.add(new Item("Whetstone", "Common", "/ItemAssets/Whetstone.png", "Makes critical hits stronger"));

        //Adding the uncommon items blah blah
        uncommonItems.add(new Item("Chainmail Armor", "Uncommon", "/ItemAssets/ChainmailArmor.png", "Defense +10"));
        uncommonItems.add(new Item("Knight Sword", "Uncommon", "/ItemAssets/KnightSword.png", "Attack Power +10"));
        uncommonItems.add(new Item("Magic Charm", "Uncommon", "/ItemAssets/MagicCharm.png", "Defense +5, Max Health +20"));
        uncommonItems.add(new Item("Ninja Belt", "Uncommon", "/ItemAssets/NinjaBelt.png", "Makes critical hits a lot stronger"));
        uncommonItems.add(new Item("Gambler’s Dice", "Uncommon", "/ItemAssets/GamblersDice.png", "Increases the chance of finding rare items"));
        uncommonItems.add(new Item("Spikes", "Uncommon", "/ItemAssets/Spike.png", "Reflects some damage back to the enemy"));

        //rares
        rareItems.add(new Item("Holy Armor", "Rare", "/ItemAssets/HolyArmor.png", "Defense +15"));
        rareItems.add(new Item("Sniper Lens", "Rare", "/ItemAssets/SniperLens.png", "Makes critical hits a lot stronger"));
        rareItems.add(new Item("Vampiric Sword", "Rare", "/ItemAssets/VampiricSword.png", "Attack Power +15, Life Steal"));
        rareItems.add(new Item("Spiky Shield", "Rare", "/ItemAssets/SpikyShield.png", "Reflects most damage back to the enemy"));

        //legendary :100::100::100:
        legendaryItems.add(new Item("Light Speed Bracelet", "Legendary", "/ItemAssets/LightSpeedBracelet.png", "Allows for an extra action in combat"));
        legendaryItems.add(new Item("After Image", "Legendary", "/ItemAssets/AfterImage.png", "Attacks twice per turn"));
        legendaryItems.add(new Item("Holy Concoction", "Legendary", "/ItemAssets/HolyConcoction.png", "Increases attack, defense, and health"));
        legendaryItems.add(new Item("Totem of Rebirth", "Legendary", "/ItemAssets/TotemofRebirth.png", "Revives the player upon death, removed on use"));
        
        //side note: thank heavens for intellisense holy heck
        //actual meaningful sidenote: please work || side side side note: IT WORKS !!!! 
        allItems = new ArrayList<>();
        allItems.addAll(commonItems);
        allItems.addAll(uncommonItems);
        allItems.addAll(rareItems);
        allItems.addAll(legendaryItems);

        saveItems();
    }

    public List<Item> generateRandomItems() {
        List<Item> randomItems = new ArrayList<>();
        Set<Item> addedItems = new HashSet<>(); //makes use of a hashset to ensure no duplicates :100:

        while (randomItems.size() < 5) {
            int random = (int) (Math.random() * 100);
            Item selectedItem = null;
            if (random < 50) { // 50% for common
                selectedItem = commonItems.get((int) (Math.random() * commonItems.size()));
            } else if (random < 80) { // 30% for uncommon
                selectedItem = uncommonItems.get((int) (Math.random() * uncommonItems.size()));
            } else if (random < 95) { // 15% for rare
                selectedItem = rareItems.get((int) (Math.random() * rareItems.size()));
            } else { // 5% for legendary
                selectedItem = legendaryItems.get((int) (Math.random() * legendaryItems.size()));
            }

            if (addedItems.add(selectedItem)) { //returns true if this set did not already contain the specified element
                randomItems.add(selectedItem);
            }
        }

        return randomItems;
    }

    public List<Item> getAllItems(){
        return allItems;
    }

    public String getImagePath(Item item){
        return item.getFilePath();
    }
    
    public Image getImageForItem(Item item){
        String imagePath = item.getFilePath();
        URL resourceUrl = getClass().getResource(imagePath);
        if (resourceUrl == null) {
            throw new IllegalArgumentException("Resource not found: " + imagePath);
        }
        return new Image(resourceUrl.toExternalForm());
    }

    public Item findItemByName(String string){
        for (Item item : commonItems){
            if (item.getName().equals(string)){
                return item;
            }
        }
        for (Item item : uncommonItems){
            if (item.getName().equals(string)){
                return item;
            }
        }
        for (Item item : rareItems){
            if (item.getName().equals(string)){
                return item;
            }
        }
        for (Item item : legendaryItems){
            if (item.getName().equals(string)){
                return item;
            }
        }
        return null;
    }

    public String getEffectForItem(Item item){
        return item.getEffect();
    }

    //save items to a file to keep track of whether each item is owned or not
    public void saveItems(){
        String fileName = "items.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Item item : allItems) {
                writer.write(item.getName() + "," + item.getRarity() + "," + item.getFilePath() + "," + item.getEffect() + "," + item.getIsOwned());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadItems(){
        //TODO
        String fileName = "items.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line = reader.readLine();
            while (line != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String name = parts[0];
                    String rarity = parts[1];
                    String filePath = parts[2];
                    String effect = parts[3];
                    boolean isOwned = Boolean.parseBoolean(parts[4]);

                    Item item = findItemByName(name);
                    if (item == null) {
                        // Assuming a constructor that takes all properties
                        item = new Item(name, rarity, filePath, effect);
                        switch (rarity) {
                            case "Common":
                                commonItems.add(item);
                                break;
                            case "Uncommon":
                                uncommonItems.add(item);
                                break;
                            case "Rare":
                                rareItems.add(item);
                                break;
                            case "Legendary":
                                legendaryItems.add(item);
                                break;
                        }
                    }
                    if (isOwned) { item.setIsOwned(); } //just to set if owned, because it is not owned by default
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
