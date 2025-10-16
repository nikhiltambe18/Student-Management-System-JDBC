import java.sql.*;
import java.util.Scanner;

public class StudentManagementSystem {
    private static Connection conn = DBConnection.getConnection();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Student Management System =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> viewStudents();
                case 3 -> updateStudent();
                case 4 -> deleteStudent();
                case 5 -> {
                    System.out.println("Exiting...");
                    System.exit(0);
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void addStudent() {
        try {
            System.out.print("Enter Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter Grade: ");
            String grade = sc.nextLine();

            String sql = "INSERT INTO students(name, age, grade) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, grade);
            stmt.executeUpdate();
            System.out.println("✅ Student added successfully!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void viewStudents() {
        try {
            String sql = "SELECT * FROM students";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\nID\tName\tAge\tGrade");
            System.out.println("------------------------------------");
            while (rs.next()) {
                System.out.printf("%d\t%s\t%d\t%s%n",
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("grade"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent() {
        try {
            System.out.print("Enter Student ID to Update: ");
            int id = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter New Name: ");
            String name = sc.nextLine();
            System.out.print("Enter New Age: ");
            int age = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter New Grade: ");
            String grade = sc.nextLine();

            String sql = "UPDATE students SET name=?, age=?, grade=? WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setInt(2, age);
            stmt.setString(3, grade);
            stmt.setInt(4, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) System.out.println("✅ Student updated successfully!");
            else System.out.println("❌ Student not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent() {
        try {
            System.out.print("Enter Student ID to Delete: ");
            int id = sc.nextInt();

            String sql = "DELETE FROM students WHERE id=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();

            if (rows > 0) System.out.println("✅ Student deleted successfully!");
            else System.out.println("❌ Student not found!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

