package Week3;

import java.sql.*;

public class Movies {
    public static void main(String[] args) {
        String DB_URL = "jdbc:mysql://localhost:3306/MoviesDB"; // Update with your database name
        String USER = "root";
        String PASS = "";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            // i. Display details of all the Movies
            String queryAllMovies = "SELECT * FROM Movies";
            ResultSet rs = stmt.executeQuery(queryAllMovies);
            System.out.println("All Movies:");
            System.out.println("ID Movie_Name Genre IMDB_Rating Year");
            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " " + 
                                   rs.getString("Movie_Name") + " " + 
                                   rs.getString("Genre") + " " + 
                                   rs.getFloat("IMDB_Rating") + " " + 
                                   rs.getInt("Year"));
            }

            // ii. Display details of the 5th Movie
            if (rs.absolute(5)) {
                System.out.println("\n5th Movie:");
                System.out.println(rs.getInt("ID") + " " + 
                                   rs.getString("Movie_Name") + " " + 
                                   rs.getString("Genre") + " " + 
                                   rs.getFloat("IMDB_Rating") + " " + 
                                   rs.getInt("Year"));
            } else {
                System.out.println("\n5th Movie not found (less than 5 movies).");
            }

            // iii. Insert a new row using PreparedStatement and display all details
            String insertQuery = "INSERT INTO Movies (ID, Movie_Name, Genre, IMDB_Rating, Year) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement psInsert = conn.prepareStatement(insertQuery);
            psInsert.setInt(1, 101); // Example ID
            psInsert.setString(2, "Inception");
            psInsert.setString(3, "Sci-fi");
            psInsert.setFloat(4, 8.8f);
            psInsert.setInt(5, 2010);
            psInsert.executeUpdate();
            System.out.println("\nInserted new movie 'Inception'.");

            rs = stmt.executeQuery(queryAllMovies); // Refresh ResultSet
            System.out.println("\nMovies after insertion:");
            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " " + 
                                   rs.getString("Movie_Name") + " " + 
                                   rs.getString("Genre") + " " + 
                                   rs.getFloat("IMDB_Rating") + " " + 
                                   rs.getInt("Year"));
            }

            // iv. Delete rows where IMDB_Rating < 5
            String deleteQuery = "DELETE FROM Movies WHERE IMDB_Rating < 5";
            int rowsDeleted = stmt.executeUpdate(deleteQuery);
            System.out.println("\nDeleted rows with IMDB_Rating < 5: " + rowsDeleted);

            rs = stmt.executeQuery(queryAllMovies); // Refresh ResultSet
            System.out.println("\nMovies after deletion:");
            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " " + 
                                   rs.getString("Movie_Name") + " " + 
                                   rs.getString("Genre") + " " + 
                                   rs.getFloat("IMDB_Rating") + " " + 
                                   rs.getInt("Year"));
            }

            // v. Update Genre of movie with ID = 10 to "Sci-fi"
            rs.beforeFirst(); // Move cursor to the start
            while (rs.next()) {
                if (rs.getInt("ID") == 10) {
                    rs.updateString("Genre", "Sci-fi");
                    rs.updateRow();
                    System.out.println("\nUpdated genre of movie with ID 10 to 'Sci-fi'.");
                }
            }

            rs = stmt.executeQuery(queryAllMovies); // Refresh ResultSet
            System.out.println("\nMovies after updating genre:");
            while (rs.next()) {
                System.out.println(rs.getInt("ID") + " " + 
                                   rs.getString("Movie_Name") + " " + 
                                   rs.getString("Genre") + " " + 
                                   rs.getFloat("IMDB_Rating") + " " + 
                                   rs.getInt("Year"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

