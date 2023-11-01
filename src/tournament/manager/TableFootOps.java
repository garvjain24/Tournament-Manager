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
public class TableFootOps {
    public void createTable(String databaseName)
    {
    
        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "password";

        // Database and table names
     

        Connection connection = null;
        Statement statement = null;

        try {
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the MySQL server
            connection = DriverManager.getConnection(url, user, password);

            // Create a statement
            statement = connection.createStatement();
            try
            {
                
            // Create the database
            String createDatabaseSQL = "CREATE DATABASE " + databaseName;
            statement.executeUpdate(createDatabaseSQL);
            String useDatabaseSQL = "use " + databaseName;
                 statement.executeUpdate(useDatabaseSQL);
            }
            catch(SQLException e)
            {
                 String createDatabaseSQL = "use " + databaseName;
                 statement.executeUpdate(createDatabaseSQL);
            }
            String insertDat= "create table points(Name varchar(40) primary key not null , points int, goals_scored int, goals_against int, goal_diff int);";
            statement.executeUpdate(insertDat);
            System.out.println("Database '" + databaseName + "' created successfully.");

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
    }
    
    public void insertTeams(String teamName, String databaseName)
    {
         // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "password";

        // Database and table names
     

        Connection connection = null;
        Statement statement = null;

        try {
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the MySQL server
            connection = DriverManager.getConnection(url, user, password);

            // Create a statement
            statement = connection.createStatement();
            
                 String createDatabaseSQL = "use " + databaseName;
                 statement.executeUpdate(createDatabaseSQL);
            
            String insertDat= "insert into points(Name, points, goals_scored, goals_against, goal_diff) values('"+teamName+"', 0, 0, 0, 0);";
            statement.executeUpdate(insertDat);
            System.out.println("Database '" + databaseName + "' updated successfully.");

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
    }
    
    public void updateScoreFoot(String team1, String team2, int scoreTeam1,int scoreTeam2, String winner, String databaseName)
    {
         // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/";
        String user = "root";
        String password = "password";

        // Database and table names
     

        Connection connection = null;
        Statement statement = null;

        try {
            // Register the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the MySQL server
            connection = DriverManager.getConnection(url, user, password);

            // Create a statement
            statement = connection.createStatement();
            
                 String createDatabaseSQL = "use " + databaseName;
                 statement.executeUpdate(createDatabaseSQL);
            
            String insertDat1= "update points SET goals_scored=goals_scored+"+scoreTeam1+", goals_against=goals_against+"+scoreTeam2+",goal_diff=goal_diff+"+scoreTeam1+"-"+scoreTeam2+" WHERE Name='"+team1+"'";
            statement.executeUpdate(insertDat1);
            String insertDat2= "update points SET goals_scored=goals_scored+"+scoreTeam2+", goals_against=goals_against+"+scoreTeam1+",goal_diff=goal_diff+"+scoreTeam2+"-"+scoreTeam1+" WHERE Name='"+team2+"'";
            statement.executeUpdate(insertDat2);
            if(winner==team1)
            {
             
            String insertDat3= "update points SET points= points+3 WHERE Name='"+team1+"'";
            statement.executeUpdate(insertDat3);
            }
            else if(winner==team2)
            {
                String insertDat3= "update points SET points= points+3 WHERE Name='"+team2+"'";
            statement.executeUpdate(insertDat3);
            }
            else if(winner=="Draw")
            {
                String insertDat3= "update points SET points= points+1 WHERE Name='"+team2+"'";
            statement.executeUpdate(insertDat3);
                String insertDat4= "update points SET points= points+1 WHERE Name='"+team1+"'";
            statement.executeUpdate(insertDat4);
            
            }
            
            System.out.println("Database '" + databaseName + "' updated successfully.");

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
    }
    
}

