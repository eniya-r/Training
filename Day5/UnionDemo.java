import java.util.*;

public class UnionDemo {
    public static void main(String[] args) {
        Set<String> a = new HashSet<>(Arrays.asList("Apple", "Banana"));
        Set<String> b = new HashSet<>(Arrays.asList("Banana", "Cherry"));

        Set<String> union = new HashSet<>(a);
        union.addAll(b);
        System.out.println("Union: " + union);


        Set<String> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        System.out.println("Intersection: " + intersection);

    }
}
