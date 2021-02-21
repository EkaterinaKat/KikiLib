package com.katyshevtseva.collage.test;

import com.katyshevtseva.fx.WindowBuilder;
import javafx.application.Application;
import javafx.stage.Stage;

public class CollageTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        new WindowBuilder("/com/katyshevtseva/collage/test/collage.fxml").setHeight(1000).setWidth(1000)
                .setController(new Controller())
                .setCursorImagePath("/cursor1.png").showWindow();
    }
}
