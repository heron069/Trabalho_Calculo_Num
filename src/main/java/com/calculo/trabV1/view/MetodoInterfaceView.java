package com.calculo.trabV1.view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MetodoInterfaceView extends VBox {

    public MetodoInterfaceView(String nomeMetodo) {
        setSpacing(10);
        setPadding(new Insets(20));

        Label lblTitulo = new Label(nomeMetodo);
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        grid.add(new Label("Parâmetro / Função f(x):"), 0, 0);
        grid.add(new TextField(), 1, 0);
        grid.add(new Label("Valor Inicial / Intervalo:"), 0, 1);
        grid.add(new TextField(), 1, 1);
        grid.add(new Label("Tolerância / Precisão:"), 0, 2);
        grid.add(new TextField("0.0001"), 1, 2);

        Button btnExecutar = new Button("Executar Cálculo");
        btnExecutar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        TextArea txtResultado = new TextArea();
        txtResultado.setEditable(false);
        txtResultado.setPromptText("As iterações, valores intermediários e resultados finais aparecerão aqui...");
        txtResultado.setPrefHeight(200);

        btnExecutar.setOnAction(e -> {
            txtResultado.setText(">>> Executando " + nomeMetodo + "...\n[Interface]: Dados lidos e validados com sucesso.\nIteração 1: ...\nResultado final exibido com sucesso.");
        });

        Button btnVoltar = new Button("← Voltar aos Subtópicos");
        btnVoltar.setOnAction(e -> {
            String letra = nomeMetodo.substring(0, 1);
            MainApp.mudarTela(new SubtopicosView(letra));
        });

        HBox barraInferior = new HBox(10, btnExecutar, btnVoltar);

        getChildren().addAll(lblTitulo, grid, barraInferior, new Label("Resultados e Iterações:"), txtResultado);
    }
}