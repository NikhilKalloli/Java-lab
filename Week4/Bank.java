package Week4;

import java.sql.*;

public class Bank {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/BankDB"; // Update with your database name
        String USER = "root";
        String PASS = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            conn.setAutoCommit(false); // Enable manual transaction management

            // i. Insert rows using PreparedStatement
            String insertQuery = "INSERT INTO Bank_Account (Account_No, Account_Name, Type_of_Account, Balance) VALUES (?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(insertQuery);
            psInsert.setInt(1, 101);
            psInsert.setString(2, "Alice");
            psInsert.setString(3, "Savings");
            psInsert.setDouble(4, 10000.50);
            psInsert.executeUpdate();

            System.out.println("Rows inserted into Bank_Account table.");

            // Save a SavePoint after the insert operation
            Savepoint savePoint1 = conn.setSavepoint("InsertSavePoint");

            // ii. Display details of all the Accounts
            String selectQuery = "SELECT * FROM Bank_Account";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(selectQuery);
            System.out.println("\nBank Account Details:");
            while (rs.next()) {
                System.out.println(rs.getInt("Account_No") + " " +
                                   rs.getString("Account_Name") + " " +
                                   rs.getString("Type_of_Account") + " " +
                                   rs.getDouble("Balance"));
            }

            // iii. Demonstrate the Working of Rollback and Commit
            try {
                String updateQuery = "UPDATE Bank_Account SET Balance = Balance + 500 WHERE Account_No = 101";
                stmt.executeUpdate(updateQuery);
                System.out.println("\nBalance updated for Account_No 101.");

                // Intentionally cause an error to demonstrate rollback
                String errorQuery = "INSERT INTO Bank_Account (Account_No, Account_Name) VALUES (103, 'Invalid')";
                stmt.executeUpdate(errorQuery);

                conn.commit(); // If everything is successful, commit the transaction
            } catch (SQLException e) {
                System.out.println("\nError occurred. Rolling back to the previous savepoint.");
                conn.rollback(savePoint1); // Rollback to the savepoint
            }

            // iv. Demonstrate the Working of SavePoints
            Savepoint savePoint2 = conn.setSavepoint("FinalSavePoint");
            try {
                String updateQuery2 = "UPDATE Bank_Account SET Balance = Balance - 1000 WHERE Account_No = 102";
                stmt.executeUpdate(updateQuery2);
                System.out.println("\nBalance updated for Account_No 102.");

                conn.commit(); // Commit after successful operations
            } catch (SQLException e) {
                System.out.println("\nError occurred. Rolling back to the previous savepoint.");
                conn.rollback(savePoint2); // Rollback to the final savepoint
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

