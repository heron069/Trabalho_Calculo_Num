import java.util.Scanner;

public class AppCalc {
    public static void main (String[] args){

        Scanner scan = new Scanner(System.in);
        
        String funcao = scan.nextLine();

        double valor = scan.nextDouble();

        Interpretador interpretador = new Interpretador(funcao);

        double resultado = interpretador.calcular(valor);
        double resultadoDerivada = interpretador.calcularDerivada(valor);

        System.out.println("Funcao Montada e Resultado:");
        System.out.println(interpretador.getFuncaoFormatada());
        System.out.println(resultado);

        System.out.println("Funcao Derivada Montada e Resultado:");
        System.out.println(interpretador.getFuncaoDerivadaFormatada());
        System.out.println(resultadoDerivada);
    }
}
