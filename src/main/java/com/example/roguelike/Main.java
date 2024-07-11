package com.example.roguelike;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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


//TODO Link all classes with corresponding ui elements
//TODO Polish UI elements (make it actually look nice)
//TODO code the combat
//TODO Randomize items on reward
