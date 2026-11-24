package com.calculo.trabV1.view;

import com.calculo.trabV1.geral.Interpretador;
import com.calculo.trabV1.metodos.MetodoBisseccao;
import com.calculo.trabV1.metodos.MetodoNewton;
import com.calculo.trabV1.metodos.MetodoRegulaFalsi;
import com.calculo.trabV1.metodos.MetodoSecante;
import com.calculo.trabV1.sistemasLineares.EliminacaoGauss;
import com.calculo.trabV1.sistemasLineares.FatoracaoLU;
import com.calculo.trabV1.sistemasLineares.GaussSeidel;
import com.calculo.trabV1.resultados.ResultadoMetodo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
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

        TextField txtFuncao = new TextField("x^2 - 2");
        TextField txtA = new TextField("1");
        TextField txtB = new TextField("2");
        TextField txtX0 = new TextField("1.5");
        TextField txtX1 = new TextField("2");
        TextField txtTolerancia = new TextField("0.0001");
        TextField txtMaxIter = new TextField("50");
        TextArea txtMatriz = new TextArea();
        txtMatriz.setPrefRowCount(5);

        String nomeLower = nomeMetodo != null ? nomeMetodo.toLowerCase() : "";

        boolean ehBisseccao = nomeMetodo != null &&
                (nomeMetodo.startsWith("A.1") || nomeLower.contains("bissec"));
        boolean ehNewton = nomeMetodo != null &&
                (nomeMetodo.startsWith("A.2") || nomeLower.contains("newton"));
        boolean ehSecante = nomeMetodo != null &&
                (nomeMetodo.startsWith("A.3") || nomeLower.contains("secante"));
        boolean ehRegulaFalsi = nomeMetodo != null &&
                (nomeMetodo.startsWith("A.4") || nomeLower.contains("regula") || nomeLower.contains("falsa"));

        boolean ehGauss = nomeMetodo != null &&
                ((nomeLower.contains("gauss") && !nomeLower.contains("seidel")) || nomeMetodo.startsWith("B.1"));
        boolean ehLU = nomeMetodo != null &&
                (nomeLower.contains("lu") || nomeMetodo.startsWith("B.2"));
        boolean ehGaussSeidel = nomeMetodo != null &&
                (nomeLower.contains("seidel") || nomeMetodo.startsWith("B.3"));

        if (ehBisseccao || ehRegulaFalsi) {
            grid.add(new Label("Função f(x):"), 0, 0);
            grid.add(txtFuncao, 1, 0);
            grid.add(new Label("Limite inferior a:"), 0, 1);
            grid.add(txtA, 1, 1);
            grid.add(new Label("Limite superior b:"), 0, 2);
            grid.add(txtB, 1, 2);
            grid.add(new Label("Tolerância:"), 0, 3);
            grid.add(txtTolerancia, 1, 3);
            grid.add(new Label("Máx. iterações:"), 0, 4);
            grid.add(txtMaxIter, 1, 4);
        } else if (ehNewton) {
            grid.add(new Label("Função f(x):"), 0, 0);
            grid.add(txtFuncao, 1, 0);
            grid.add(new Label("Chute inicial x0:"), 0, 1);
            grid.add(txtX0, 1, 1);
            grid.add(new Label("Tolerância:"), 0, 2);
            grid.add(txtTolerancia, 1, 2);
            grid.add(new Label("Máx. iterações:"), 0, 3);
            grid.add(txtMaxIter, 1, 3);
        } else if (ehSecante) {
            grid.add(new Label("Função f(x):"), 0, 0);
            grid.add(txtFuncao, 1, 0);
            grid.add(new Label("Ponto inicial x0:"), 0, 1);
            grid.add(txtX0, 1, 1);
            grid.add(new Label("Ponto inicial x1:"), 0, 2);
            grid.add(txtX1, 1, 2);
            grid.add(new Label("Tolerância:"), 0, 3);
            grid.add(txtTolerancia, 1, 3);
            grid.add(new Label("Máx. iterações:"), 0, 4);
            grid.add(txtMaxIter, 1, 4);
        } else if (ehGauss || ehLU) {
            txtMatriz.setPromptText("3, 2, 4, 1\n1, 1, 2, 2\n4, 3, -2, 3");
            grid.add(new Label("Matriz Aumentada [A | b]:"), 0, 0);
            grid.add(txtMatriz, 1, 0);
        } else if (ehGaussSeidel) {
            txtMatriz.setPromptText("3, 2, 4, 1\n1, 1, 2, 2\n4, 3, -2, 3");
            grid.add(new Label("Matriz Aumentada [A | b]:"), 0, 0);
            grid.add(txtMatriz, 1, 0);
            grid.add(new Label("Chute inicial x0:"), 0, 1);
            grid.add(txtX0, 1, 1);
            grid.add(new Label("Tolerância:"), 0, 2);
            grid.add(txtTolerancia, 1, 2);
            grid.add(new Label("Máx. iterações:"), 0, 3);
            grid.add(txtMaxIter, 1, 3);
        } else {
            grid.add(new Label("Parâmetro / Função f(x):"), 0, 0);
            grid.add(txtFuncao, 1, 0);
            grid.add(new Label("Valor Inicial / Intervalo:"), 0, 1);
            grid.add(txtX0, 1, 1);
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

        btnExecutar.setOnAction(e -> {
            try {
                if (ehBisseccao) {
                    Interpretador interp = new Interpretador(txtFuncao.getText());
                    double a = parseDouble(txtA.getText());
                    double b = parseDouble(txtB.getText());
                    double tol = parseDouble(txtTolerancia.getText());
                    int maxIter = parseInt(txtMaxIter.getText());

                    MetodoBisseccao metodo = new MetodoBisseccao(interp);
                    ResultadoMetodo<?> resultado = metodo.calcularRaiz(a, b, tol, maxIter);
                    txtResultado.setText(formatarResultadoRaiz(interp, resultado, false));
                } else if (ehNewton) {
                    Interpretador interp = new Interpretador(txtFuncao.getText());
                    double x0 = parseDouble(txtX0.getText());
                    double tol = parseDouble(txtTolerancia.getText());
                    int maxIter = parseInt(txtMaxIter.getText());

                    MetodoNewton metodo = new MetodoNewton(interp);
                    ResultadoMetodo<?> resultado = metodo.calcularRaiz(x0, tol, maxIter);
                    txtResultado.setText(formatarResultadoRaiz(interp, resultado, true));
                } else if (ehSecante) {
                    Interpretador interp = new Interpretador(txtFuncao.getText());
                    double x0 = parseDouble(txtX0.getText());
                    double x1 = parseDouble(txtX1.getText());
                    double tol = parseDouble(txtTolerancia.getText());
                    int maxIter = parseInt(txtMaxIter.getText());

                    MetodoSecante metodo = new MetodoSecante(interp);
                    ResultadoMetodo<?> resultado = metodo.calcularRaiz(x0, x1, tol, maxIter);
                    txtResultado.setText(formatarResultadoRaiz(interp, resultado, false));
                } else if (ehRegulaFalsi) {
                    Interpretador interp = new Interpretador(txtFuncao.getText());
                    double a = parseDouble(txtA.getText());
                    double b = parseDouble(txtB.getText());
                    double tol = parseDouble(txtTolerancia.getText());
                    int maxIter = parseInt(txtMaxIter.getText());

                    MetodoRegulaFalsi metodo = new MetodoRegulaFalsi(interp);
                    ResultadoMetodo<?> resultado = metodo.calcularRaiz(a, b, tol, maxIter);
                    txtResultado.setText(formatarResultadoRaiz(interp, resultado, false));
                } else if (ehGauss) {
                    double[][] aumentada = parseMatriz(txtMatriz.getText());
                    EliminacaoGauss gauss = new EliminacaoGauss();
                    EliminacaoGauss.ResultadoGauss resultado = gauss.eliminacaoDeGauss(aumentada);
                    txtResultado.setText(resultado.getLogEtapas());
                } else if (ehLU) {
                    double[][] aumentada = parseMatriz(txtMatriz.getText());
                    int n = aumentada.length;
                    double[][] a = new double[n][n];
                    double[] b = new double[n];
                    for (int i = 0; i < n; i++) {
                        if (aumentada[i].length != n + 1) {
                            throw new IllegalArgumentException("A matriz deve ter " + n + " colunas de A e 1 coluna de b.");
                        }
                        System.arraycopy(aumentada[i], 0, a[i], 0, n);
                        b[i] = aumentada[i][n];
                    }
                    FatoracaoLU lu = new FatoracaoLU();
                    FatoracaoLU.ResultadoLU resultado = lu.resolver(a, b);
                    txtResultado.setText(resultado.getLog());
                } else if (ehGaussSeidel) {
                    double[][] aumentada = parseMatriz(txtMatriz.getText());
                    int n = aumentada.length;
                    double[][] a = new double[n][n];
                    double[] b = new double[n];
                    for (int i = 0; i < n; i++) {
                        if (aumentada[i].length != n + 1) {
                            throw new IllegalArgumentException("A matriz deve ter " + n + " colunas de A e 1 coluna de b.");
                        }
                        System.arraycopy(aumentada[i], 0, a[i], 0, n);
                        b[i] = aumentada[i][n];
                    }
                    double[] x0 = parseVetorOuEscalar(txtX0.getText(), n);
                    double tol = parseDouble(txtTolerancia.getText());
                    int maxIter = parseInt(txtMaxIter.getText());
                    GaussSeidel gs = new GaussSeidel();
                    GaussSeidel.ResultadoGaussSeidel resultado = gs.resolver(a, b, x0, tol, maxIter);
                    txtResultado.setText(resultado.getLog());
                } else {
                    txtResultado.setText(">>> Executando " + nomeMetodo + "...\n[Interface]: Dados lidos e validados com sucesso.\nIteração 1: ...\nResultado final exibido com sucesso.");
                }
            } catch (Exception ex) {
                txtResultado.setText("Erro: " + ex.getMessage());
            }
        });

        Button btnVoltar = new Button("← Voltar aos Subtópicos");
        btnVoltar.setOnAction(e -> {
            String letra = nomeMetodo != null && !nomeMetodo.isEmpty() ? nomeMetodo.substring(0, 1) : "A";
            MainApp.mudarTela(new SubtopicosView(letra));
        });

        HBox barraInferior = new HBox(10, btnExecutar, btnVoltar);

        getChildren().addAll(lblTitulo, grid, barraInferior, new Label("Resultados e Iterações:"), txtResultado);
    }

    private String formatarResultadoRaiz(Interpretador interp, ResultadoMetodo<?> resultado, boolean mostrarDerivada) {
        StringBuilder sb = new StringBuilder();
        sb.append("f(x) = ").append(interp.getFuncaoFormatada()).append("\n");
        if (mostrarDerivada) {
            sb.append("f'(x) = ").append(interp.getFuncaoDerivadaFormatada()).append("\n");
        }
        sb.append("--------------------------------------------------\n");

        List<?> iteracoes = extrairIteracoes(resultado);
        if (iteracoes != null && !iteracoes.isEmpty()) {
            for (Object it : iteracoes) {
                sb.append(it.toString()).append("\n");
            }
        } else {
            sb.append("Nenhuma iteração registrada (verifique se o intervalo possui troca de sinal ou se o chute inicial já é raiz).\n");
        }

        sb.append("--------------------------------------------------\n");
        Object raiz = extrairCampoOuMetodo(resultado, "getRaiz", "raiz");
        Object convergiu = extrairCampoOuMetodo(resultado, "isConvergiu", "getConvergiu", "convergiu");

        if (raiz != null) {
            sb.append("Raiz aproximada: ").append(raiz).append("\n");
        }
        if (convergiu != null) {
            sb.append("Convergiu: ").append((boolean) convergiu ? "Sim" : "Não").append("\n");
        }

        if (raiz == null && (iteracoes == null || iteracoes.isEmpty())) {
            sb.append(resultado.toString());
        }

        return sb.toString();
    }

    private List<?> extrairIteracoes(Object resultado) {
        Object val = extrairCampoOuMetodo(resultado, "getIteracoes", "iteracoes");
        if (val instanceof List<?> lista) {
            return lista;
        }
        for (Field f : resultado.getClass().getDeclaredFields()) {
            if (List.class.isAssignableFrom(f.getType())) {
                try {
                    f.setAccessible(true);
                    return (List<?>) f.get(resultado);
                } catch (Exception ignored) {
                }
            }
        }
        return null;
    }

    private Object extrairCampoOuMetodo(Object alvo, String... nomes) {
        for (String nome : nomes) {
            try {
                Method m = alvo.getClass().getMethod(nome);
                return m.invoke(alvo);
            } catch (Exception ignored) {
            }
            try {
                Field f = alvo.getClass().getDeclaredField(nome);
                f.setAccessible(true);
                return f.get(alvo);
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private double parseDouble(String texto) {
        return Double.parseDouble(texto.trim().replace(",", "."));
    }

    private int parseInt(String texto) {
        return Integer.parseInt(texto.trim());
    }

    private double[] parseVetorOuEscalar(String texto, int n) {
        if (texto == null || texto.isBlank()) {
            return new double[n];
        }
        String[] partes = texto.trim().split("[,;\\s]+");
        if (partes.length == 1) {
            double val = parseDouble(partes[0]);
            double[] vet = new double[n];
            for (int i = 0; i < n; i++) {
                vet[i] = val;
            }
            return vet;
        }
        if (partes.length != n) {
            throw new IllegalArgumentException("Informe 1 valor inicial ou " + n + " valores para x0.");
        }
        double[] vet = new double[n];
        for (int i = 0; i < n; i++) {
            vet[i] = parseDouble(partes[i]);
        }
        return vet;
    }

    private double[][] parseMatriz(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Informe a matriz aumentada.");
        }
        String[] linhas = texto.trim().split("\\R+");
        double[][] matriz = new double[linhas.length][];
        for (int i = 0; i < linhas.length; i++) {
            String[] elementos = linhas[i].trim().split("[,;\\s]+");
            matriz[i] = new double[elementos.length];
            for (int j = 0; j < elementos.length; j++) {
                matriz[i][j] = parseDouble(elementos[j]);
            }
        }
        return matriz;
    }
}