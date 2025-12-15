class User {
    String name;
    int age;

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

public class FactoryDemo {
    public static void main(String[] args) {
        User u = createUser("Eniya", 22);
        System.out.println(u.name + ", " + u.age);
    }

    public static User createUser(String name, int age) {
        return new User(name, age);
    }
}
