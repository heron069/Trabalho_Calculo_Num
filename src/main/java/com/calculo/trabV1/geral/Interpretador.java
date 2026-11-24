package com.calculo.trabV1.geral;

import com.calculo.trabV1.excecoes.*;

public class Interpretador{
    private No arvore;
    private No arvoreDerivada;

    public Interpretador(String funcao){
        Montador montador = new Montador(funcao);
        this.arvore = montador.montar();
        this.arvoreDerivada = arvore.derivar();
    }

    public double calcular(double x){
        if(!Double.isFinite(x)){
            throw new ArgumentoInvalidoException("O valor de x deve ser finito.");
        }
        return arvore.interpretar(x);
    }
    public double calcularDerivada(double x){
        if(!Double.isFinite(x)){
            throw new ArgumentoInvalidoException("O valor de x deve ser finito.");
        }
        return arvoreDerivada.interpretar(x);
    }

    public String getFuncaoFormatada(){
        return arvore.toString();
    }
    public String getFuncaoDerivadaFormatada(){
        return arvoreDerivada.toString();
    }
    public No getArvore(){
        return arvore;
    }
}