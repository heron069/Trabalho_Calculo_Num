package com.calculo.trabV1.geral;


public class NoFuncao implements No{
    private String funcao;
    private No argumento;

    public NoFuncao(String funcao, No argumento){
        this.funcao = funcao.toLowerCase();
        this.argumento = argumento;
    }

    @Override
    public double interpretar(double x){
        double valor = argumento.interpretar(x);
    
        switch(funcao){
            case "sin":
                return Math.sin(valor);
                
            case "cos":
                return Math.cos(valor);

            case "tg":
                return Math.tan(valor);

            case "log":
                return Math.log10(valor);
        
            case "sqrt":
                return Math.sqrt(valor);

            case "ln":
                return Math.log(valor);
            case "sec":
                return 1.0/(Math.cos(valor));

            case "cossec":
                return 1.0/(Math.sin(valor));

            case "cotg":
                return Math.cos(valor)/(Math.sin(valor));

            default:
                throw new RuntimeException("Função desconhecida: " + funcao);
        }
    }

    @Override 
    public No derivar(){
        switch (funcao) {
            case "sin":
                return new NoOperador("*",
                                     new NoFuncao("cos",argumento),
                                      argumento.derivar());
            
            case "cos":
                return new NoOperador("*",
                                     new NoSinal("-",
                                                new NoFuncao("sin",
                                                argumento)),
                                     argumento.derivar());
            
            case "tg":
                return new NoOperador("*",
                                     new NoOperador("^",
                                                    new NoFuncao("sec",
                                                    argumento),
                                                    new NoNumero(2)),
                                     argumento.derivar());
            
            case "log":
                return new NoOperador("*",
                                     new NoOperador("/",
                                     new NoNumero(1),
                                     new NoOperador("*",
                                                    argumento,
                                                    new NoFuncao("ln",
                                                                new NoNumero(10)))),
                                     argumento.derivar());

            case "sqrt":
                return new NoOperador("*",
                                    new NoOperador("*",
                                                new NoNumero(0.5),
                                                new NoOperador("^",
                                                            argumento,
                                                            new NoNumero(-0.5))),
                                    argumento.derivar());
            case "ln":
                return new NoOperador("*",
                                      new NoOperador("/",
                                                    new NoNumero(1),
                                                    argumento),
                                    argumento.derivar());
            
            case "sec":
                return new NoOperador("*",
                                    new NoOperador("*",
                                                  new NoFuncao("sec", argumento),
                                                  new NoFuncao("tg",argumento)),
                                    argumento.derivar());

            case "cossec":
                return new NoOperador("*",
                                    new NoOperador("*",
                                                new NoSinal("-",new NoFuncao("cossec",argumento)),
                                                new NoFuncao("cotg", argumento)) ,
                                    argumento.derivar());
            
            case "cotg":
                return new NoOperador("*",
                                    new NoSinal("-",new NoOperador("^",
                                                                         new NoFuncao("cossec",argumento),
                                                                         new NoNumero(2))),
                                    argumento.derivar());
            default:
                throw new RuntimeException("Função desconhecida: " + funcao);
        }
    }

    @Override
    public String toString(){
        return funcao+"("+argumento.toString()+")";
    }
}
