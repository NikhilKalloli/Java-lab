package Week2;
import java.sql.*;

public class Employee {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost/Office";
        String user = "root";
        String pass = "";

        String createSQL = "CREATE DATABASE IF NOT EXISTS Office";
        String createTable = "CREATE TABLE Employee (ID INT PRIMARY KEY, FName VARCHAR(20), LName VARCHAR(20), Project VARCHAR(20), Salary FLOAT)";
        String insert = "INSERT INTO Employee (ID, FName, LName, Project, Salary) VALUES (1, 'john', 'wick', 'continental', 87.64)";
        


        try(Connection conn = DriverManager.getConnection(url, user, pass);
            Statement smt = conn.createStatement()
        ){
            smt.executeUpdate(createSQL);
            System.out.println("Dstabase created");

            smt.executeUpdate(createTable);
            System.out.println("Table created!");

            smt.executeUpdate(insert);
            System.out.println("Inserted values!");


            String get = "SELECT * FROM Employee";
            ResultSet rs = smt.executeQuery(get);
            while(rs.next()){
                System.out.println(rs.getInt("ID") + rs.getString("FName") + rs.getString("LName") + rs.getString("Project") + rs.getFloat("Salary"));
            }

            String q2 = "SELECT * FROM Employee WHERE Project='Web Development'";
            rs = smt.executeQuery(q2);
            while(rs.next()){
                System.out.println(rs.getInt("ID") + rs.getString("FName") + rs.getString("LName") + rs.getString("Project") + rs.getFloat("Salary"));
            }

            // Display  the  IDs  of  all  those  employee  who  have  salary  above  75,000/-  and  are  in  “Web 
            // Development
            String q3="SELECT ID FROM Employee WHERE Salary > 75000 AND Project='Web Development' ";
            rs = smt.executeQuery(q3);
            while(rs.next()){
                System.out.println(rs.getInt("ID") );
            }

            // Display the total Number of employees who have salary less than 50,000/-.
            String q4 = "SELECT COUNT(*) AS total FROM Employee WHERE salary<50000";
            rs = smt.executeQuery(q4);
            while(rs.next()){
                System.out.println(rs.getInt("total") );
            }


        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
