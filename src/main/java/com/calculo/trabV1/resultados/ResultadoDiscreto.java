package com.calculo.trabV1.resultados;

import com.calculo.trabV1.geral.*;

public class ResultadoDiscreto {
    private double[][] A;
    private double[] alpha;
    private double[] b;
    private No arvore;
    private String polinomio;

    public ResultadoDiscreto(double[][] A, double[] b, double[] alpha, Interpretador interpretador){
        this.A = A;
        this.b = b;
        this.alpha = alpha;
        this.arvore = interpretador.getArvore();
        this.polinomio = interpretador.getFuncaoFormatada();
    }

    public double[][] getA(){
        return A;
    }
    public double[] getB(){
        return b;
    }
    public double[] getAlpha(){
        return alpha;
    }
    public String getPolinomio(){
        return polinomio;
    }
}
