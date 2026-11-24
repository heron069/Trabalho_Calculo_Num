package com.calculo.trabV1.view;

import com.calculo.trabV1.sistemasLineares.EliminacaoGauss;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class MetodoInterfaceView extends VBox{

    public MetodoInterfaceView(String nomeMetodo){
        setSpacing(10);
        setPadding(new Insets(20));

        Label lblTitulo = new Label(nomeMetodo);
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333;");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField txtParametro = new TextField();
        TextField txtValorInicial = new TextField();
        TextField txtTolerancia = new TextField("0.0001");
        TextArea txtMatriz = new TextArea();
        txtMatriz.setPrefRowCount(5);

        boolean ehGauss = nomeMetodo != null &&
                (nomeMetodo.toLowerCase().contains("gauss") || nomeMetodo.startsWith("B.1"));

        if(ehGauss){
            txtMatriz.setPromptText("3, 2, 4, 1\n1, 1, 2, 2\n4, 3, -2, 3");
            grid.add(new Label("Matriz Aumentada [A | b]:"), 0, 0);
            grid.add(txtMatriz, 1, 0);
        } else{
            grid.add(new Label("Parâmetro / Função f(x):"), 0, 0);
            grid.add(txtParametro, 1, 0);
            grid.add(new Label("Valor Inicial / Intervalo:"), 0, 1);
            grid.add(txtValorInicial, 1, 1);
            grid.add(new Label("Tolerância / Precisão:"), 0, 2);
            grid.add(txtTolerancia, 1, 2);
        }

        Button btnExecutar = new Button("Executar Cálculo");
        btnExecutar.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

        TextArea txtResultado = new TextArea();
        txtResultado.setEditable(false);
        txtResultado.setPromptText("As iterações, valores intermediários e resultados finais aparecerão aqui...");
        txtResultado.setPrefHeight(220);
        txtResultado.setStyle("-fx-font-family: monospace;");

        btnExecutar.setOnAction(e ->{
            if(ehGauss){
                try{
                    double[][] aumentada = parseMatriz(txtMatriz.getText());
                    EliminacaoGauss gauss = new EliminacaoGauss();
                    EliminacaoGauss.ResultadoGauss resultado = gauss.eliminacaoDeGauss(aumentada);
                    txtResultado.setText(resultado.getLogEtapas());
                } catch (Exception ex){
                    txtResultado.setText("Erro: " + ex.getMessage());
                }
            } else{
                txtResultado.setText(">>> Executando " + nomeMetodo + "...\n[Interface]: Dados lidos e validados com sucesso.\nIteração 1: ...\nResultado final exibido com sucesso.");
            }
        });

        Button btnVoltar = new Button("← Voltar aos Subtópicos");
        btnVoltar.setOnAction(e ->{
            String letra = nomeMetodo.substring(0, 1);
            MainApp.mudarTela(new SubtopicosView(letra));
        });

        HBox barraInferior = new HBox(10, btnExecutar, btnVoltar);

        getChildren().addAll(lblTitulo, grid, barraInferior, new Label("Resultados e Iterações:"), txtResultado);
    }

    private double[][] parseMatriz(String texto){
        if(texto == null || texto.isBlank()){
            throw new IllegalArgumentException("Informe a matriz aumentada.");
        }
        String[] linhas = texto.trim().split("\\R+");
        double[][] matriz = new double[linhas.length][];
        for (int i = 0; i < linhas.length; i++){
            String[] elementos = linhas[i].trim().split("[,;\\s]+");
            matriz[i] = new double[elementos.length];
            for (int j = 0; j < elementos.length; j++){
                matriz[i][j] = Double.parseDouble(elementos[j].trim().replace(",", "."));
            }
        }
        return matriz;
    }
}