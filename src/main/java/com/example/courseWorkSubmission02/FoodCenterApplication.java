package com.example.courseWorkSubmission02;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FoodCenterApplication extends Application {
    Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage; // assign the stage parameter to the static variable
        FXMLLoader fxmlLoader = new FXMLLoader(FoodCenterApplication.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Food Center Program");
        stage.setScene(scene);
        stage.show(); // show the new stage
    }

    public static void launchApplication(){
        launch();
    }


}
