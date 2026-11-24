package com.calculo.trabV1.sistemasLineares;
import java.util.Arrays;

public class GaussSeidel{

    public static class ResultadoGaussSeidel{
        private final double[] solucao;
        private final int iteracoes;
        private final String log;

        public ResultadoGaussSeidel(double[] solucao, int iteracoes, String log){
            this.solucao = solucao;
            this.iteracoes = iteracoes;
            this.log = log;
        }

        public double[] getSolucao(){
            return solucao;
        }

        public int getIteracoes(){
            return iteracoes;
        }

        public String getLog(){
            return log;
        }
    }

    public ResultadoGaussSeidel resolver(double[][] a, double[] b, double[] x0, double tol, int maxIter){
        int n = a.length;
        double[] x = Arrays.copyOf(x0, n);
        StringBuilder sb = new StringBuilder();
        int iter = 0;

        for (int k = 1; k <= maxIter; k++){
            iter = k;
            double[] xAnt = Arrays.copyOf(x, n);
            double maiorErro = 0.0;

            for (int i = 0; i < n; i++){
                if(a[i][i] == 0.0){
                    throw new ArithmeticException("Elemento diagonal nulo na linha " + (i + 1));
                }
                double soma = b[i];
                for (int j = 0; j < n; j++){
                    if(j != i){
                        soma -= a[i][j] * x[j];
                    }
                }
                x[i] = soma / a[i][i];
                double erro = Math.abs(x[i] - xAnt[i]);
                if(erro > maiorErro){
                    maiorErro = erro;
                }
            }

            sb.append(String.format("k=%d | erro=%.6f | x=%s%n", k, maiorErro, Arrays.toString(x)));
            if(maiorErro <= tol){
                break;
            }
        }

        return new ResultadoGaussSeidel(x, iter, sb.toString());
    }
}