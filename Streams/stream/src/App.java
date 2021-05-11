import java.util.stream.IntStream;
import java.util.stream.Stream;

public class App {
    public static void main(String[] args) throws Exception {
        
        // 1. Uso de IntStream para simular un for y realizar acciones de acuerdo a la posición que estamos
        System.out.println("=============================");
        System.out.println("Recorrido simple con IntSream");
        IntStream
            .range(1, 10)
            .forEach(System.out::print);
        System.out.println("");

        
        // 2. Uso de IntStream para recorrer de acuerdo aun rango determinado
        System.out.println("=============================");
        System.out.println("Recorrido con InputStream contemplando la posicion actual");
        IntStream
            .range(1, 10) //Se define el rango a recorrer  LIKE a FOR(i++)
            .forEach(x ->  // se utiliza x -> para indicar que en "X" se guarde el rango en el que estamos posicionado
            System.out.println("Se esta recorriendo el elemento: "+ x));  //ForEach -> como su nombre lo dice, es para indicar actividades a realizar por cada rango en proceso
        System.out.println("");

        // 3. Uso de IntStream para recorrer de acuerdo aun rango determinado INDICANDO un skip para contar a partir del numero que indicamos
        System.out.println("=============================");
        System.out.println("Recorrido con InputStream contemplando la posicion actual");
        IntStream
            .range(1, 10) //Se define el rango a recorrer  LIKE a FOR(i++)
            .skip(5)
            .forEach(x ->  // se utiliza x -> para indicar que en "X" se guarde el rango en el que estamos posicionado
            System.out.println("Se esta recorriendo el elemento: "+ x));  //ForEach -> como su nombre lo dice, es para indicar actividades a realizar por cada rango en proceso
        System.out.println("");

        // 4. Uso basico de operador SUM() en stream
        System.out.println("=============================");
        System.out.println("Uso de operador SUM() en STREAM");
        System.out.println("Suma de IntStream(1,5): "+
            IntStream
                .range(1, 5)
                .sum()
        );
        System.out.println("");

        // 5. Uso de Stream.of,  Filtrado y findFirst
        System.out.println("=============================");
        System.out.println("Uso de Stream.of y SORTED()");
        System.out.println("Antes de Sorted: Erick | Alan | Inaki");
        Stream.of("Erick","Alan","Inaki")
            .sorted() //Ordena el elemento introducion en Stream.of
            .forEach(
                x -> System.out.print(x+" | "));
        System.out.println("");

        System.out.println("=============================");
        System.out.println("Uso de Stream.of,  SORTED() y findFirst()");
        System.out.println("Antes de Sorted: Erick | Alan | Inaki");
            Stream.of("Erick","Alan","Inaki")
                .sorted()
                .findFirst()
                .ifPresent(System.out::println);
        System.out.println("");
    }
}
