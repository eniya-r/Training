import java.util.*;

public class MapExample {
    public static void main(String[] args) {
        Map<Integer, String> fruits = new HashMap<>();

        fruits.put(1, "Apple");
        fruits.put(2, "Banana");
        fruits.put(3, "Apple");

        System.out.println("Fruits Map: " + fruits);
        System.out.println("Fruit with key 2: " + fruits.get(2));
    }
}
