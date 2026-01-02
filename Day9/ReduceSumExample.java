import java.util.List;

public class ReduceSumExample {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        int sum = numbers.stream()
                .reduce(0, (acc, n) -> acc + n);

        System.out.println("Sum = " + sum);
    }
}
