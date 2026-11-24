package com.calculo.trabV1.geral;


public class NoNumero implements No{
    private double valor;

    public NoNumero(double valor){
        this.valor = valor;
    }

    @Override 
    public double interpretar(double x){
        return this.valor;
    }

    @Override 
    public No derivar(){
        return new NoNumero(0);
    }

    public double getValor(){
        return valor;
    }

    @Override 
    public String toString(){
        if(valor == (int) valor){
            return String.valueOf((int) valor);
        }
        return String.valueOf(valor);
    }
}
