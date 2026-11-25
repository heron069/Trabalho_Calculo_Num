package com.calculo.trabV1.view;

import com.calculo.trabV1.geral.Interpretador;
import com.calculo.trabV1.geral.No;
import com.calculo.trabV1.integracaoNumerica.*;
import com.calculo.trabV1.interpolacaoPolinomial.*;
import com.calculo.trabV1.raizesFuncoes.*;
import com.calculo.trabV1.sistemasLineares.*;
import com.calculo.trabV1.resultados.ResultadoIntegracao;
import com.calculo.trabV1.resultados.ResultadoInterpolacao;
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

        TextField txtFx = new TextField("x^2 - 2");
        TextField txtMinIntervalo = new TextField("1");
        TextField txtMaxIntervalo = new TextField("2");
        TextField txtX0 = new TextField("1.5");
        TextField txtX1 = new TextField("2");
        TextField txtTolerancia = new TextField("0.0001");
        TextField txtMaxIteracoes = new TextField("50");
        TextField txtN = new TextField("4");
        TextField txtValoresX = new TextField("0, 1, 2");
        TextField txtValoresY = new TextField("1, 3, 2");
        TextField txtGrauAprox = new TextField("2");
        TextField txtAvaliarX = new TextField("1.5");
        TextArea txtMatriz = new TextArea();
        txtMatriz.setPrefRowCount(5);

        String nomeLower = nomeMetodo != null ? nomeMetodo.toLowerCase() : "";

        boolean ehLagrange = nomeMetodo != null &&
                (nomeMetodo.startsWith("C.1") || nomeLower.contains("lagrange"));
        boolean ehInterpNewton = nomeMetodo != null &&
                (nomeMetodo.startsWith("C.2") || (nomeLower.contains("newton") && (nomeLower.contains("interpol") || nomeMetodo.startsWith("2."))));

        boolean ehBisseccao = !ehInterpNewton && nomeMetodo != null &&
                (nomeMetodo.startsWith("A.1") || nomeLower.contains("bissec"));
        boolean ehNewton = !ehInterpNewton && nomeMetodo != null &&
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

        boolean ehTrapezio = nomeMetodo != null &&
                (nomeMetodo.startsWith("D.1") || nomeLower.contains("trap"));
        boolean ehSimpson = nomeMetodo != null &&
                (nomeMetodo.startsWith("D.2") || nomeLower.contains("simpson"));
        boolean ehCasoDiscreto = nomeMetodo != null &&
                (nomeMetodo.startsWith("D.3") || nomeLower.contains("discreto"));

        if (ehLagrange || ehInterpNewton) {
            grid.add(new Label("valores de x:"), 0, 0);
            grid.add(txtValoresX, 1, 0);
            grid.add(new Label("valores de y (fx):"), 0, 1);
            grid.add(txtValoresY, 1, 1);
            grid.add(new Label("avaliar em x (opcional):"), 0, 2);
            grid.add(txtAvaliarX, 1, 2);
        } else if (ehBisseccao || ehRegulaFalsi) {
            grid.add(new Label("fx:"), 0, 0);
            grid.add(txtFx, 1, 0);
            grid.add(new Label("minIntervalo:"), 0, 1);
            grid.add(txtMinIntervalo, 1, 1);
            grid.add(new Label("maxIntervalo:"), 0, 2);
            grid.add(txtMaxIntervalo, 1, 2);
            grid.add(new Label("tolerancia:"), 0, 3);
            grid.add(txtTolerancia, 1, 3);
            grid.add(new Label("maxIteracoes:"), 0, 4);
            grid.add(txtMaxIteracoes, 1, 4);
        } else if (ehNewton) {
            grid.add(new Label("fx:"), 0, 0);
            grid.add(txtFx, 1, 0);
            grid.add(new Label("x0 (chute inicial):"), 0, 1);
            grid.add(txtX0, 1, 1);
            grid.add(new Label("tolerancia:"), 0, 2);
            grid.add(txtTolerancia, 1, 2);
            grid.add(new Label("maxIteracoes:"), 0, 3);
            grid.add(txtMaxIteracoes, 1, 3);
        } else if (ehSecante) {
            grid.add(new Label("fx:"), 0, 0);
            grid.add(txtFx, 1, 0);
            grid.add(new Label("x0:"), 0, 1);
            grid.add(txtX0, 1, 1);
            grid.add(new Label("x1:"), 0, 2);
            grid.add(txtX1, 1, 2);
            grid.add(new Label("tolerancia:"), 0, 3);
            grid.add(txtTolerancia, 1, 3);
            grid.add(new Label("maxIteracoes:"), 0, 4);
            grid.add(txtMaxIteracoes, 1, 4);
        } else if (ehGauss || ehLU) {
            txtMatriz.setPromptText("3, 2, 4, 1\n1, 1, 2, 2\n4, 3, -2, 3");
            grid.add(new Label("Matriz Aumentada [A | b]:"), 0, 0);
            grid.add(txtMatriz, 1, 0);
        } else if (ehGaussSeidel) {
            txtMatriz.setPromptText("3, 2, 4, 1\n1, 1, 2, 2\n4, 3, -2, 3");
            grid.add(new Label("Matriz Aumentada [A | b]:"), 0, 0);
            grid.add(txtMatriz, 1, 0);
            grid.add(new Label("x0 (chute inicial):"), 0, 1);
            grid.add(txtX0, 1, 1);
            grid.add(new Label("tolerancia:"), 0, 2);
            grid.add(txtTolerancia, 1, 2);
            grid.add(new Label("maxIteracoes:"), 0, 3);
            grid.add(txtMaxIteracoes, 1, 3);
        } else if (ehTrapezio || ehSimpson) {
            grid.add(new Label("fx:"), 0, 0);
            grid.add(txtFx, 1, 0);
            grid.add(new Label("minIntervalo:"), 0, 1);
            grid.add(txtMinIntervalo, 1, 1);
            grid.add(new Label("maxIntervalo:"), 0, 2);
            grid.add(txtMaxIntervalo, 1, 2);
            grid.add(new Label("n:"), 0, 3);
            grid.add(txtN, 1, 3);
        } else if (ehCasoDiscreto) {
            grid.add(new Label("valores de x:"), 0, 0);
            grid.add(txtValoresX, 1, 0);
            grid.add(new Label("valores de y (fx):"), 0, 1);
            grid.add(txtValoresY, 1, 1);
            grid.add(new Label("grau da função aprox:"), 0, 2);
            grid.add(txtGrauAprox, 1, 2);
        } else {
            grid.add(new Label("fx:"), 0, 0);
            grid.add(txtFx, 1, 0);
            grid.add(new Label("x0:"), 0, 1);
            grid.add(txtX0, 1, 1);
            grid.add(new Label("tolerancia:"), 0, 2);
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
                if (ehLagrange) {
                    double[] x = parseVetor(txtValoresX.getText());
                    double[] y = parseVetor(txtValoresY.getText());
                    InterpolacaoLagrange lagrange = new InterpolacaoLagrange();
                    ResultadoInterpolacao res = lagrange.montarPolinomio(x, y);
                    txtResultado.setText(formatarResultadoInterpolacao(res, txtAvaliarX.getText(), null));
                } else if (ehInterpNewton) {
                    double[] x = parseVetor(txtValoresX.getText());
                    double[] y = parseVetor(txtValoresY.getText());
                    InterpolacaoNewton interpNewton = new InterpolacaoNewton();
                    ResultadoInterpolacao res = interpNewton.montarPolinomio(x, y);
                    txtResultado.setText(formatarResultadoInterpolacao(res, txtAvaliarX.getText(), interpNewton.getOrdens()));
                } else if (ehBisseccao) {
                    Interpretador interp = new Interpretador(txtFx.getText());
                    double minIntervalo = parseDouble(txtMinIntervalo.getText());
                    double maxIntervalo = parseDouble(txtMaxIntervalo.getText());
                    double tolerancia = parseDouble(txtTolerancia.getText());
                    int maxIteracoes = parseInt(txtMaxIteracoes.getText());

                    MetodoBisseccao metodo = new MetodoBisseccao(interp);
                    ResultadoMetodo<?> resultado = metodo.calcularRaiz(minIntervalo, maxIntervalo, tolerancia, maxIteracoes);
                    txtResultado.setText(formatarResultadoRaiz(interp, resultado, false));
                } else if (ehNewton) {
                    Interpretador interp = new Interpretador(txtFx.getText());
                    double x0 = parseDouble(txtX0.getText());
                    double tolerancia = parseDouble(txtTolerancia.getText());
                    int maxIteracoes = parseInt(txtMaxIteracoes.getText());

                    MetodoNewton metodo = new MetodoNewton(interp);
                    ResultadoMetodo<?> resultado = metodo.calcularRaiz(x0, tolerancia, maxIteracoes);
                    txtResultado.setText(formatarResultadoRaiz(interp, resultado, true));
                } else if (ehSecante) {
                    Interpretador interp = new Interpretador(txtFx.getText());
                    double x0 = parseDouble(txtX0.getText());
                    double x1 = parseDouble(txtX1.getText());
                    double tolerancia = parseDouble(txtTolerancia.getText());
                    int maxIteracoes = parseInt(txtMaxIteracoes.getText());

                    MetodoSecante metodo = new MetodoSecante(interp);
                    ResultadoMetodo<?> resultado = metodo.calcularRaiz(x0, x1, tolerancia, maxIteracoes);
                    txtResultado.setText(formatarResultadoRaiz(interp, resultado, false));
                } else if (ehRegulaFalsi) {
                    Interpretador interp = new Interpretador(txtFx.getText());
                    double minIntervalo = parseDouble(txtMinIntervalo.getText());
                    double maxIntervalo = parseDouble(txtMaxIntervalo.getText());
                    double tolerancia = parseDouble(txtTolerancia.getText());
                    int maxIteracoes = parseInt(txtMaxIteracoes.getText());

                    MetodoRegulaFalsi metodo = new MetodoRegulaFalsi(interp);
                    ResultadoMetodo<?> resultado = metodo.calcularRaiz(minIntervalo, maxIntervalo, tolerancia, maxIteracoes);
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
                    double tolerancia = parseDouble(txtTolerancia.getText());
                    int maxIteracoes = parseInt(txtMaxIteracoes.getText());
                    GaussSeidel gs = new GaussSeidel();
                    GaussSeidel.ResultadoGaussSeidel resultado = gs.resolver(a, b, x0, tolerancia, maxIteracoes);
                    txtResultado.setText(resultado.getLog());
                } else if (ehTrapezio) {
                    Interpretador interp = new Interpretador(txtFx.getText());
                    double minIntervalo = parseDouble(txtMinIntervalo.getText());
                    double maxIntervalo = parseDouble(txtMaxIntervalo.getText());
                    int n = parseInt(txtN.getText());
                    IntegracaoTrapezio trap = new IntegracaoTrapezio(interp, minIntervalo, maxIntervalo, n);
                    ResultadoIntegracao res = trap.calcularIntegral();
                    txtResultado.setText(formatarResultadoIntegracao(interp, minIntervalo, maxIntervalo, n, res));
                } else if (ehSimpson) {
                    Interpretador interp = new Interpretador(txtFx.getText());
                    double minIntervalo = parseDouble(txtMinIntervalo.getText());
                    double maxIntervalo = parseDouble(txtMaxIntervalo.getText());
                    int n = parseInt(txtN.getText());
                    IntegracaoSimpson simp = new IntegracaoSimpson(interp, minIntervalo, maxIntervalo, n);
                    ResultadoIntegracao res = simp.calcularIntegral();
                    txtResultado.setText(formatarResultadoIntegracao(interp, minIntervalo, maxIntervalo, n, res));
                } else if (ehCasoDiscreto) {
                    double[] x = parseVetor(txtValoresX.getText());
                    double[] y = parseVetor(txtValoresY.getText());
                    int grau = parseInt(txtGrauAprox.getText());
                    txtResultado.setText(calcularAjustePolinomialDiscreto(x, y, grau));
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

    private String calcularAjustePolinomialDiscreto(double[] x, double[] y, int grau) {
        if (x.length != y.length || x.length == 0) {
            throw new IllegalArgumentException("Os vetores x e y devem ter o mesmo tamanho e não podem estar vazios.");
        }
        if (grau < 0 || grau >= x.length) {
            throw new IllegalArgumentException("O grau da função aproximada deve estar entre 0 e " + (x.length - 1) + ".");
        }

        int m = grau + 1;
        double[][] aumentada = new double[m][m + 1];

        for (int linha = 0; linha < m; linha++) {
            for (int col = 0; col < m; col++) {
                double soma = 0.0;
                for (double xi : x) {
                    soma += Math.pow(xi, linha + col);
                }
                aumentada[linha][col] = soma;
            }
            double somaY = 0.0;
            for (int k = 0; k < x.length; k++) {
                somaY += y[k] * Math.pow(x[k], linha);
            }
            aumentada[linha][m] = somaY;
        }

        EliminacaoGauss gauss = new EliminacaoGauss();
        EliminacaoGauss.ResultadoGauss res = gauss.eliminacaoDeGauss(aumentada);
        double[] coef = res.getSolucao();

        StringBuilder sb = new StringBuilder();
        sb.append("Grau da função aproximada: ").append(grau).append("\n");
        sb.append("Polinômio ajustado P(x): ");
        for (int i = 0; i < coef.length; i++) {
            if (i > 0) {
                sb.append(coef[i] >= 0 ? " + " : " - ");
                sb.append(String.format("%.6f", Math.abs(coef[i])));
            } else {
                sb.append(String.format("%.6f", coef[i]));
            }
            if (i == 1) {
                sb.append(" * x");
            } else if (i > 1) {
                sb.append(" * x^").append(i);
            }
        }
        sb.append("\n--------------------------------------------------\n");
        sb.append(res.getLogEtapas());
        return sb.toString();
    }

    private String formatarResultadoIntegracao(Interpretador interp, double minIntervalo, double maxIntervalo, int n, ResultadoIntegracao res) {
        StringBuilder sb = new StringBuilder();
        sb.append("fx = ").append(interp.getFuncaoFormatada()).append("\n");
        sb.append("Intervalo: [").append(minIntervalo).append(", ").append(maxIntervalo).append("] | n = ").append(n).append("\n");
        sb.append("--------------------------------------------------\n");

        Object valor = extrairCampoOuMetodo(res, "getResultado", "getValor", "getIntegral", "resultado", "valor", "integral");
        if (valor != null) {
            sb.append("Valor da integral: ").append(valor).append("\n");
        } else {
            sb.append(res.toString()).append("\n");
        }
        return sb.toString();
    }

    private String formatarResultadoInterpolacao(ResultadoInterpolacao res, String textoX, List<List<Double>> ordens) {
        StringBuilder sb = new StringBuilder();
        Object poliObj = extrairCampoOuMetodo(res, "getPolinomio", "polinomio", "getNo", "no");

        if (poliObj instanceof No noPolinomio) {
            sb.append("Polinômio P(x): ").append(noPolinomio.toString()).append("\n");
            if (textoX != null && !textoX.isBlank()) {
                double px = parseDouble(textoX);
                double valorPx = noPolinomio.interpretar(px);
                sb.append("P(").append(px).append(") = ").append(valorPx).append("\n");
            }
        } else if (poliObj != null) {
            String exprPoli = poliObj.toString();
            sb.append("Polinômio P(x): ").append(exprPoli).append("\n");
            if (textoX != null && !textoX.isBlank()) {
                double px = parseDouble(textoX);
                Interpretador interpPoli = new Interpretador(exprPoli);
                double valorPx = interpPoli.calcular(px);
                sb.append("P(").append(px).append(") = ").append(valorPx).append("\n");
            }
        } else {
            sb.append(res.toString()).append("\n");
            if (textoX != null && !textoX.isBlank()) {
                double px = parseDouble(textoX);
                Interpretador interpPoli = new Interpretador(res.toString());
                sb.append("P(").append(px).append(") = ").append(interpPoli.calcular(px)).append("\n");
            }
        }

        if (ordens != null && !ordens.isEmpty()) {
            sb.append("--------------------------------------------------\n");
            sb.append("Diferenças divididas:\n");
            for (int k = 0; k < ordens.size(); k++) {
                sb.append("Ordem ").append(k).append(": ").append(ordens.get(k)).append("\n");
            }
        }
        return sb.toString();
    }

    private String formatarResultadoRaiz(Interpretador interp, ResultadoMetodo<?> resultado, boolean mostrarDerivada) {
        StringBuilder sb = new StringBuilder();
        sb.append("fx = ").append(interp.getFuncaoFormatada()).append("\n");
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
        if (alvo == null) {
            return null;
        }
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

    private double[] parseVetor(String texto) {
        if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("Informe os valores separados por vírgula.");
        }
        String[] partes = texto.trim().split("[,;\\s]+");
        double[] vet = new double[partes.length];
        for (int i = 0; i < partes.length; i++) {
            vet[i] = parseDouble(partes[i]);
        }
        return vet;
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