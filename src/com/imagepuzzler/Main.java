package com.imagepuzzler;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("com/imagepuzzler/Main.fxml"));
        root.getStylesheets().add("image_puzzler_style.css");
        primaryStage.setTitle("Image Puzzler");
        primaryStage.setScene(new Scene(root, 850, 850));
        primaryStage.show();
    }
}
