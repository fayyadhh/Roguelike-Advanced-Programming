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

    //local variable for reward money
    //this is just a placeholder for now. to be linked with the money reward
    public int rewardMoney;

    public void initialize(){
        //Handle what the screen will show upon completion
        rewardMoneyIndicator.setText("$" + Integer.toString(rewardMoney));
        //Setting the images for the items
        //item1.setImage();
        //TODO make it so that the item image will show the correct item that is available.. idk how to do that yet but this is a placeholder !!!!


        //Handling what happens when the items get clicked
        item1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(item1));
        item2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(item2));
        item3.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(item3));
        item4.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(item4));
        item5.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> itemChoose(item5));

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

    private void itemChoose(ImageView item) {
        //add the chosen image to the player inventory and move on to the next screen

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
