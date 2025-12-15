import java.util.*;

public class ListExample {
    public static void main(String[] args) {
        List<String> fruits = new ArrayList<>();

        fruits.add("Apple");
        fruits.add("Banana");
        fruits.add("Apple");
        System.out.println(fruits.get(0));
        System.out.println(fruits.size());
//      fruits.remove("Apple");
//      System.out.println(fruits.contains("Banana"));

        System.out.println("Fruits List: " + fruits);
    }
}