public class Montador{
    private String expressao;
    private int pos = 0;
    private char charAtual;

    private void avancar() {
        if (pos < expressao.length()) {
            charAtual = expressao.charAt(pos++);
        } else {
            charAtual = '\0';
        }
    }

    public Montador(String expressao){
        this.expressao = formatarFuncao(expressao);
        avancar();
    }

    public No montar(){
        No no = montarAdSub();

        if (charAtual != '\0') {
            throw new RuntimeException("Caractere inesperado: " + charAtual);
        }

        return no;
    }

    private No montarDentro(){
        return montarAdSub();
    }

    private No montarAdSub(){
        No no = montarMulDiv();

        while(charAtual == '+' || charAtual == '-'){
            char operador = charAtual;
            avancar();
            No valorDireita = montarMulDiv();
            no = new NoOperador(String.valueOf(operador), no, valorDireita);
        }
        return no;
    }

    private No montarMulDiv(){
        No no = montarSinal();

        while(charAtual == '*' || charAtual == '/'){
            char operador = charAtual;
            avancar();
            No valorDireita = montarSinal();
            no = new NoOperador(String.valueOf(operador), no, valorDireita);
        }
        return no;
    }

    private No montarSinal(){ 
        if(charAtual == '+'){
            avancar();
            return new NoSinal("+", montarSinal());
        }
        if(charAtual == '-'){
            avancar();
            return new NoSinal("-", montarSinal());
        }
        return montarExpo();
    }

    private No montarExpo(){
        No no = montarTermo();

        while(charAtual == '^'){
            char operador = charAtual;
            avancar();
            No valorDireita = montarExpo();
            no = new NoOperador(String.valueOf(operador), no, valorDireita);
        }
        return no;
    }

    private No montarTermo(){
        
        if(charAtual == '('){
            avancar();
            No no = montarDentro();
            if (charAtual != ')'){
                throw new RuntimeException("Esperava ')'");
            }
            avancar();
            return no;
        }

        if (Character.isDigit(charAtual) || charAtual == '.' || charAtual == ',') {
            StringBuilder sb = new StringBuilder();
            while (Character.isDigit(charAtual) || charAtual == '.' || charAtual == ',') {
                sb.append(charAtual);
                avancar();
            }

            double valorFinal = Double.parseDouble(sb.toString().replace(',', '.'));
            return new NoNumero(valorFinal);
        }

        
        if (Character.isLetter(charAtual)) {
            StringBuilder sb = new StringBuilder();
            while (Character.isLetter(charAtual)) {
                sb.append(charAtual);
                avancar();
            }

            String op = sb.toString();

            if (op.equalsIgnoreCase("x")) {
                return new NoVariavel();
            }
            if (op.equalsIgnoreCase("e")) {
                return new NoNumero(Math.E);
            }
            if (op.equalsIgnoreCase("pi")) {
                return new NoNumero(Math.PI);
            }
            if (charAtual == '(') {
                avancar();
                No argumento = montarDentro();

                if (charAtual != ')') {
                    throw new RuntimeException("Esperava ')'");
                }
                avancar();

                return new NoFuncao(op, argumento);
            }
            throw new RuntimeException("Operador inválido: " + op);
        }
        throw new RuntimeException("Caractere não existe: " + charAtual);
    }

    private String formatarFuncao(String funcao){
        StringBuilder formatada = new StringBuilder();

        for (int i = 0; i < funcao.length(); i++) {
            char atual = funcao.charAt(i);

            if (Character.isWhitespace(atual)) {
                continue;
            }

            if (formatada.length() > 0) {
                char anterior = formatada.charAt(formatada.length() - 1);

                if ((Character.isDigit(anterior) || anterior == ')') && (Character.isLetter(atual) || atual == '(')) {
                    formatada.append('*');
                }
            }

            formatada.append(atual);
        }
        return formatada.toString();
    }
}