package com.example.roguelike;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class VictoryScreenController {
    @FXML
    private Button continueButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private GridPane itemsGrid;

    @FXML
    public void initialize() {
        continueButton.setOnAction(e -> {
            continueButton.setOnAction(event -> returnToCombatScreen());
        });

        mainMenuButton.setOnAction(e -> {
            mainMenuButton.setOnAction(event -> returnToMainMenu());
        });

        //display items from player's inventory
        Player player = new Player("playerData.txt");
        ItemManager itemManager = new ItemManager();
        for(int i = 0; i < player.getInventory().size(); i++){
            ImageView itemImage = new ImageView(); 
            itemImage.setImage(itemManager.getImageForItem(player.getInventory().get(i)));
            itemImage.setFitWidth(50);
            itemImage.setFitHeight(50);
            itemsGrid.add(itemImage, i, 0);
        }
    }

    private void returnToCombatScreen() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.setScene(scene);
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    private void returnToMainMenu() {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) mainMenuButton.getScene().getWindow();
            stage.setScene(scene);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
