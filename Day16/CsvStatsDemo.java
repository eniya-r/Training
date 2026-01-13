import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvStatsDemo {

    public static void main(String[] args) {
        Path csvPath = Paths.get("people.csv");

        try {
            printAgeStats(csvPath);
        } catch (IOException e) {
            System.err.println("Failed to read CSV: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void printAgeStats(Path csvPath) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(csvPath, StandardCharsets.UTF_8)) {

            String header = reader.readLine();
            if (header == null) {
                System.out.println("Empty file: " + csvPath.toAbsolutePath());
                return;
            }

            int count = 0;
            long sum = 0;
            Integer min = null;
            Integer max = null;

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split(",", -1); // -1 keeps empty trailing fields
                if (parts.length < 2) {
                    continue;
                }

                String name = parts[0].trim();
                String ageStr = parts[1].trim();

                int age;
                try {
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException nfe) {
                    continue;
                }

                count++;
                sum += age;
                if (min == null || age < min) min = age;
                if (max == null || age > max) max = age;

            }

            System.out.println("CSV file: " + csvPath.toAbsolutePath());
            System.out.println("Rows parsed (with valid ages): " + count);

            if (count == 0) {
                System.out.println("No valid age values found.");
                return;
            }

            double average = (double) sum / count;

            System.out.println("Age sum   : " + sum);
            System.out.println("Age avg   : " + String.format("%.2f", average));
            System.out.println("Age min   : " + min);
            System.out.println("Age max   : " + max);
        }
    }
}
