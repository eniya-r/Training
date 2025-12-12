public class Student {
    String name;
    int rollNumber;
    double[] marks;
    int totalClasses;
    int attendedClasses;

    public Student(String name, int rollNumber, double[] marks, int totalClasses, int attendedClasses) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
        this.totalClasses = totalClasses;
        this.attendedClasses = attendedClasses;
    }

    public double calculateAveragePercentage() {
        double sum = 0.0;
        for (double m : marks) {
            sum += m;
        }
        double average = sum / marks.length;
        return average;
    }

    public String calculateGrade() {
        double avg = calculateAveragePercentage();
        if (avg >= 90) return "A+";
        else if (avg >= 80) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 60) return "C";
        else if (avg >= 50) return "D";
        else return "F";
    }

    public double getAttendancePercentage() {
        if (totalClasses == 0) {
            return 0.0;
        }
        return (attendedClasses * 100.0) / totalClasses;
    }

    public void printReport() {
        System.out.println("----- Student Report -----");
        System.out.println("Name: " + name);
        System.out.println("Roll No: " + rollNumber);

        System.out.print("Marks: ");
        for (int i = 0; i < marks.length; i++) {
            System.out.print(marks[i]);
            if (i < marks.length - 1) System.out.print(", ");
        }
        System.out.println();

        System.out.printf("Average: %.2f%%\n", calculateAveragePercentage());
        System.out.println("Grade: " + calculateGrade());
        System.out.printf("Attendance: %.2f%%\n", getAttendancePercentage());
    }

    public static void main(String[] args) {
        double[] marks = {85.0, 92.5, 76.0, 88.0, 90.0};
        int totalClasses = 40;
        int attendedClasses = 36;

        Student s = new Student("Eniya", 101, marks, totalClasses, attendedClasses);

        s.printReport();
    }
}
