package com.calculo.trabV1.metodos;

import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.resultados.ResultadoDiscreto;

public class MetodoDiscreto {
    private Interpretador interpretador;
    private double[] x;
    private double[] y;
    private int grau;
    
    public MetodoDiscreto(double[] x, double[] y, int grau){
        if(x.length != y.length){
            throw new RuntimeException("Valores de x[] e y[] Invalidos");
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
        for (int i = 0; i < tam; i++) {
            double soma = 0.0;
            for (int k = 0; k < x.length; k++) {
                soma += y[k] * Math.pow(x[k], g[i]);
            }
            b[i] = soma;
        }

        double[] alpha = new double[tam];
        // Encontra uma forma de resolver o sistema A * alpha = b 
        // usando a classe de Eliminação de Gauss Da Erika.

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < alpha.length; i++){
            double c = alpha[i];
            if(i > 0){
                sb.append(c >= 0 ? " + " : " - ");
                sb.append(Math.abs(c)).append("*x^").append(i);
            }
            else{
                sb.append(c).append("*x^").append(i);
            }
        }
        
        interpretador = new Interpretador(sb.toString());

        return new ResultadoDiscreto(A, b, alpha, interpretador);
    }
}
