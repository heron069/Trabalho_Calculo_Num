package com.calculo.trabV1.excecoes;

public class FuncaoInvalidaException extends RuntimeException{
    public FuncaoInvalidaException(){
        super("Funcao Invalida ou Desconhecida encontrada!");
    }
    public FuncaoInvalidaException(String msg){
        super(msg);
    }
}
