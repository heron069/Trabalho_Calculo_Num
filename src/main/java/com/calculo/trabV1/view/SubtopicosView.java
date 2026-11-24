package com.calculo.trabV1.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SubtopicosView extends VBox{

    public SubtopicosView(String letraTopico){
        setAlignment(Pos.CENTER);
        setSpacing(15);
        setPadding(new Insets(20));

        Label lblTitulo = new Label();
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox boxBotoes = new VBox(10);
        boxBotoes.setAlignment(Pos.CENTER);

        switch (letraTopico){
            case "A":
                lblTitulo.setText("A) Raízes de Funções");
                adicionarSubtopico(boxBotoes, "1. Método da Bissecção");
                adicionarSubtopico(boxBotoes, "2. Método de Newton-Raphson");
                adicionarSubtopico(boxBotoes, "3. Método da Secante");
                adicionarSubtopico(boxBotoes, "4. Método da Falsa Posição (Regula Falsi)");
                break;
            case "B":
                lblTitulo.setText("B) Sistemas de Equações Lineares");
                adicionarSubtopico(boxBotoes, "1. Método da Eliminação de Gauss");
                adicionarSubtopico(boxBotoes, "2. Método da fatoração LU");
                adicionarSubtopico(boxBotoes, "3. Método de Gauss-Seidel");
                break;
            case "C":
                lblTitulo.setText("C) Interpolação Polinomial");
                adicionarSubtopico(boxBotoes, "1. Interpolação pela Forma de Lagrange");
                adicionarSubtopico(boxBotoes, "2. Interpolação pela Forma de Newton");
                break;
            case "D":
                lblTitulo.setText("D) Integração Numérica");
                adicionarSubtopico(boxBotoes, "1. Regra do Trapézio Repetida");
                adicionarSubtopico(boxBotoes, "2. Regra 1/3 de Simpson Repetida");
                adicionarSubtopico(boxBotoes, "3. Caso Discreto (Dados do usuário)");
                break;
        }

        Button btnVoltar = new Button("← Voltar ao Menu Principal");
        btnVoltar.setOnAction(e -> MainApp.mudarTela(new HomeView()));
        btnVoltar.setStyle("-fx-background-color: #ddd;");

        getChildren().addAll(lblTitulo, boxBotoes, btnVoltar);
    }

    private void adicionarSubtopico(VBox container, String nomeSubtopico){
        Button btn = new Button(nomeSubtopico);
        btn.setPrefWidth(400);
        btn.setOnAction(e -> MainApp.mudarTela(new MetodoInterfaceView(nomeSubtopico)));
        container.getChildren().add(btn);
    }
}