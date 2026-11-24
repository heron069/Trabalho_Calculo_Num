package excecoes;

public class ExpressaoInvalidaException extends RuntimeException{
    public ExpressaoInvalidaException(){
        super("Expressão Matemática Invalida encontrada!");
    }
    public ExpressaoInvalidaException(String msg){
        super(msg);
    }
}
