public class Parameterized {
    String name;
    int age;


    public Parameterized(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public static void main(String[] args) {
        Parameterized p = new Parameterized("Eniya", 22);
        System.out.println(p.name + ", " + p.age);
    }
}
