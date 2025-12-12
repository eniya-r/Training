public class Car {
    String brand;
    int year;

    void start() {
        System.out.println(brand + " (" + year + ") started.");
    }

    public static void main(String[] args) {
        Car c1 = new Car();
        c1.brand = "Toyota";
        c1.year = 2020;
        c1.start();


        Car c2 = new Car();
        c2.brand = "Honda";
        c2.year = 2022;
        c2.start();
    }
}
