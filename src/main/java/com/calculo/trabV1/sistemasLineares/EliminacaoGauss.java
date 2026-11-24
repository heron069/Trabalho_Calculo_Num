package com.calculo.trabV1.sistemasLineares;
import com.calculo.trabV1.excecoes.*;

public class EliminacaoGauss{

    public static class ResultadoGauss{
        private final double[] solucao;
        private final double[][] matrizTriangular;
        private final String logEtapas;

        public ResultadoGauss(double[] solucao, double[][] matrizTriangular, String logEtapas){
            this.solucao = solucao;
            this.matrizTriangular = matrizTriangular;
            this.logEtapas = logEtapas;
        }

        public double[] getSolucao(){
            return solucao;
        }

        public double[][] getMatrizTriangular(){
            return matrizTriangular;
        }

        public String getLogEtapas(){
            return logEtapas;
        }
    }

    public ResultadoGauss eliminacaoDeGauss(double[][] aumentada){
        if(aumentada == null || aumentada.length == 0){
            throw new ArgumentoInvalidoException("A matriz aumentada não pode ser nula ou vazia.");
        }

        int n = aumentada.length;
        double[][] m = copiarMatriz(aumentada, n, n + 1);
        StringBuilder log = new StringBuilder();

        log.append("Matriz aumentada inicial [A^(0) | b^(0)]:\n");
        log.append(formatarMatriz(m, n)).append("\n");

        for (int k = 0; k < n - 1; k++){
            int estagio = k + 1;
            log.append("Estagio ").append(estagio).append(":\n");

            if(m[k][k] == 0.0){
                int linhaTroca = -1;
                for (int i = k + 1; i < n; i++){
                    if(m[i][k] != 0.0){
                        linhaTroca = i;
                        break;
                    }
                }

                if (linhaTroca == -1) {
                    throw new DivisaoPorZeroException("Pivô nulo na coluna " + (k + 1) + ". Não foi encontrado elemento não nulo abaixo.");
                }

                double[] temp = m[k];
                m[k] = m[linhaTroca];
                m[linhaTroca] = temp;
                log.append("Troca da linha L").append(k + 1)
                        .append(" com a linha L").append(linhaTroca + 1).append(":\n");
                log.append(formatarMatriz(m, n)).append("\n");
            }

            double pivo = m[k][k];
            log.append("Pivo = ").append(pivo).append("\n");

            for (int i = k + 1; i < n; i++){
                double multiplicador = m[i][k] / pivo;
                log.append(String.format("m_%d%d = %.6f   |   L%d <- L%d - m_%d%d * L%d%n",
                        i + 1, k + 1, multiplicador, i + 1, i + 1, i + 1, k + 1, k + 1));

                m[i][k] = 0.0;
                for (int j = k + 1; j <= n; j++){
                    m[i][j] = m[i][j] - multiplicador * m[k][j];
                }
            }

            log.append("Matriz apos o Estagio ").append(estagio).append(":\n");
            log.append(formatarMatriz(m, n)).append("\n");
        }

        for (int i = 0; i < n; i++){
            if(m[i][i] == 0.0){
                throw new DivisaoPorZeroException("Elemento da diagonal nulo na linha " + (i + 1) + ".");
            }
        }

        double[] solucao = resolverSistemaTriangularSuperior(m, n, log);
        return new ResultadoGauss(solucao, m, log.toString());
    }

    public double[] resolverSistemaTriangularSuperior(double[][] triangularAumentada, int n, StringBuilder log){
        double[] x = new double[n];
        log.append("Resolucao do sistema triangular superior:\n");

        for (int i = n - 1; i >= 0; i--){
            if(triangularAumentada[i][i] == 0.0){
                throw new DivisaoPorZeroException("Divisão por zero na substituição retroativa.");
            }

            double soma = triangularAumentada[i][n];
            for (int j = i + 1; j < n; j++){
                soma -= triangularAumentada[i][j] * x[j];
            }
            x[i] = soma / triangularAumentada[i][i];
            log.append(String.format("x_%d = %.6f%n", i + 1, x[i]));
        }

        return x;
    }

    private double[][] copiarMatriz(double[][] origem, int linhas, int colunas){
        double[][] copia = new double[linhas][colunas];
        for (int i = 0; i < linhas; i++){
            System.arraycopy(origem[i], 0, copia[i], 0, colunas);
        }
        return copia;
    }

    private String formatarMatriz(double[][] m, int n){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++){
            sb.append("[ ");
            for (int j = 0; j < n; j++){
                sb.append(String.format("%10.4f ", m[i][j]));
            }
            sb.append(String.format("| %10.4f ]%n", m[i][n]));
        }
        return sb.toString();
    }
}