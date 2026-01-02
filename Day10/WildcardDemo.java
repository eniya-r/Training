import java.util.*;

public class WildcardDemo {
    public static void printList(List<?> list) {
        for (Object obj : list) {
            System.out.println(obj);
        }
    }

    public static double sumNumbers(List<? extends Number> numbers) {
        double sum = 0;
        for (Number n : numbers) {
            sum += n.doubleValue();
        }
        return sum;
    }

    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(1, 2, 3);
        List<Double> doubleList = Arrays.asList(1.5, 2.5);

        System.out.println("Printing any list:");
        printList(intList);
        printList(doubleList);

        System.out.println("Sum of numbers:");
        System.out.println(sumNumbers(intList));
        System.out.println(sumNumbers(doubleList));
    }
}
