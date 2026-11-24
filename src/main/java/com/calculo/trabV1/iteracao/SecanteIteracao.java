package com.calculo.trabV1.iteracao;

public class SecanteIteracao{
    private int num;
    private double x;
    private double x1;
    private double x2;
    private double fx;
    private double fx1;
    private double erro;

    public SecanteIteracao(int num, double x, double fx, double x1, double fx1,double x2, double erro){
        this.num = num;
        this.x = x;
        this.x1 = x1;
        this.x2 = x2;
        this.fx = fx;
        this.fx1 = fx1;
        this.erro = erro;
    }

    @Override 
    public String toString(){
        return "it: " + num + " x: " + x + " x1: " + x1 +
               " fx: " + fx + " fx1: " + fx1 + " x2: " + x2 +" erro: " + erro;
    }

}
