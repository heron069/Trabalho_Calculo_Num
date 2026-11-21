package com.calculo.trabV1.metodos;

import java.util.ArrayList;
import java.util.List;

import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.iteracao.BisseccaoIteracao;
import com.calculo.trabV1.resultados.ResultadoMetodo;


public class MetodoBisseccao {
    private Interpretador interpretador;

    public MetodoBisseccao(Interpretador interpretador){
        this.interpretador = interpretador;
    }

    public ResultadoMetodo<BisseccaoIteracao> calcularRaiz(double minIntervalo, double maxIntervalo, double tolerancia, int maxIteracao){
        List<BisseccaoIteracao> iteracoes = new ArrayList<>();
        
        double fa = interpretador.calcular(minIntervalo);
        double fb = interpretador.calcular(maxIntervalo);
        
        if(fa*fb >= 0){
            throw new IllegalArgumentException("Intervalo Invalido");
        }

        double x = 0;
        double fx = 0;
        double erro = Double.POSITIVE_INFINITY;

        int i = 0;

        while(erro > tolerancia && i < maxIteracao){
            x = (minIntervalo + maxIntervalo)/2.0;
            fx = interpretador.calcular(x);
            erro = Math.abs(maxIntervalo - minIntervalo)/2.0;
            i++;

            iteracoes.add(new BisseccaoIteracao(i, x, fx, minIntervalo, maxIntervalo, erro));
        
            if(fx == 0){
                erro = 0;
                break;
            }
            
            if(fa*fx < 0){
                maxIntervalo = x;
                fb = fx;
            }
            else{
                minIntervalo = x;
                fa = fx;
            }
        }
        boolean convergiu = erro <= tolerancia || fx == 0;

        return new ResultadoMetodo<>(x,convergiu,iteracoes);
    }
}
