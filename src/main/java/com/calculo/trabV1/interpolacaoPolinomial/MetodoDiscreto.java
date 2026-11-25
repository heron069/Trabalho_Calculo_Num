package com.calculo.trabV1.interpolacaoPolinomial;
import com.calculo.trabV1.excecoes.*;
import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.resultados.ResultadoDiscreto;
import com.calculo.trabV1.sistemasLineares.EliminacaoGauss.ResultadoGauss;
import com.calculo.trabV1.sistemasLineares.EliminacaoGauss;

public class MetodoDiscreto{
    private Interpretador interpretador;
    private EliminacaoGauss gauss = new EliminacaoGauss();
    private double[] x;
    private double[] y;
    private int grau;
    
    public MetodoDiscreto(double[] x, double[] y, int grau){
        if(x == null || y == null){
            throw new ArgumentoInvalidoException("Os vetores x e y não podem ser nulos.");
        }

        if(x.length == 0 || x.length != y.length){
            throw new ArgumentoInvalidoException("Os vetores x e y devem ter o mesmo tamanho e não podem estar vazios.");
        }
        
        this.x = x;
        this.y = y;
        this.grau = grau;
    }

    public ResultadoDiscreto calcularDiscreto(){
        int tam = grau+1;
        int[] g = new int [tam];
        for(int i = 0; i<g.length; i++){
            g[i] = i;
        }
        
        double[][] A = new double[tam][tam];
        for(int i = 0; i < tam; i++){
            for(int j = 0; j < tam; j++){
                double soma = 0.0;
                for(int k = 0; k < x.length; k++){
                    soma += Math.pow(x[k],g[i]) * Math.pow(x[k],g[j]);
                }
                A[i][j] = soma;
                A[j][i] = soma;
            }
        }

        double[] b = new double[tam];
        for (int i = 0; i < tam; i++){
            double soma = 0.0;
            for (int k = 0; k < x.length; k++){
                soma += y[k] * Math.pow(x[k], g[i]);
            }
            b[i] = soma;
        }

        double[][] aumentada = new double[tam][tam+1];
        for(int i = 0; i < tam; i++){
            for(int j = 0; j<tam+1; j++){
                if(j == tam){
                    aumentada[i][j] = b[i];
                }
                else{
                    aumentada[i][j] = A[i][j];
                }
            }
        }

        ResultadoGauss solucao = gauss.eliminacaoDeGauss(aumentada);
        double[] alpha = new double[tam];
        alpha = solucao.getSolucao();


        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < alpha.length; i++){
            double c = alpha[i];
            String valorAbs = java.math.BigDecimal.valueOf(Math.abs(c)).toPlainString();

            if(i > 0){
                sb.append(c >= 0 ? " + " : " - ");
                sb.append(valorAbs).append("*x^").append(i);
            } else{
                if(c < 0) sb.append("-");
                sb.append(valorAbs).append("*x^").append(i);
            }
        }
        
        interpretador = new Interpretador(sb.toString());

        return new ResultadoDiscreto(A, b, alpha, interpretador);
    }
}
