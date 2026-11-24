package com.calculo.trabV1.metodos;

import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.resultados.ResultadoInterpolacao;

public class InterpolacaoLagrange {
    
    public No montarLk(double[] x, int k){
        No numerador = null;
        No denominador = null;
        for(int i = 0; i < x.length; i++){
            if(i == k){
                continue;
            }
            No fatNum = new NoOperador("-",new NoVariavel(),new NoNumero(x[i]));
            No fatDen = new NoOperador("-",new NoNumero(x[k]),new NoNumero(x[i]));

            if(numerador == null){
                numerador = fatNum;
                denominador = fatDen;
            }
            else{
                numerador = new NoOperador("*", numerador, fatNum);
                denominador = new NoOperador("*", denominador, fatDen);
            }
        }
        if(numerador == null){
            return new NoNumero(1);
        }
        return new NoOperador("/",numerador,denominador);
    }

    public ResultadoInterpolacao montarPolinomio(double[] x, double[] y){
        No polinomio = null;

        for(int k = 0; k < x.length; k++){
            No lk = montarLk(x,k);
            No termo = new NoOperador("*",lk,new NoNumero(y[k]));

            if(polinomio == null){
                polinomio = termo;
            }
            else{
                polinomio = new NoOperador("+", polinomio, termo);
            }
        }
        
        return new ResultadoInterpolacao(polinomio);
    }
}
