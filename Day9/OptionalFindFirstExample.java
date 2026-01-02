import java.util.List;
import java.util.Optional;

public class OptionalFindFirstExample {
    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 3, 7, 9, 15);

        Optional<Integer> firstEven = numbers.stream()
                .filter(n -> n % 2 == 0)
                .findFirst();
        System.out.println(
                "First even = " + firstEven.orElse(-1)
        );
    }
}
