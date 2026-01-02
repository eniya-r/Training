import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.util.*;
import java.util.stream.*;

public class App {
    static class Person {
        final int id;
        final String name;
        final Optional<Integer> age;
        final String city;
        final String department;
        final String email;
        final int salary;
        final String joinDate;
        final boolean active;
        final double rating;

        Person(int id, String name, Optional<Integer> age, String city,
               String department, String email, int salary,
               String joinDate, boolean active, double rating) {
            this.id = id;
            this.name = name;
            this.age = age;
            this.city = city;
            this.department = department;
            this.email = email;
            this.salary = salary;
            this.joinDate = joinDate;
            this.active = active;
            this.rating = rating;
        }

        @Override
        public String toString() {
            return String.format(
                    "Person{id=%d, name='%s', age=%s, city='%s', dept='%s', email='%s', salary=%d, join='%s', active=%s, rating=%.1f}",
                    id, name, age.map(String::valueOf).orElse("N/A"), city, department, email, salary, joinDate, active, rating
            );
        }
    }

    public static void main(String[] args) {

        Path csvPath = Path.of("people10.csv");

        List<Person> people;
        try (Stream<String> lines = Files.lines(csvPath)) {
            people = lines
                    .filter(line -> line != null && !line.isBlank())
                    .skip(1)
                    .map(App::parseLineToPerson)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
            return;
        }

        System.out.println("Parsed People (" + people.size() + " rows):");
        people.forEach(System.out::println);
        System.out.println("--------------------------------------------------");


        List<Person> inNoida = people.stream()
                .filter(p -> "Noida".equalsIgnoreCase(p.city))
                .collect(Collectors.toList());

        System.out.println("People in Noida:");
        inNoida.forEach(System.out::println);
        System.out.println("--------------------------------------------------");


        long missingAgeCount = people.stream()
                .filter(p -> p.age.isEmpty())
                .count();

        System.out.println("Rows with missing age: " + missingAgeCount);
        System.out.println("--------------------------------------------------");

        OptionalDouble avgAge = people.stream()
                .filter(p -> p.age.isPresent())
                .mapToInt(p -> p.age.orElse(0))
                .average();

        System.out.println("Average age (present only): " +
                (avgAge.isPresent() ? String.format("%.2f", avgAge.getAsDouble()) : "N/A"));
    }

    private static Person parseLineToPerson(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 10) {
            throw new IllegalArgumentException("Invalid line (expected 10 columns): " + line);
        }

        String idStr     = parts[0].trim();
        String name      = parts[1].trim();
        String ageStr    = parts[2].trim();
        String city      = parts[3].trim();
        String department= parts[4].trim();
        String email     = parts[5].trim();
        String salaryStr = parts[6].trim();
        String joinDate  = parts[7].trim();
        String activeStr = parts[8].trim();
        String ratingStr = parts[9].trim();

        int id = parseRequiredInt(idStr, "id");
        Optional<Integer> age = tryParseInt(ageStr);
        int salary = parseRequiredInt(salaryStr, "salary");
        boolean active = Boolean.parseBoolean(activeStr);
        double rating = tryParseDouble(ratingStr).orElse(0.0);

        return new Person(id, name, age, city, department, email, salary, joinDate, active, rating);
    }


    private static int parseRequiredInt(String s, String fieldName) {
        return tryParseInt(s).orElseThrow(() ->
                new IllegalArgumentException("Invalid " + fieldName + ": " + s));
    }

    private static Optional<Integer> tryParseInt(String s) {
        if (s == null || s.isBlank()) return Optional.empty();
        try { return Optional.of(Integer.parseInt(s)); }
        catch (NumberFormatException e) { return Optional.empty(); }
    }

    private static Optional<Double> tryParseDouble(String s) {
        if (s == null || s.isBlank()) return Optional.empty();
        try { return Optional.of(Double.parseDouble(s)); }
        catch (NumberFormatException e) { return Optional.empty(); }
    }
}
