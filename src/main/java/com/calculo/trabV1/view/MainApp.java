package com.calculo.trabV1.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static StackPane rootContainer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Trabalho de Métodos Numéricos");

        rootContainer = new StackPane();

        mudarTela(new HomeView());

        Scene scene = new Scene(rootContainer, 800, 550);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void mudarTela(javafx.scene.Node novaTela) {
        rootContainer.getChildren().clear();
        rootContainer.getChildren().add(novaTela);
    }

    public static void main(String[] args) {
        launch(args);
    }
}