package excecoes;

public class DivisaoPorZeroException extends ArithmeticException {
    public DivisaoPorZeroException(){
        super("Divisao por zero!");
    }

    public DivisaoPorZeroException(String msg){
        super(msg);
    }
}