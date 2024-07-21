package com.example.roguelike;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
        stage.setTitle("Roguelike Game");
        stage.setScene(new Scene(loader.load(), 800, 600));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}


//TODO Polish UI elements (make it actually look nice)

//TODO grid pane for the inventory catalog in the main menu, 
// add a new variable to each item for ifOwned, and if true, display the item fully, else, display the item as a shadowed image
// add a button to the catalog screen to go back to the main menu

//TODO pick 5 items to be keepsakes, and if owned is true, be able to equip at the start of the run
//
