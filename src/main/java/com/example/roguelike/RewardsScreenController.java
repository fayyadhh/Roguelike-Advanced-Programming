package com.example.roguelike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RewardsScreenController {
    @FXML
    private Text rewardMoneyIndicator;
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
    @FXML
    private Button rerollButton;
    @FXML
    private Button skipButton;

    //instance of the item manager class
    private ItemManager itemManager = new ItemManager();

    //instance of the player class using the filename constructor
    private Player player = new Player("playerData.txt");

    //local variable for reward money
    //this is just a placeholder for now. to be linked with the money reward
    public int rewardMoney;

    public void initialize(){
        //Handle what the screen will show upon completion
        rewardMoneyIndicator.setText("$" + Integer.toString(rewardMoney));

        //genearte tha random items
        List<Item> randomItems = itemManager.generateRandomItems();


        //Setting the images for the items
        item1.setImage(itemManager.getImageForItem(randomItems.get(0)));
        item2.setImage(itemManager.getImageForItem(randomItems.get(1)));
        item3.setImage(itemManager.getImageForItem(randomItems.get(2)));
        item4.setImage(itemManager.getImageForItem(randomItems.get(3)));
        item5.setImage(itemManager.getImageForItem(randomItems.get(4)));

        //Handling what happens when the items get clicked
        item1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(0)));
        item2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(1)));
        item3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(2)));
        item4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(3)));
        item5.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(player, randomItems.get(4)));

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
