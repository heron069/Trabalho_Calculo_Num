package com.calculo.trabV1.excecoes;

public class ArgumentoInvalidoException extends RuntimeException{
    public ArgumentoInvalidoException(){
        super("Argumento Invalido encontrado!");
    }

    public ArgumentoInvalidoException(String msg){
        super(msg);
    }
}
