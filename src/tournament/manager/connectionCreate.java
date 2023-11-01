/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tournament.manager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Admin
 */
public class connectionCreate {
    
     public boolean loginsq(String username, String password)
     {
         String url = "jdbc:mysql://Garvs-MacBook-Air.local/";
        

        // Database and table names
        

        Connection connection = null;
        Statement statement = null;
        try {
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the MySQL server
            connection = DriverManager.getConnection(url, username, password);

            if (connection.isValid(5)) {
                System.out.println("Connected to MySQL!");
                return true;
                
               // footTourneyForm ftf = new footTourneyForm();
               // ftf.setVisible(true);
                
            } else {
                System.out.println("Failed to connect to MySQL.");
                return false;
            }
            

        
            

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the statement and connection
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    
       return false;  
     }
     public static void main(String[] args){
         // Database connection parameters

}
}
