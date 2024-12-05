package Week2;
import java.sql.*;
// executeQuery	    Read  (fetch data)	ResultSet	    SELECT
// executeUpdate	Write (modify data)	int(row count)	INSERT, UPDATE, DELETE,
public class Student {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/dbName";
        String USER = "root";
        String PASS = "";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (USN VARCHAR(20) PRIMARY KEY, Name VARCHAR(100), Semester INT, CGPA FLOAT)";
        String insertDataSQL = "INSERT INTO students (USN, Name, Semester, CGPA) VALUES ('1MS22CS301', 'Anika Sharma', 5, 8.5), ('1MS22CS302', 'Rahul Patel', 5, 7.5)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // Create table and insert data
            stmt.executeUpdate(createTableSQL);
            System.out.println("Table 'students' created or already exists.");

            stmt.executeUpdate(insertDataSQL);
            System.out.println("Sample data inserted into 'students' table.");

            // Query and display all students
            String queryAllStudents = "SELECT * FROM students";
            ResultSet rs = stmt.executeQuery(queryAllStudents);
            System.out.println("All Students:");
            System.out.println("USN Name Semester CGPA");
            while (rs.next()) {
                System.out.println(rs.getString("USN") + " " + rs.getString("Name") + " " + rs.getInt("Semester") + " " + rs.getFloat("CGPA"));
            }

            // Query and display students in the 5th Semester
            rs = stmt.executeQuery("SELECT * FROM students WHERE Semester = 5");
            System.out.println("Students in 5th Semester:");
            System.out.println("USN Name CGPA");
            while (rs.next()) {
                System.out.println(rs.getString("USN") + " " + rs.getString("Name") + " " + rs.getFloat("CGPA"));
            }

            // Query and display students with CGPA > 8.0 in the 5th Semester
            rs = stmt.executeQuery("SELECT * FROM students WHERE CGPA > 8.0 AND Semester = 5");
            System.out.println("Students with CGPA > 8.0 in 5th Semester:");
            System.out.println("USN Name CGPA");
            while (rs.next()) {
                System.out.println(rs.getString("USN") + " " + rs.getString("Name") + " " + rs.getFloat("CGPA"));
            }

            // Query and display total number of students with CGPA > 8.0
            rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM students WHERE CGPA > 8.0");
            if (rs.next()) {
                System.out.println("\nTotal number of students with CGPA > 8.0: " + rs.getInt("total"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
