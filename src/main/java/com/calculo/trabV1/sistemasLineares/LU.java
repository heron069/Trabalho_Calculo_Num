package com.calculo.trabV1.sistemasLineares;

public class LU {

    public static class ResultadoLU {
        private final double[][] l;
        private final double[][] u;
        private final double[] y;
        private final double[] solucao;
        private final String log;

        public ResultadoLU(double[][] l, double[][] u, double[] y, double[] solucao, String log) {
            this.l = l;
            this.u = u;
            this.y = y;
            this.solucao = solucao;
            this.log = log;
        }

        public double[][] getL() {
            return l;
        }

        public double[][] getU() {
            return u;
        }

        public double[] getY() {
            return y;
        }

        public double[] getSolucao() {
            return solucao;
        }

        public String getLog() {
            return log;
        }
    }

    public ResultadoLU resolver(double[][] a, double[] b) {
        int n = a.length;
        double[][] l = new double[n][n];
        double[][] u = new double[n][n];
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < n; i++) {
            l[i][i] = 1.0;
            for (int k = 0; k < i; k++) {
                double soma = 0.0;
                for (int j = 0; j < k; j++) {
                    soma += l[i][j] * u[j][k];
                }
                if (u[k][k] == 0.0) {
                    throw new ArithmeticException("Pivo nulo na fatoracao LU.");
                }
                l[i][k] = (a[i][k] - soma) / u[k][k];
            }
            for (int k = i; k < n; k++) {
                double soma = 0.0;
                for (int j = 0; j < i; j++) {
                    soma += l[i][j] * u[j][k];
                }
                u[i][k] = a[i][k] - soma;
            }
            if (u[i][i] == 0.0) {
                throw new ArithmeticException("Pivo nulo em U na linha " + (i + 1));
            }
        }

        double[] y = new double[n];
        for (int i = 0; i < n; i++) {
            double soma = b[i];
            for (int j = 0; j < i; j++) {
                soma -= l[i][j] * y[j];
            }
            y[i] = soma / l[i][i];
        }

        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            double soma = y[i];
            for (int j = i + 1; j < n; j++) {
                soma -= u[i][j] * x[j];
            }
            x[i] = soma / u[i][i];
        }

        sb.append("Matriz L:\n").append(formatarQuadrada(l)).append("\n");
        sb.append("Matriz U:\n").append(formatarQuadrada(u)).append("\n");
        sb.append("Solucao x:\n");
        for (int i = 0; i < n; i++) {
            sb.append(String.format("x_%d = %.6f%n", i + 1, x[i]));
        }

        return new ResultadoLU(l, u, y, x, sb.toString());
    }

    private String formatarQuadrada(double[][] m) {
        StringBuilder sb = new StringBuilder();
        for (double[] linha : m) {
            sb.append("[ ");
            for (double v : linha) {
                sb.append(String.format("%10.4f ", v));
            }
            sb.append("]\n");
        }
        return sb.toString();
    }
}