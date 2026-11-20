package com.calculo.trabV1.metodos;

import java.util.ArrayList;
import java.util.List;

import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.iteracao.RegulaFalsiIteracao;
import com.calculo.trabV1.resultados.ResultadoMetodo;

public class MetodoRegulaFalsi {
    private Interpretador interpretador;

    public MetodoRegulaFalsi(Interpretador interpretador){
        this.interpretador = interpretador;
    }

    public ResultadoMetodo<RegulaFalsiIteracao> calcularRaiz(double minIntervalo, double maxIntervalo, double tolerancia, int maxIteracao){
        List<RegulaFalsiIteracao> iteracoes = new ArrayList<>();
        int num = 0;
        double a = minIntervalo;
        double b = maxIntervalo;
        
        double raiz = a;
        boolean convergiu = false;
        
        
        double fa = interpretador.calcular(a);
        double fb = interpretador.calcular(b);
        
        if(Math.abs(fa) <= tolerancia){
            raiz = a;
            convergiu = true;
        }
        if(Math.abs(fb) <= tolerancia && !convergiu){
            raiz = b;
            convergiu = true;
        }
        if (fa * fb > 0) {
            return new ResultadoMetodo<>(0, false, iteracoes);
        }

        while(!convergiu && num < maxIteracao){
            
            if (Math.abs(fb - fa) < 1e-12) {
                System.out.println("Divisão por zero Encontrada!!! Verifique Valores");
                break;
            }
            
            num++;
            double x = (a*fb - b*fa)/(fb - fa);
            double fx = interpretador.calcular(x);
            double erro = Math.abs(b-a);
            
            iteracoes.add(new RegulaFalsiIteracao(num, x, fx, erro));
            
            raiz = x;
            
            if(fa*fx > 0){
                a = x;
                fa = fx;
            }
            else{
                b = x;
                fb = fx;
            }

            convergiu = Math.abs(fx) <= tolerancia || Math.abs(b-a) <= tolerancia;
        }

        return new ResultadoMetodo<>(raiz, convergiu, iteracoes);
    }
}
