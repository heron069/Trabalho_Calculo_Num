package com.calculo.trabV1.metodos;

import java.util.ArrayList;
import java.util.List;
import com.calculo.trabV1.excecoes.*;
import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.iteracao.BisseccaoIteracao;
import com.calculo.trabV1.resultados.ResultadoMetodo;


public class MetodoBisseccao{
    private Interpretador interpretador;

    public MetodoBisseccao(Interpretador interpretador){
        this.interpretador = interpretador;
    }

    public ResultadoMetodo<BisseccaoIteracao> calcularRaiz(double minIntervalo, double maxIntervalo, double tolerancia, int maxIteracao){
        List<BisseccaoIteracao> iteracoes = new ArrayList<>();
        if(!Double.isFinite(minIntervalo) || !Double.isFinite(maxIntervalo)){
            throw new ArgumentoInvalidoException("Os limites do intervalo devem ser finitos.");
        }
        if(minIntervalo >= maxIntervalo){
            throw new ArgumentoInvalidoException("O limite mínimo deve ser menor que o máximo.");
        }
        if(tolerancia <= 0 || !Double.isFinite(tolerancia)){
            throw new ArgumentoInvalidoException("A tolerância deve ser positiva e finita.");
        }
        if(maxIteracao <= 0){
            throw new ArgumentoInvalidoException("O número máximo de iterações deve ser positivo.");
        }
        
        double fa = interpretador.calcular(minIntervalo);
        double fb = interpretador.calcular(maxIntervalo);
        
        if(fa == 0){
            return new ResultadoMetodo<>(minIntervalo, true, iteracoes);
        }
        if(fb == 0){
            return new ResultadoMetodo<>(maxIntervalo, true, iteracoes);
        }
        if(fa*fb>0){
            throw new ArgumentoInvalidoException("O intervalo não possui mudança de sinal.");
        }

        double x = 0;
        double fx = 0;
        double erro = Double.POSITIVE_INFINITY;

        int i = 0;

        while(erro > tolerancia && i < maxIteracao){
            x =(minIntervalo + maxIntervalo)/2.0;
            fx = interpretador.calcular(x);
            if (!Double.isFinite(fx)) {
                throw new DominioMatematicoException("A função retornou um valor inválido durante a bissecção.");
            }
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
