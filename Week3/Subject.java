package Week3;

import java.sql.*;

public class Subject {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/SubjectDB"; // Update with your database name
        String USER = "root";
        String PASS = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            // Display all subjects using an Updatable ResultSet
            String queryAllSubjects = "SELECT * FROM Subject";
            ResultSet rs = stmt.executeQuery(queryAllSubjects);
            System.out.println("All Subjects:");
            System.out.println("Code Name Department Credits");
            while (rs.next()) {
                System.out.println(rs.getString("Code") + 
                                   rs.getString("Name") + 
                                   rs.getString("Department") + 
                                   rs.getInt("Credits"));
            }

            // Update the name of the subject from "Java Programming Lab" to "Advanced Java Programming Lab" where Code = CSL56
            rs.beforeFirst(); // Move cursor to start
            while (rs.next()) {
                if (rs.getString("Code").equals("CSL56")) {
                    rs.updateString("Name", "Advanced Java Programming Lab");
                    rs.updateRow();
                }
            }
            System.out.println("\nUpdated subject with Code 'CSL56'.");

            // Delete the subject "System Programming" using PreparedStatement
            String deleteQuery = "DELETE FROM Subject WHERE Name = ?";
            PreparedStatement psDelete = conn.prepareStatement(deleteQuery);
            psDelete.setString(1, "System Programming");
            int rowsDeleted = psDelete.executeUpdate();
            System.out.println("\nDeleted rows: " + rowsDeleted);

            // Display all subjects after the updates
            rs = stmt.executeQuery(queryAllSubjects);
            System.out.println("\nAll Subjects after updates:");
            System.out.println("Code Name Department Credits");
            while (rs.next()) {
                System.out.println(rs.getString("Code") + " " + 
                                   rs.getString("Name") + " " + 
                                   rs.getString("Department") + " " + 
                                   rs.getInt("Credits"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
