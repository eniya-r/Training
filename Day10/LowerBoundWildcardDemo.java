import java.util.ArrayList;
import java.util.List;

public class LowerBoundWildcardDemo {

    public static void addIntegers(List<? super Integer> sink) {
        sink.add(10);
        sink.add(20);
        sink.add(30);
        for (Object o : sink) {
            System.out.println(o);
        }
        System.out.println(); 
    }

    public static void main(String[] args) {
        List<Integer> intList = new ArrayList<>();
        List<Number> numList = new ArrayList<>();
        List<Object> objList = new ArrayList<>();

        System.out.println("Adding to List<Integer>:");
        addIntegers(intList); 

        System.out.println("Adding to List<Number>:");
        addIntegers(numList); 

        System.out.println("Adding to List<Object>:");
        addIntegers(objList); 
    }
}
