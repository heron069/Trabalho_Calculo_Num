package com.calculo.trabV1.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class HomeView extends VBox {

    public HomeView() {
        setAlignment(Pos.CENTER);
        setSpacing(20);
        setPadding(new Insets(20));

        Label lblTitulo = new Label("Sistema Integrado de Métodos Numéricos");
        lblTitulo.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Button btnA = criarBotaoTopico("A) Métodos para determinação de raízes de funções", "A");
        Button btnB = criarBotaoTopico("B) Sistemas de equações lineares", "B");
        Button btnC = criarBotaoTopico("C) Interpolação polinomial", "C");
        Button btnD = criarBotaoTopico("D) Integração numérica", "D");

        getChildren().addAll(lblTitulo, btnA, btnB, btnC, btnD);
    }

    private Button criarBotaoTopico(String texto, String letra) {
        Button btn = new Button(texto);
        btn.setPrefWidth(450);
        btn.setPrefHeight(4550);
        btn.setStyle("-fx-font-size: 14px; -fx-padding: 10px;");

        btn.setOnAction(e -> MainApp.mudarTela(new SubtopicosView(letra)));
        return btn;
    }
}