package com.calculo.trabV1.geral;


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
            case "/": return valorEsquerda / valorDireita;
            case "^": return Math.pow(valorEsquerda, valorDireita);
            default: throw new RuntimeException("Operador desconhecido: " + operador);
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

            default: throw new RuntimeException("Operador desconhecido: " + operador);
        }
    }

    @Override
    public String toString(){
        return "(" + esq.toString() + " " + operador + " " + dir.toString() + ")";
    }
}
