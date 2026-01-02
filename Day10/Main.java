import java.util.Objects;
class Box<T> {
    private T value;

    public Box(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Box(" + Objects.toString(value) + ")";
    }
}

public class Main {
    public static void main(String[] args) {
        Box<Integer> intBox = new Box<>(42);
        System.out.println(intBox.get());
        intBox.set(100);
        System.out.println(intBox.get());

        Box<String> strBox = new Box<>("Hello");
        System.out.println(strBox.get());
    }
}
