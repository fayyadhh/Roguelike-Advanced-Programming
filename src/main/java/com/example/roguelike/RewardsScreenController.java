package com.example.roguelike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;

public class RewardsScreenController {
    @FXML
    private Text rewardMoneyIndicator;
    @FXML
    private Text totalMoneyIndicator;
    @FXML
    private ImageView item1;
    @FXML
    private ImageView item2;
    @FXML
    private ImageView item3;
    @FXML
    private ImageView item4;
    @FXML
    private ImageView item5;
    private List<ImageView> itemImageViews;

    @FXML
    private Button rerollButton;
    @FXML
    private Button skipButton;
    @FXML
    private Text tooPoorIndicator;

    //instance of the item manager class
    private ItemManager itemManager = new ItemManager();

    //instance of the player class using the filename constructor
    private Player player = new Player("playerData.txt");

    //instance of level class
    private Level level = new Level();

    //local variable for reward money
    //this is just a placeholder for now. to be linked with the money reward
    public int rewardMoney;
    private int rerollCount = 0;
    private int rerollCost = 5 + rerollCount; 

    @FXML
    public void initialize(){
        //Handle what the screen will show upon completion
        rewardMoney = level.getRewardMoney();
        rewardMoneyIndicator.setText("$" + Integer.toString(rewardMoney));
        player.setMoney(rewardMoney + player.getMoney());
        totalMoneyIndicator.setText("Total Money: $" + Integer.toString(player.getMoney()));
        tooPoorIndicator.setVisible(false);

        //show how much rerolls cost
        rerollButton.setText("Reroll Items: $" + 5);

        //genearte tha random items
        List<Item> randomItems = itemManager.generateRandomItems();


        //Setting the images for the items
        item1.setImage(itemManager.getImageForItem(randomItems.get(0)));
        item2.setImage(itemManager.getImageForItem(randomItems.get(1)));
        item3.setImage(itemManager.getImageForItem(randomItems.get(2)));
        item4.setImage(itemManager.getImageForItem(randomItems.get(3)));
        item5.setImage(itemManager.getImageForItem(randomItems.get(4)));
        itemImageViews = List.of(item1, item2, item3, item4, item5);

        //Handling what happens when the items get clicked
        item1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(0)));
        item2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(1)));
        item3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(2)));
        item4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(3)));
        item5.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(4)));

        //tooltips for items
        for(int i = 0; i < randomItems.size(); i++){
            Item currentItem = randomItems.get(i);
            ImageView currentItemView = itemImageViews.get(i);

            String toolTipText = String.format("%s\n%s\n%s", currentItem.getName(), currentItem.getRarity(), currentItem.getEffect());
            Tooltip currenTooltip = new Tooltip(toolTipText);
            Tooltip.install(currentItemView, currenTooltip);
            currenTooltip.setShowDelay(Duration.ZERO);
        }


        //Buttons
        rerollButton.setOnAction(event -> rerollItems());
        skipButton.setOnAction(event -> skipItems());
    }

    private void skipItems() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) rerollButton.getScene().getWindow();
            stage.setScene(scene);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void rerollItems() {
        //reroll will cost more per reroll
        
        if(player.getMoney() >= rerollCost){
            //player money update
            player.setMoney(player.getMoney() - rerollCost);
            player.savePlayerData();

            //change the reroll button to show the cost
            rerollButton.setText("Reroll Items: $" + rerollCost);
            totalMoneyIndicator.setText("Total Money: $" + Integer.toString(player.getMoney()));

            //regenerate
            List<Item> randomItems = itemManager.generateRandomItems();

            //Setting the images for the items
            item1.setImage(itemManager.getImageForItem(randomItems.get(0)));
            item2.setImage(itemManager.getImageForItem(randomItems.get(1)));
            item3.setImage(itemManager.getImageForItem(randomItems.get(2)));
            item4.setImage(itemManager.getImageForItem(randomItems.get(3)));
            item5.setImage(itemManager.getImageForItem(randomItems.get(4)));

            rerollCount++;
            //hopefully this works
        } else{
            //if the player does not have enough money
            tooPoorIndicator.setText("Not enough money to reroll");
            tooPoorIndicator.setVisible(true);
        }
    }

    private void itemChoose(Player player, Item item) {
        //add the chosen image to the player inventory and move on to the next screen
        player.addItemToInventory(item);
        player.savePlayerData();

        //Change screen
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) rerollButton.getScene().getWindow();
            stage.setScene(scene);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
