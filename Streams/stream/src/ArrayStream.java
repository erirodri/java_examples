import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ArrayStream {
    public static void main(String[] args) throws Exception {
        
        String[] names = {"Erick","Alan","Inaki","Juan","Imelda","Gaby"};
        int[] number = {-3,-2,1,2,3,4};

        //*************************************************
        //Aplicar Stream a un Array definido
        System.out.println("==========================");
        System.out.println("Ordenar array con .sorted()");
        System.out.print("Antes de stream: ");
        Arrays.stream(names) // Tambien se puede usar Stream.of(names)
            .forEach(x -> System.out.print(x+" | "));
        System.out.println("");
        System.out.print("Despues de stream: ");
        Arrays.stream(names)
            .sorted()
            .forEach(x -> System.out.print(x+" | "));
        System.out.println("");



        System.out.println("");
        //*************************************************
        //Filtro de Array con Stream

            // Filtrado filter()
        System.out.println("==========================");
        System.out.println("Filtro de Array con .filter()");
        Stream.of(names)
            .filter(x-> x.startsWith("I"))
            .forEach(x-> System.out.print(x+" | "));
        System.out.println("");
        System.out.println("Filtrar y ordenar los elementos filtrados");
        Stream.of(names)
            .filter(x-> x.startsWith("I")) // Localiza solos los datos que emmpiezan con la letra indicada y son con los que se trabajaran
            .sorted()
            .forEach(x-> System.out.print(x+" | "));

        System.out.println("");
        System.out.println("Operacion LowerCase");
        Stream.of(names)
            .map(x -> x.toLowerCase())
            .forEach(x -> System.out.print(x+ " | "));

        System.out.println("");
        System.out.println("Operacion LowerCase, despues filtrado y al final se ordena el resultado");
        Arrays.stream(names)
            .map(String::toLowerCase) // Recorre arreglo y aplica operación indicada dentro de ()
            .filter(x -> x.startsWith("i"))
            .sorted()
            .forEach(y -> System.out.print(y+ " | "));
        System.out.println("");

        //*************************************************
        //2. Operaciones recorriendo un Arreglo de Enteros
        System.out.println("==========================");
        System.out.println("Operaciones recorriendo un Arreglo de Enteros");
        System.out.println("SUMA: "+
            Arrays.stream(number)
            .sum() //Recorre el stream (array) en cuestion y va realizando la suma mientras guarda el resultado en memoria para despues mandarlo al sysout
        );
        System.out.println("MULTIPLICACION: "+
            Arrays.stream(number)
            .reduce(1, Math::multiplyExact) //Recorre el stream (array) en cuestion y va realizando la suma mientras guarda el resultado en memoria para despues mandarlo al sysout
        ); 
        System.out.println("");

        //*************************************************
        //Recorrer arreglo de arreglo
        System.out.println("==========================");
        System.out.println("Usando split para dividir contenido de arreglo por indice despues de coma");

        String[] list = {"Erick,15.8,H","Gaby,10,M","Alan,15.8,H","Inaki,8,H"};
        List<String> listString = new ArrayList<>();
        Stream.of(list).map(x -> x.split(","))
        .forEach((x)-> {
            listString.add(x[0]);
            listString.add(x[1]);
            listString.add(x[2]);
        });

        listString.forEach(x -> System.out.print(x+" | "));
        System.out.println("");
        System.out.println(listString.size());
        System.out.println(" ");

        //*************************************************
        //Crear arreglo a partir de otro

        System.out.println("CREAR ARREGO A PARTIR DE OTRO");
        int[] newNumber= Arrays.stream(number)
                .filter(m -> m>-1)
                .toArray();
        System.out.println("Antes de mover");
        Arrays.stream(number).forEach(m -> System.out.print(m+"|"));
        System.out.println(" ");
        System.out.println("Nuevo Array ");
        Arrays.stream(newNumber).forEach(m -> System.out.print(m+"|"));
        System.out.println(" ");

    }
}
