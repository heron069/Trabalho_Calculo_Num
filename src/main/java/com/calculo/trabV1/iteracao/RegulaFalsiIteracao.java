package com.calculo.trabV1.iteracao;

public class RegulaFalsiIteracao {
    int num;
    double x;
    double fx;
    double erro;

    public RegulaFalsiIteracao(int num, double x, double fx, double erro){
        this.num = num;
        this.x = x;
        this.fx = fx;
        this.erro = erro;
    }

    @Override 
    public String toString(){
        return "it: " + num + " x: " + x + " fx: " + fx + " |b-a|: " + erro;
    }
}
