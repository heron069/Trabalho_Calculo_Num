package com.calculo.trabV1.geral;
import com.calculo.trabV1.excecoes.*;

public class NoOperador implements No{
    private String operador;
    private No esq;
    private No dir;

    public NoOperador(String operador, No esq, No dir){
        this.operador = operador;
        this.esq = esq;
        this.dir = dir;
    }

    @Override 
    public double interpretar(double x){

        double valorEsquerda = esq.interpretar(x);
        double valorDireita = dir.interpretar(x);

        switch (operador) {
            case "+": return valorEsquerda + valorDireita;
            case "-": return valorEsquerda - valorDireita;
            case "*": return valorEsquerda * valorDireita;
            case "/":
                if (valorDireita == 0.0) {
                    throw new DivisaoPorZeroException();
                }
                return valorEsquerda / valorDireita;
            case "^": 
                double resultado = Math.pow(valorEsquerda, valorDireita);
                if(Double.isNaN(resultado)){
                    throw new DominioMatematicoException("Potência fora do domínio dos números reais.");
                }

                if(!Double.isFinite(resultado)){
                    throw new DominioMatematicoException("O resultado da potência não é finito.");
                }
            return resultado;

            default: 
                throw new ExpressaoInvalidaException("Operador desconhecido: " + operador);
        }
    }

    @Override
    public No derivar(){

        switch(operador){
            case "+":
                return new NoOperador("+", esq.derivar(),dir.derivar()); 

            case "-":
                return new NoOperador("-", esq.derivar(), dir.derivar());
            
            case "*":
                return new NoOperador("+",
                                    new NoOperador("*",esq.derivar(),dir),
                                    new NoOperador("*",esq,dir.derivar()));

            case "/":
                return new NoOperador("/",
                                    new NoOperador("-",
                                                new NoOperador("*",
                                                            esq.derivar(),
                                                            dir),
                                                new NoOperador("*",
                                                            esq,
                                                            dir.derivar())),
                                    new NoOperador("^",dir,new NoNumero(2)));

            case "^":
                if(dir  instanceof NoNumero){
                    return new NoOperador("*",
                                        new NoOperador("*",
                                                        dir,
                                                        new NoOperador("^",
                                                                        esq,
                                                                        new NoOperador("-",
                                                                                        dir,
                                                                                        new NoNumero(1)))),
                                        esq.derivar());
                }

                if (esq instanceof NoNumero) {
                    return new NoOperador("*",
                        new NoOperador("^", esq, dir),
                        new NoOperador("*",
                            new NoFuncao("ln", esq),
                            dir.derivar()
                        )
                    );
                }

                return new NoOperador("*",
                                        new NoOperador("^", esq, dir),
                                        new NoOperador("+",
                                                    new NoOperador("*",
                                                                dir.derivar(),
                                                                new NoFuncao("ln", esq)),
                                        new NoOperador("*",
                                                    dir,
                                                    new NoOperador("/",
                                                                esq.derivar(),
                                                                esq))));

            default: 
                throw new ExpressaoInvalidaException("Operador desconhecido: " + operador);
        }
    }

    public String getOperador() {
        return operador;
    }

    public No getEsquerda() {
        return esq;
    }

    public No getDireita() {
        return dir;
    }

    @Override
    public String toString(){
        return "(" + esq.toString() + " " + operador + " " + dir.toString() + ")";
    }
}
