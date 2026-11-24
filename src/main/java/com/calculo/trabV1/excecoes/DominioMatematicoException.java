package excecoes;

public class DominioMatematicoException extends RuntimeException{
    public DominioMatematicoException(){
        super("Valor fora do Dominio Matematico encontrado!");
    }
    public DominioMatematicoException(String msg){
        super(msg);
    }
}
