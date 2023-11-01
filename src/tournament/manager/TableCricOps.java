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
public class TableCricOps {
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
            String insertDat= "create table pointsCric(Name varchar(40) primary key not null , points int, runs_scored int, overs_played double, runs_against int, overs_faced double);";
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
            
            String insertDat= "insert into pointsCric(Name, points, runs_scored, overs_played, runs_against, overs_faced) values('"+teamName+"', 0, 0, 0.01, 0, 0.01);";
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
    public void updateScoresCric(String Name, int runs_scored, double overs_played, int runs_against, double overs_faced, String databaseName)
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
            String insertDat= "update pointsCric set runs_scored = runs_scored+"+runs_scored+", overs_played = overs_played+"+overs_played+", runs_against = runs_against+"+runs_against+",overs_faced = overs_faced+"+overs_faced+" where Name='"+Name+"';";
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
    
    public void updateWinner(String winner, String databaseName)
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
            String insertDat= "update pointsCric set points= points+2 where Name ='"+winner+"';";
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
    public void updateDrawwer(String team1, String team2, String databaseName)
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
            String insertDat1= "update pointsCric set points= points+1 where Name ='"+team1+"';";
            statement.executeUpdate(insertDat1);
            String insertDat2= "update pointsCric set points= points+1 where Name ='"+team2+"';";
            statement.executeUpdate(insertDat2);
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
}

