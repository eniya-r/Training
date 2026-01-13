import java.io.*;

public class SimpleSerializationDemo {
    public static void main(String[] args) {
        Person person = new Person("Eniya", 24);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("person.dat"))) {
            out.writeObject(person);
            System.out.println("Object has been serialized and saved to person.dat");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("person.dat"))) {
            Person restored = (Person) in.readObject();
            System.out.println("Object has been deserialized: " + restored);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}
