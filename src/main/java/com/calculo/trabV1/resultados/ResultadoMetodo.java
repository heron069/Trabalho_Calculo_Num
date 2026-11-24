package com.calculo.trabV1.resultados;

import java.util.List;

public class ResultadoMetodo<T>{
    private double raiz;
    private boolean convergiu;
    private int numIteracoes;
    private List<T> iteracoes;

    public ResultadoMetodo(
        double raiz,
        boolean convergiu,
        List<T> iteracoes
    ){
        this.raiz = raiz;
        this.convergiu = convergiu;
        this.numIteracoes = iteracoes.size();
        this.iteracoes = iteracoes;
    }

    public double getRaiz(){
        return raiz;
    }

    public boolean isConvergiu(){
        return convergiu;
    }

    public int getTotalIteracoes(){
        return numIteracoes;
    }

    public List<T> getIteracoes(){
        return iteracoes;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();

        for(T it : iteracoes){
        sb.append(it).append("\n");
        }

        sb.append("Raiz: ").append(raiz).append('\n');

        return sb.toString();
    }
}
