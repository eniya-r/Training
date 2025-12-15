import java.util.*;

class Student1 {
    String name;
    int age;

    Student1(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

public class ComparatorExample {
    public static void main(String[] args) {
        List<Student1> students = new ArrayList<>();
        students.add(new Student1("Charlie", 22));
        students.add(new Student1("Alice", 20));
        students.add(new Student1("Bob", 21));

        System.out.println("Original: " + students);

        Comparator<Student1> byAge = (s1, s2) -> Integer.compare(s1.age, s2.age);

        students.sort(byAge);

        System.out.println("Sorted by age: " + students);
    }
}
