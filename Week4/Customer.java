package Week4;
import java.sql.*;

public class Customer {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/CustomerDB"; // Update with your database name
        String USER = "root";
        String PASS = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {

            // i. Insert rows using PreparedStatement
            String insertQuery = "INSERT INTO Customer (ID, Name, Type_of_Customer, Amount_Spent) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(insertQuery);
            psInsert.setInt(1, 1);
            psInsert.setString(2, "Alice");
            psInsert.setString(3, "Regular");
            psInsert.setFloat(4, 2500.75f);
            psInsert.executeUpdate();
            System.out.println("Rows inserted into Customer table.");

            // ii. Display details of all the Customer table
            String selectQuery = "SELECT * FROM Customer";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            System.out.println("\nCustomer Table Details:");
            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " " +
                                   rs.getString("Name") + " " +
                                   rs.getString("Type_of_Customer") + " " +
                                   rs.getFloat("Amount_Spent"));
            }

            // iii. Demonstrate details of the database using DatabaseMetaData
            DatabaseMetaData dbMetaData = conn.getMetaData();
            System.out.println("\nDatabase MetaData:");
            System.out.println("Database Name: " + dbMetaData.getDatabaseProductName());
            System.out.println("Database Version: " + dbMetaData.getDatabaseProductVersion());
            System.out.println("Driver Name: " + dbMetaData.getDriverName());
            System.out.println("Driver Version: " + dbMetaData.getDriverVersion());

            // iv. Demonstrate details of the ResultSet using ResultSetMetaData
            ResultSetMetaData rsMetaData = rs.getMetaData();
            System.out.println("\nResultSet MetaData:");
            int columnCount = rsMetaData.getColumnCount();
            System.out.println("Number of Columns: " + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column " + i + ": " +
                                   rsMetaData.getColumnName(i) + " (" +
                                   rsMetaData.getColumnTypeName(i) + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
