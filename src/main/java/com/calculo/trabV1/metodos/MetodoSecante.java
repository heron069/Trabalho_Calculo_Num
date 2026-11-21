package com.calculo.trabV1.metodos;

import java.util.ArrayList;
import java.util.List;

import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.iteracao.SecanteIteracao;
import com.calculo.trabV1.resultados.ResultadoMetodo;

public class MetodoSecante {
    private Interpretador interpretador;

    public MetodoSecante(Interpretador interpretador){
        this.interpretador = interpretador;
    }

    public ResultadoMetodo<SecanteIteracao> calcularRaiz(double x0, double x1, double tolerancia, int it){
        List<SecanteIteracao> iteracoes = new ArrayList<>();
        double x = x0;
        double fx = interpretador.calcular(x);
        double fx1 = interpretador.calcular(x1);
        
        int num = 0;
        boolean convergiu = false;
        double raiz = 0;
        
        if(Math.abs(fx) <= tolerancia){
            raiz = x;
            convergiu = true;
        }
        if(Math.abs(fx1) <= tolerancia && !convergiu){
            raiz = x1;
            convergiu = true;
        }

        while(!convergiu && num < it){
            num++;
            
            if (Math.abs(fx1 - fx) < 1e-12) {
                System.out.println("Divisão por zero Encontrada!!! Verifique Valores");
                break;
            }

            double x2 = x1 - (fx1*(x1-x))/(fx1 - fx);
            double fx2 = interpretador.calcular(x2);
            
            raiz = x2;
            
            iteracoes.add(new SecanteIteracao(num, x, fx, x1, fx1, x2, fx2));

            convergiu = Math.abs(fx2) <= tolerancia || Math.abs(x2 - x1) <= tolerancia;
            
            x = x1;
            fx = fx1;
            x1 = x2;
            fx1 = fx2;

        }

        return new ResultadoMetodo<>(raiz, convergiu, iteracoes);
    }
}
