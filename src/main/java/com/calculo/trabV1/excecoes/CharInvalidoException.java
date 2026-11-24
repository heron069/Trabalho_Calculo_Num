package excecoes;

public class CharInvalidoException extends RuntimeException{
    public CharInvalidoException(){
        super("Caractere Invalido encontrado!");
    }

    public CharInvalidoException(String msg){
        super(msg);
    }
}