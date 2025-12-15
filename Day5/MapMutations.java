import java.util.*;

public class MapMutations {
    public static void main(String[] args) {
        Map<Integer, String> m = new HashMap<>();
        m.put(1, "A");
        m.put(2, "B");

        m.putIfAbsent(2, "X");
        m.putIfAbsent(3, "C");

        m.replace(1, "A", "Z");
        m.remove(2);

        System.out.println(m);
    }
}
