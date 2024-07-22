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
    @FXML
    private ImageView cross1, cross2, cross3, cross4, cross5;

    private Level level = new Level();

    public void initialize(){
        goNext.setOnAction(event -> goToCombat());

        cross1.setVisible(false);
        cross2.setVisible(false);
        cross3.setVisible(false);
        cross4.setVisible(false);
        cross5.setVisible(false);

        switch(level.getLevel() % 5){
            case 2:
                cross1.setVisible(true);
                break;
            case 3:
                cross1.setVisible(true);
                cross2.setVisible(true);
                break;
            case 4:
                cross1.setVisible(true);
                cross2.setVisible(true);
                cross3.setVisible(true);
                break;
            case 0:
                cross1.setVisible(true);
                cross2.setVisible(true);
                cross3.setVisible(true);
                cross4.setVisible(true);
                break;
            //no case for 1, because that would be the first level in the sequence and no levels would be crossed out
        }
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
