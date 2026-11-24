package com.calculo.trabV1.metodos;
import com.calculo.trabV1.excecoes.*;
import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.resultados.ResultadoInterpolacao;

import java.util.ArrayList;
import java.util.List;

public class InterpolacaoNewton{
    private List<List<Double>> ordens = new ArrayList<>();


    public double montarD(double[] x, double [] y, int k){
        if (k < 0 || k >= x.length) {
            throw new ArgumentoInvalidoException("Ordem de diferença dividida inválida.");
        }
        
        if(ordens.isEmpty()){
            List<Double> ordem0 = new ArrayList<>();
            for(double i: y){
                ordem0.add(i);
            }
            ordens.add(ordem0);
        }
        
        while(ordens.size()<=k){
            int ordemAtual = ordens.size();
            List<Double> ordemAnt = ordens.get(ordemAtual - 1);
            List<Double> novaOrdem = new ArrayList<>();

            for(int i = 0; i < ordemAnt.size()-1; i++){
                double numerador = ordemAnt.get(i+1) - ordemAnt.get(i);
                double denominador = x[i + ordemAtual] - x[i];
                if (denominador == 0.0) {
                    throw new DivisaoPorZeroException("Não é possível calcular com valores de x repetidos.");
                }
                
                novaOrdem.add(numerador/denominador);
            }

            ordens.add(novaOrdem);
        }
    
        return ordens.get(k).get(0);
    }

    public ResultadoInterpolacao montarPolinomio(double[] x, double[] y){
        
        if(x.length == 0 || x.length != y.length){
            throw new ArgumentoInvalidoException("Os vetores x e y devem ter o mesmo tamanho e não podem estar vazios.");
        }
        for(int i = 0; i < x.length; i++){
            for(int j = i + 1; j < x.length; j++){
                if(x[i] == x[j]){
                    throw new DivisaoPorZeroException("Existem valores de x repetidos na interpolação.");
                }
            }
        }
        
        
        ordens.clear();

        No polinomio = null;
        No fatores = null;

        for(int k = 0; k < x.length; k++){
            double dk = montarD(x, y, k);
            No termo;

            if(k == 0){
                termo = new NoNumero(dk);
            }
            else{
                No fator = new NoOperador("-", new NoVariavel(), new NoNumero(x[k - 1]));
                
                if(fatores == null){
                    fatores = fator;
                }
                else{
                   fatores = new NoOperador("*", fatores, fator);
                }

                termo =  new NoOperador("*", new NoNumero(dk), fatores);
            }
            if(polinomio == null){
                polinomio = termo;
            }
            else{
                polinomio = new NoOperador("+", polinomio, termo);
            }
        }
        return new ResultadoInterpolacao(polinomio, ordens);
    }

    public List<List<Double>> getOrdens(){
        return ordens;
    }
}
