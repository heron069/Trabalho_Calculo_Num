package com.calculo.trabV1.integracaoNumerica;
import com.calculo.trabV1.excecoes.*;
import com.calculo.trabV1.geral.*;
import com.calculo.trabV1.resultados.ResultadoIntegracao;

public class IntegracaoTrapezio{
    private Interpretador interpretador;
    private double resultado;
    private double minIntervalo;
    private double maxIntervalo;
    private double altura;
    private int n;

    public IntegracaoTrapezio(Interpretador interpretador, double minIntervalo, double maxIntervalo, int n){
        if (maxIntervalo < minIntervalo) {
            throw new ArgumentoInvalidoException("O limite máximo deve ser maior que o mínimo.");
        }
        if (n <= 0) {
            throw new ArgumentoInvalidoException("O número de subintervalos deve ser maior que zero.");
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
            valor += 2 * interpretador.calcular(minIntervalo+(altura*i));
        }
        resultado = (altura/2) * valor;

        return new ResultadoIntegracao(resultado);
    }
}
