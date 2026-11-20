package com.calculo.trabV1.geral;


public class NoSinal implements No{
    private String sinal;
    private No argumento;

    public NoSinal(String sinal, No argumento){
        this.sinal = sinal;
        this.argumento = argumento;
    }

    @Override
    public double interpretar(double x){
        double valor = argumento.interpretar(x);
        
        if(sinal == "-"){
            return -valor;
        }
        else if(sinal == "+"){
            return valor;
        }
        else{
            throw new RuntimeException("Sinal desconhecida: " + sinal);
        }
    }
    
    @Override 
    public No derivar(){
        return new NoSinal(sinal,argumento.derivar());
    }

    @Override
    public String toString(){
        if(sinal == "+"){
            return argumento.toString();
        }
        return "-(" + argumento.toString() + ")";
    }

}