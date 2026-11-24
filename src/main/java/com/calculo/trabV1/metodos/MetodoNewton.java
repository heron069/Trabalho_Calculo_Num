package com.calculo.trabV1.metodos;

import java.util.ArrayList;
import java.util.List;
import com.calculo.trabV1.excecoes.*;
import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.iteracao.NewtonIteracao;
import com.calculo.trabV1.resultados.ResultadoMetodo;

public class MetodoNewton{
    private Interpretador interpretador;

    public MetodoNewton(Interpretador interpretador){
        this.interpretador = interpretador;
    }

    public ResultadoMetodo<NewtonIteracao> calcularRaiz(double x0, double tolerancia, int it){
        List<NewtonIteracao> iteracoes = new ArrayList<>();
        if(tolerancia <= 0 || !Double.isFinite(tolerancia)){
            throw new ArgumentoInvalidoException("A tolerância deve ser positiva e finita.");
        }
        if(it <= 0){
            throw new ArgumentoInvalidoException("O número máximo de iterações deve ser positivo.");
        }
        
        double x = x0;
        double fx = interpretador.calcular(x);
        int num = 0;
        double raiz = x;
        boolean convergiu = Math.abs(fx) <= tolerancia;
        
        while (!convergiu && num < it){

            double flinhax = interpretador.calcularDerivada(x);

            if(!Double.isFinite(flinhax)){
                throw new DominioMatematicoException("A derivada retornou um valor inválido.");
            }

            if(Math.abs(flinhax) < 1e-12){
                throw new DivisaoPorZeroException("A derivada é zero ou muito próxima de zero.");
            }

            double x1 = x - (fx / flinhax);
            double fx1 = interpretador.calcular(x1);
            if(!Double.isFinite(x1) || !Double.isFinite(fx1)){
                throw new DivisaoPorZeroException("O método da newton gerou um valor inválido.");
            }

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
