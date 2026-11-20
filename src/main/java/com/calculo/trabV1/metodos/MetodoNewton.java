package com.calculo.trabV1.metodos;

import java.util.ArrayList;
import java.util.List;

import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.iteracao.NewtonIteracao;
import com.calculo.trabV1.resultados.ResultadoMetodo;

public class MetodoNewton {
    private Interpretador interpretador;

    public MetodoNewton(Interpretador interpretador){
        this.interpretador = interpretador;
    }

    public ResultadoMetodo<NewtonIteracao> calcularRaiz(double x0, double tolerancia, int it){
        List<NewtonIteracao> iteracoes = new ArrayList<>();
        double x = x0;
        double fx = interpretador.calcular(x);
        int num = 0;
        double raiz = x;
        boolean convergiu = Math.abs(fx) <= tolerancia;
        
        while (!convergiu && num < it) {

            double flinhax = interpretador.calcularDerivada(x);

            if (Math.abs(flinhax) < 1e-12) {
                System.out.println("Divisão por zero Encontrada!!! Verifique Valores");
                break;
            }

            double x1 = x - (fx / flinhax);
            double fx1 = interpretador.calcular(x1);
            double erro = Math.abs(x1 - x);

            num++;

            iteracoes.add(new NewtonIteracao(num, x, x1, fx1, flinhax, erro));

            x = x1;
            fx = fx1;
            raiz = x;

            convergiu = Math.abs(fx) <= tolerancia && erro <= tolerancia;
        }

        return new ResultadoMetodo<>(raiz, convergiu, iteracoes);
    }
}
