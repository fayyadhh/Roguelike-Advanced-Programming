package com.example.roguelike;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class MapScreenController {
    @FXML
    private ImageView mapImage;
    @FXML
    private Button goNext;
    @FXML
    private Text instructionText;

    public void initialize(){
        goNext.setOnAction(event -> goToCombat());
    }

    public void goToCombat(){
        //Go To Combat Screen
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CombatScreen.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) goNext.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e){
            e.printStackTrace();
        }

        //Test
        //System.out.println("Clicked!");
    }
}
