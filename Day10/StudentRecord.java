import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class StudentRecord {

    private final String id;
    private final String name;
    private final LocalDate enrollmentDate;
    private final List<String> courses;

    public StudentRecord(String id, String name, LocalDate enrollmentDate, List<String> courses) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("id must be non-empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("name must be non-empty");
        }
        if (enrollmentDate == null) {
            throw new IllegalArgumentException("enrollmentDate must not be null");
        }
        if (courses == null) {
            throw new IllegalArgumentException("courses must not be null");
        }

        this.id = id;
        this.name = name;
        this.enrollmentDate = enrollmentDate;

        List<String> tmp = new ArrayList<>(courses);
        this.courses = Collections.unmodifiableList(tmp);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public List<String> getCourses() {
        return courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentRecord)) return false;
        StudentRecord other = (StudentRecord) o;
        return Objects.equals(this.id, other.id)
                && Objects.equals(this.name, other.name)
                && Objects.equals(this.enrollmentDate, other.enrollmentDate)
                && Objects.equals(this.courses, other.courses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, enrollmentDate, courses);
    }

    @Override
    public String toString() {
        return String.format(
                "StudentRecord{id='%s', name='%s', enrollmentDate=%s, courses=%s}",
                id, name, enrollmentDate, courses
        );
    }

    public static void main(String[] args) {
        List<String> initialCourses = new ArrayList<>();
        initialCourses.add("Java Basics");
        initialCourses.add("Data Structures");

        StudentRecord sr = new StudentRecord(
                "S-1001",
                "Alice",
                LocalDate.of(2025, 8, 1),
                initialCourses
        );

        System.out.println(sr);

        initialCourses.add("Algorithms");
        System.out.println("After external list change: " + sr.getCourses());

    }
}
