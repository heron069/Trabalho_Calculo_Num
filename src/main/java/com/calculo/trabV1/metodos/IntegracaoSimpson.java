package com.calculo.trabV1.metodos;
import com.calculo.trabV1.excecoes.*;
import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.resultados.ResultadoIntegracao;

public class IntegracaoSimpson{
    private Interpretador interpretador;
    private double resultado;
    private double minIntervalo;
    private double maxIntervalo;
    private double altura;
    private int n;

    public IntegracaoSimpson(Interpretador interpretador, double minIntervalo, double maxIntervalo, int n){
        if(n < 2 || (n%2) == 1){
            throw new RuntimeException("N impar para Simpson");
        }
        if(maxIntervalo < minIntervalo){
            throw new RuntimeException("Intervalo Invalido");
        }
        this.interpretador = interpretador;
        this.minIntervalo = minIntervalo;
        this.maxIntervalo = maxIntervalo;
        this.n = n;
        this.altura = (maxIntervalo-minIntervalo)/n;
    }

    public ResultadoIntegracao calcularIntegral(){
        double valor = 0;

        for(int i = 0; i <= n; i++){
            if(i == 0){
                valor += interpretador.calcular(minIntervalo);
                continue;
            }
            if(i == n){
                valor += interpretador.calcular(maxIntervalo);
                continue;
            }

            if((i%2) == 0){
                valor+= 2 * interpretador.calcular(minIntervalo+(altura*i));
            }
            if((i%2) == 1){
                valor+= 4 * interpretador.calcular(minIntervalo+(altura*i));
            }
        }
        resultado = (altura/3)*valor;

        return new ResultadoIntegracao(resultado);
    }
}