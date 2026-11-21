package com.calculo.trabV1.iteracao;

public class BisseccaoIteracao {
    private int num;
    private double x;
    private double fx;
    private double maxIntervalo;
    private double minIntervalo;
    private double erro;

    public BisseccaoIteracao(int num, double x, double fx, double minIntervalo, double maxIntervalo, double erro){
        this.num = num;
        this.x = x;
        this.fx = fx;
        this.maxIntervalo = maxIntervalo;
        this.minIntervalo = minIntervalo;
        this.erro = erro;
    }

    public int getNum(){return num;}

    public double getX(){return x;}
    
    public double getFx(){return fx;}
    
    public double getMaxIntervalo(){return maxIntervalo;}
    
    public double getMinIntervalo(){return minIntervalo;}
    
    public double getErro(){return erro;}

    @Override 
    public String toString(){
        return "it: " + num + " minIntervalo: " + minIntervalo + " maxIntervalo: " + maxIntervalo +
               " x: " + x + " fx: " + fx + " erro: " + erro;
    }
}
