import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App2 {
    public static void main(String[] args) throws Exception {
        //int[] number = {-2,4,2,5,3};
        //int[] number = {-2,-3};
        //int[] number = {-3,-1,1,2,3,4,6};
        int[] number = {1,1,2,3};
        int faltante=0;
        int numIni = Arrays.stream(number).min().getAsInt();
        int numFin = Arrays.stream(number).max().getAsInt();
        System.out.println(numIni+" "+numFin);
        for(int numInp=numIni;numInp<=numFin;numInp++){
            final int value=numInp;
            System.out.println("Se evalua: "+value+ " "+Arrays.stream(number).anyMatch(n->n==value));
            if(numInp>0 && !Arrays.stream(number).anyMatch(n->n==value)){
                faltante=value;
                break;
            }
        }
        if(numIni<0 && faltante==0){
            System.out.println("NUMERO FALTANTE: "+faltante);
        }else if(faltante==0){
            System.out.println("NUMERO FALTANTE: "+(numFin+1));
        }else{
            System.out.println("NUMERO FALTANTE: "+ faltante);
        }
    }
}
