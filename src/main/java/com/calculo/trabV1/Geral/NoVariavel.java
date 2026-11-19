public class NoVariavel implements No{
    @Override 
    public double interpretar(double x){
        return x;
    }

    @Override 
    public No derivar(){
        return new NoNumero(1);
    }

    @Override
    public String toString(){
        return "x";
    }
}
