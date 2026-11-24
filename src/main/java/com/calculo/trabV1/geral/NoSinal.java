package com.calculo.trabV1.geral;
import com.calculo.trabV1.excecoes.*;

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
            throw new ExpressaoInvalidaException("Sinal desconhecido: " + sinal);
        }
    }
    
    @Override 
    public No derivar(){
        return new NoSinal(sinal,argumento.derivar());
    }

    public String getSinal(){
        return sinal;
    }

    public No getArgumento(){
        return argumento;
    }

    @Override
    public String toString(){
        if(sinal == "+"){
            return argumento.toString();
        }
        return "-(" + argumento.toString() + ")";
    }

}