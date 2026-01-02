import java.util.List;
import java.util.Optional;

public class ReduceMaxExample {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(10, 3, 27, 4);

        Optional<Integer> max = numbers.stream()
                .reduce((a, b) -> a > b ? a : b);

        System.out.println("Max = " + max.orElse(Integer.MIN_VALUE));
    }
}
