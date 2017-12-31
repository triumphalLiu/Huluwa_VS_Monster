package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("sample.fxml"));
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Huluwa VS Monster");
        Scene scene = new Scene(root,1000,600);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
        primaryStage.setResizable(false);
        Controller controller = fxmlLoader.getController();
        controller.InitController();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}