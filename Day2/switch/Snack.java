import java.util.Scanner;

public class Snack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Snack Menu");
        System.out.println("1) Samosa");
        System.out.println("2) Sandwich");
        System.out.println("3) Juice");
        System.out.print("Choose an option (1-3): ");

        int choice = scanner.nextInt();
        String snack;

        switch (choice) {
            case 1:
                snack = "You chose Samosa go to counter 1";
                break;
            case 2:
                snack = "You chose Sandwich go to counter 2";
                break;
            case 3:
                snack = "You chose Juice go to counter 3";
                break;
            default:
                snack = "Invalid choice";
        }

        System.out.println(snack);
        scanner.close();
    }
}
