import java.util.List;
import java.util.stream.Collectors;

public class FlatMapNestedListExample {
    public static void main(String[] args) {
        List<List<Integer>> nested = List.of(
                List.of(1, 2),
                List.of(3, 4, 5),
                List.of(6)
        );

        List<Integer> flat = nested.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        System.out.println(flat);
    }
}
