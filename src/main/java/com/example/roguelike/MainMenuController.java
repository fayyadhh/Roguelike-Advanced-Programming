package com.example.roguelike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    private Button startButton;
    @FXML
    private Button continueButton;
    @FXML
    private Button catalogButton;
    @FXML
    private Button optionsButton;
    @FXML
    private Button exitButton;

    //catalog container and stuff
    @FXML
    private ScrollPane catalogContainer;
    @FXML
    private GridPane itemsCatalogPane;
    @FXML
    private Button closeItemsContainer;

    Player player;
    Level level = new Level();
    Item item;
    ItemManager itemManager = new ItemManager();
    
    @FXML
    public void initialize(){
        startButton.setOnAction(event -> startGame());
        catalogButton.setOnAction(event -> openCatalog());
        optionsButton.setOnAction(event -> showOptions());
        exitButton.setOnAction(event -> exitGame());

        //catalog button
        catalogContainer.setVisible(false);
        closeItemsContainer.setVisible(false);
        closeItemsContainer.setOnAction(event -> catalogContainer.setVisible(false));


        //continue button logic is here
        if(!(level.checkIfDead()) && (level.getLevel() > 1)){ //if not dead and its not level 1
            //change the opacity first
            continueButton.setOpacity(1);
            continueButton.setDisable(false);
            continueButton.setOnAction(event -> continueGame());
            
        } else {
            //continue button should be less opaque if there is no save data
            continueButton.setOpacity(0.5);
            continueButton.setDisable(true);
        }
    }

    private void exitGame() {
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    private void showOptions() {
        //Options Menu Code Here
    }

    private String generateToolTipText(Item item){
        if (item.getIsOwned()){
            return String.format("%s\n%s\n%s", item.getName(), item.getRarity(), item.getEffect());
        } else{
            return "Unknown Item";
        }
    }

    private void openCatalog() {
        //displays every item available in the grid pane.
        //using the item getIsOwned method, we can determine if the item is owned or not
        //if the item is owned, we can display the item in full color, otherwise, we can display the item as a silhouette

        //toggle the visibility of the catalog container
        catalogContainer.setVisible(true);
        closeItemsContainer.setVisible(true);

        //clear the grid pane first
        itemsCatalogPane.getChildren().clear();

        //get the list of items
        for(int i = 0; i < itemManager.getAllItems().size(); i++){
            item = itemManager.getAllItems().get(i);
            ImageView itemButton = new ImageView(itemManager.getImageForItem(item));
            itemButton.setFitWidth(50);
            itemButton.setFitHeight(50);
            
            //if the item is Not owned, display the item as a sillhouette
            if(!item.getIsOwned()){
                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(-1); //using a color adjust to adjust the color LOL
                itemButton.setEffect(colorAdjust);
            }

            //set the tooltip for the item
            Tooltip itemTooltip = new Tooltip(generateToolTipText(item));
            Tooltip.install(itemButton, itemTooltip);

            itemsCatalogPane.add(itemButton, i % 5, i / 5);
        }

        //create tooltips for owned items
        //if the item is owned, display the item name, rarity and effect
        //if the item is not owned, display "unknown item"

    }

    private void continueGame() {
        //Continue Game Code Here
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MapScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void startGame() {
        //Start Game Code Here
        player = new Player();
        player.savePlayerData();

        level.resetForNewGame();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StartGameScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) startButton.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
