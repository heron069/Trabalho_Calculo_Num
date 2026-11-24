package com.calculo.trabV1.resultados;

import com.calculo.trabV1.geral.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ResultadoInterpolacao {
    private No arvore;
    private String polinomio;
    private List<List<Double>> ordens;

    public ResultadoInterpolacao(No arvore){
        this.arvore = arvore;
        this.polinomio = simplificar(arvore);
        this.ordens = Collections.emptyList();
    }

    public ResultadoInterpolacao(No arvore, List<List<Double>> ordens){
        this.arvore = arvore;
        this.polinomio = simplificar(arvore);
        
        if(ordens != null){
        this.ordens = new ArrayList<>(ordens);
        }
        else{
            this.ordens = Collections.emptyList();
        }
    }

    public No getArvore(){
        return arvore;
    }
    public String getPolinomio(){
        return polinomio;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        if(ordens != null){
            sb.append("Tabela: ");
            for(int i = 0; i < ordens.size(); i++){
                sb.append("Ordem[").append(i).append("]: ").append(ordens.get(i)).append("\n");
            }
            sb.append("\n");
        }
        sb.append("P(x) = ").append(polinomio);
        return sb.toString();
    }


    private int grau(No no){
        if(no instanceof NoNumero || no instanceof NoFuncao){
            return 0;
        }
        if(no instanceof NoVariavel){
            return 1; 
        }
        if(no instanceof NoSinal){
            return grau(((NoSinal) no).getArgumento());
        }

        NoOperador op = (NoOperador) no;
        int a = grau(op.getEsquerda()); 
        int b = grau(op.getDireita());

        switch (op.getOperador()) {
            case "+": 
                return Math.max(a, b);
            case "-": 
                return Math.max(a, b);
            case "*":
                 return a + b;
            case "/":
                if(b == 0){
                    return a;
                }
                throw new RuntimeException("Divisão por polinômio não suportada");
            case "^":
                if(op.getDireita() instanceof NoNumero)
                    return a * (int) ((NoNumero) op.getDireita()).getValor();
        }
        throw new RuntimeException("Expressão não suportada");
    }

    private String simplificar(No arvore) {
        int n = grau(arvore) + 1;
        double[] pontosFuncao = new double[n];
        double[] diff = new double[n];
        double[] c = new double[n];
        double[] base = {1};

        for(int k = 0; k < n; k++) {
            pontosFuncao[k] = Math.cos((2 * k + 1) * Math.PI / (2 * n));
            diff[k] = arvore.interpretar(pontosFuncao[k]);
        }

        for(int k = 0; k < n; k++) {
            for(int i = 0; i < base.length; i++){
                c[i] += diff[0] * base[i];
            }
            
            double[] p = new double[base.length + 1];
            for(int i = 0; i < base.length; i++){
                p[i] -= base[i] * pontosFuncao[k];
                p[i + 1] += base[i];
            }
            base = p;

            for(int i = 0; i < n - k - 1; i++){
                diff[i] = (diff[i+1]-diff[i])/(pontosFuncao[i+k+1]- pontosFuncao[i]);
            }
        }
        StringBuilder s = new StringBuilder();

        for(int g = n - 1; g >= 0; g--){
            double v = c[g];
            if(Math.abs(v) < 1e-7){
             continue;   
            }
            if(s.length() > 0){
                if(v < 0){
                    s.append(" - ");
                }
                else{
                 s.append(" + ");   
                }
            }
            else if(v < 0){
                s.append("-");
            }
            v = Math.abs(v);
            
            if(g == 0 || Math.abs(v - 1) >= 1e-7){
                if(Math.abs(v - Math.rint(v)) < 1e-7){
                    s.append((long) Math.rint(v));
                }
                else{
                    s.append(String.format(Locale.US, "%.4f", v).replaceAll("0+$", "").replaceAll("\\.$", ""));
                }
            }
            if(g > 0){
                if(Math.abs(v - 1) >= 1e-7){
                    s.append("*");
                }
                s.append("x");
                if(g > 1){ 
                    s.append("^").append(g);
                }
            }
        }

        if(s.length() == 0){
            return "0";
        }
        return s.toString();
    }
}
