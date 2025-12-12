public class VarargsDemo {
    public static int sumAll(int... nums) {
        int total = 0;
        for (int n : nums) {
            total += n;
        }
        return total;
    }

    public static void main(String[] args) {
        System.out.println(sumAll());
        System.out.println(sumAll(1));
        System.out.println(sumAll(1, 2, 3));
    }
}
