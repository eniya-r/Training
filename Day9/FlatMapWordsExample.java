import java.util.List;
import java.util.stream.Collectors;
import java.util.Arrays;

public class FlatMapWordsExample {
    public static void main(String[] args) {
        List<String> sentences = List.of(
                "Streams make code expressive",
                "flatMap flattens nested data"
        );

        List<String> words = sentences.stream()
                .flatMap(sentence -> Arrays.stream(sentence.split("\\s+")))
                .collect(Collectors.toList());

        System.out.println(words);
    }
}
