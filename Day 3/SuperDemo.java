class Animal {
    String name;

    Animal(String name) {
        this.name = name;
        System.out.println("Animal created: " + name);
    }
}

class Dog extends Animal {
    String breed;

    Dog(String name, String breed) {
        super(name); // ✅ Call parent constructor first
        this.breed = breed;
        System.out.println("Dog created: " + name + " (" + breed + ")");
    }
}

public class SuperDemo {
    public static void main(String[] args) {
        Dog d = new Dog("Buddy", "Labrador");
    }
}
