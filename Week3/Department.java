package Week3;
import java.sql.*;
import java.util.Scanner;

public class Department {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/DepartmentDB"; // Update with your database name
        String USER = "root";
        String PASS = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             Scanner scanner = new Scanner(System.in)) {

            // i. Display details of all the Departments using Statement Object
            String queryAllDepartments = "SELECT * FROM Department";
            ResultSet rs = stmt.executeQuery(queryAllDepartments);
            System.out.println("All Departments:");
            System.out.println("Dept_ID Name Year_Established Head_Name No_of_Employees");
            while (rs.next()) {
                System.out.println(rs.getInt("Dept_ID") + 
                                   rs.getString("Name") + 
                                   rs.getInt("Year_Established") +
                                   rs.getString("Head_Name") + 
                                   rs.getInt("No_of_Employees"));
            }

            // ii. Display details of Departments established in the year 2000 using PreparedStatement
            System.out.print("\nEnter the year to search for Departments: ");
            int year = scanner.nextInt();
            String queryByYear = "SELECT * FROM Department WHERE Year_Established = ?";
            PreparedStatement psYear = conn.prepareStatement(queryByYear);
            psYear.setInt(1, year);
            rs = psYear.executeQuery();
            System.out.println("\nDepartments established in the year " + year + ":");
            if (!rs.isBeforeFirst()) {
                System.out.println("No departments found.");
            } else {
                System.out.println("Dept_ID Name Year_Established Head_Name No_of_Employees");
                while (rs.next()) {
                    System.out.println(rs.getInt("Dept_ID") + " " +
                                       rs.getString("Name") + " " +
                                       rs.getInt("Year_Established") + " " +
                                       rs.getString("Head_Name") + " " +
                                       rs.getInt("No_of_Employees"));
                }
            }

            // iii. Display details of Departments by reading Dept_ID and Name from user
            System.out.print("\nEnter Dept_ID to search: ");
            int deptID = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            System.out.print("Enter Department Name to search: ");
            String deptName = scanner.nextLine();
            String queryByIDAndName = "SELECT * FROM Department WHERE Dept_ID = ? AND Name = ?";
            PreparedStatement psIDAndName = conn.prepareStatement(queryByIDAndName);
            psIDAndName.setInt(1, deptID);
            psIDAndName.setString(2, deptName);
            rs = psIDAndName.executeQuery();
            System.out.println("\nDepartment details for Dept_ID " + deptID + " and Name " + deptName + ":");
            if (!rs.isBeforeFirst()) {
                System.out.println("No department found.");
            } else {
                System.out.println("Dept_ID Name Year_Established Head_Name No_of_Employees");
                while (rs.next()) {
                    System.out.println(rs.getInt("Dept_ID") + " " +
                                       rs.getString("Name") + " " +
                                       rs.getInt("Year_Established") + " " +
                                       rs.getString("Head_Name") + " " +
                                       rs.getInt("No_of_Employees"));
                }
            }

            // iv. Insert a new row using PreparedStatement object and display details
            System.out.print("\nEnter new Department details (Dept_ID, Name, Year_Established, Head_Name, No_of_Employees): ");
            int newDeptID = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            String newName = scanner.nextLine();
            int newYearEstablished = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            String newHeadName = scanner.nextLine();
            int newNoOfEmployees = scanner.nextInt();

            String insertDept = "INSERT INTO Department (Dept_ID, Name, Year_Established, Head_Name, No_of_Employees) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(insertDept);
            psInsert.setInt(1, newDeptID);
            psInsert.setString(2, newName);
            psInsert.setInt(3, newYearEstablished);
            psInsert.setString(4, newHeadName);
            psInsert.setInt(5, newNoOfEmployees);
            int rowsInserted = psInsert.executeUpdate();
            System.out.println("\nRows inserted: " + rowsInserted);

            // Display all details after insertion
            rs = stmt.executeQuery(queryAllDepartments);
            System.out.println("\nAll Departments after insertion:");
            System.out.println("Dept_ID Name Year_Established Head_Name No_of_Employees");
            while (rs.next()) {
                System.out.println(rs.getInt("Dept_ID") + " " +
                                   rs.getString("Name") + " " +
                                   rs.getInt("Year_Established") + " " +
                                   rs.getString("Head_Name") + " " +
                                   rs.getInt("No_of_Employees"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
