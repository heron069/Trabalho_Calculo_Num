package com.calculo.trabV1.iteracao;

public class NewtonIteracao {
    private int num;
    private double x0;
    private double x1;
    private double fx1;
    private double flinhax1;
    private double erro;
     
    public NewtonIteracao(int num, double x0, double x1, double fx1, double flinhax1, double erro){
        this.num = num;
        this.x0 = x0;
        this.x1 = x1;
        this.fx1 = fx1;
        this.flinhax1 = flinhax1;
        this.erro = erro;
    }

    public int getNum(){return num;}

    public double getX0(){return x0;}
    
    public double getX1(){return x1;}
    
    public double getFx1(){return fx1;}
    
    public double getFlinhax1(){return flinhax1;}
    
    public double getErro(){return erro;}

    @Override 
    public String toString(){
        return "it: " + num + " x0: " + x0 + " x1: " + x1 +
               " fx1: " + fx1 + " flinhax1: " + flinhax1 + " erro: " + erro;
    }
}
