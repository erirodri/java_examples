import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App2 {
    public static void main(String[] args) throws Exception {
        //int[] number = {-2,4,2,5,3};
        //int[] number = {-2,-3};
        int[] number = {-3,-1,1,2,3,4};
        int inicial = Arrays.stream(number).min().getAsInt();
        int nFinal = Arrays.stream(number).max().getAsInt();
        int faltante=0;
        for(int i =inicial;i<=nFinal;i++){
            final int value=i;
            if(i>=1 && Arrays.stream(number).allMatch(a -> a!=value)){
                faltante=value;
                break;
            }else{

            }
        }
        if(faltante!=0){
            System.out.println("EL NUMERO FALTANTE ES: "+faltante);
        }else if(nFinal<0){
            System.out.println("EL NUMERO FALTANTE ES: "+1);
        }else{
            System.out.println("EL NUMERO FALTANTE ES: "+(nFinal+1));
        }

    }
}
