public class Interpretador {
    private No arvore;
    private No arvoreDerivada;

    public Interpretador(String funcao){
        Montador montador = new Montador(funcao);
        this.arvore = montador.montar();
        this.arvoreDerivada = arvore.derivar();
    }

    public double calcular(double x) {
        return arvore.interpretar(x);
    }
    public double calcularDerivada(double x) {
    return arvoreDerivada.interpretar(x);
}

    public String getFuncaoFormatada() {
        return arvore.toString();
    }
    public String getFuncaoDerivadaFormatada() {
        return arvoreDerivada.toString();
    }
}
