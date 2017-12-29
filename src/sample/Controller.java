package sample;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Controller {
    @FXML
    private ImageView backgroundImageView;

    @FXML
    private ImageView startButton;

    @FXML
    private ImageView historyButton;

    @FXML
    private ImageView exitButton;

    @FXML
    private ImageView aboutButton;

    @FXML
    void clickStartButton(MouseEvent event){
        System.exit(1);
    }

    @FXML
    void clickHistoryButton(MouseEvent event){
        System.exit(2);
    }

    @FXML
    void clickAboutButton(MouseEvent event){
        System.exit(3);
    }

    @FXML
    void clickExitButton(MouseEvent event){
        System.exit(0);
    }
}
