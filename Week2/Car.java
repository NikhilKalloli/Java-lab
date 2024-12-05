package Week2;
import java.sql.*;

public class Car {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/CarDB"; 
        String USER = "root";
        String PASS = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement()) {

            // i. Display details of all the Cars from the table
            String queryAllCars = "SELECT * FROM Car";
            ResultSet rs = stmt.executeQuery(queryAllCars);
            System.out.println("All Cars:");
            System.out.println("Model\tCompany\tPrice\tYear");
            while (rs.next()) {
                System.out.println(rs.getString("Model") + 
                                   rs.getString("Company") + 
                                   rs.getFloat("Price") + 
                                   rs.getInt("Year"));
            }

            // ii. Insert a new row into the table and display all the details
            String insertCar = "INSERT INTO Car (Model, Company, Price, Year) VALUES ('XYZ', 'Tesla', 650000, 2022)";
            stmt.executeUpdate(insertCar);
            System.out.println("\nNew row inserted.");

            rs = stmt.executeQuery(queryAllCars);
            System.out.println("\nAll Cars after insertion:");
            System.out.println("Model\tCompany\tPrice\tYear");
            while (rs.next()) {
                System.out.println(rs.getString("Model") + 
                                   rs.getString("Company") + 
                                   rs.getFloat("Price") + 
                                   rs.getInt("Year"));
            }

            // iii. Delete a row from the table where Model="ABC" and Year=2010
            String deleteCar = "DELETE FROM Car WHERE Model='ABC' AND Year=2010";
            int rowsDeleted = stmt.executeUpdate(deleteCar);
            System.out.println("\nRows deleted: " + rowsDeleted);

            rs = stmt.executeQuery(queryAllCars);
            System.out.println("\nAll Cars after deletion:");
            System.out.println("Model\tCompany\tPrice\tYear");
            while (rs.next()) {
                System.out.println(rs.getString("Model") + 
                                   rs.getString("Company") + 
                                   rs.getFloat("Price") + 
                                   rs.getInt("Year"));
            }

            // iv. Update the price of a row from 1,50,000 to 1,25,000
            String updateCarPrice = "UPDATE Car SET Price=125000 WHERE Price=150000";
            int rowsUpdated = stmt.executeUpdate(updateCarPrice);
            System.out.println("\nRows updated: " + rowsUpdated);

            rs = stmt.executeQuery(queryAllCars);
            System.out.println("\nAll Cars after price update:");
            System.out.println("Model\tCompany\tPrice\tYear");
            while (rs.next()) {
                System.out.println(rs.getString("Model") + "\t" +
                                   rs.getString("Company") + "\t" +
                                   rs.getFloat("Price") + "\t" +
                                   rs.getInt("Year"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
