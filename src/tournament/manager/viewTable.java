/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tournament.manager;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class viewTable {
    public void viewFrame(Frame oldFrame, String dbName) {
        // Step 1: Connect to the Database
        Connection conn = null;
        String dbUrl = "jdbc:mysql://localhost:3306/"+dbName;
        String dbUser = "root";
        String dbPassword = "password";

        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Step 2: Execute the SQL Query
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT Name, Points, ROUND(((runs_scored / overs_played) - (runs_against / overs_faced)),2) AS NRR FROM pointscric ORDER BY Points DESC, NRR DESC;";
            ResultSet rs = stmt.executeQuery(sql);

            // Step 3: Create a DefaultTableModel
            DefaultTableModel tableModel = new DefaultTableModel();

            // Step 4: Populate the DefaultTableModel with query results
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                tableModel.addColumn(rs.getMetaData().getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[rs.getMetaData().getColumnCount()];
                for (int i = 0; i < row.length; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                tableModel.addRow(row);
            }

            // Step 5: Create a JTable and set its model
            JTable table = new JTable(tableModel);

            // Step 6: Display the JTable in a JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);

            // Create a Back button
            JButton backButton = new JButton("Back");

            // Add an ActionListener to the Back button
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the "Back" button click action here, e.g., close the frame
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
                    oldFrame.setVisible(true);
                    frame.dispose();
                }
            });

            // Create a JPanel for the buttons and set its layout to BorderLayout
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());

            // Add the Back button to the bottom right of the panel
            buttonPanel.add(backButton, BorderLayout.EAST);

            // Create the main panel to hold the JTable and the button panel
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            JFrame frame = new JFrame("JTable Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(mainPanel);

            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
        public void viewFrameFoot(Frame oldFrame, String dbName) {
        // Step 1: Connect to the Database
        Connection conn = null;
        String dbUrl = "jdbc:mysql://localhost:3306/"+dbName;
        String dbUser = "root";
        String dbPassword = "password";

        try {
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Step 2: Execute the SQL Query
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM points order by points desc, goal_diff desc";
            ResultSet rs = stmt.executeQuery(sql);

            // Step 3: Create a DefaultTableModel
            DefaultTableModel tableModel = new DefaultTableModel();

            // Step 4: Populate the DefaultTableModel with query results
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                tableModel.addColumn(rs.getMetaData().getColumnName(i));
            }

            while (rs.next()) {
                Object[] row = new Object[rs.getMetaData().getColumnCount()];
                for (int i = 0; i < row.length; i++) {
                    row[i] = rs.getObject(i + 1);
                }
                tableModel.addRow(row);
                String sql2 = "SELECT * FROM points ORDER BY points DESC, goal_diff DESC";
                
            }
            String sql2 = "SELECT * FROM points ORDER BY points DESC, goal_diff DESC";
                ResultSet ps = stmt.executeQuery(sql2);
                String winnerName = ""; // Initialize winnerName
                if (ps.next()) {
                    winnerName = ps.getString(1); // Assuming the winner's name is in the first column
                } else {
                    // Handle the case when there are no records (e.g., no winner found)
                }
                

            

            // Step 5: Create a JTable and set its model
            JTable table = new JTable(tableModel);

            // Step 6: Display the JTable in a JScrollPane
            JScrollPane scrollPane = new JScrollPane(table);
            
            JTextField winnerTextField = new JTextField(50);
            winnerTextField.setText("Winner: " + winnerName);// 50 is the preferred width


            // Create a Back button
            JButton backButton = new JButton("Back");

            // Add an ActionListener to the Back button
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Handle the "Back" button click action here, e.g., close the frame
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
                    oldFrame.setVisible(true);
                    frame.dispose();
                }
            });

            // Create a JPanel for the buttons and set its layout to BorderLayout
            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new BorderLayout());

            // Add the Back button to the bottom right of the panel
            buttonPanel.add(backButton, BorderLayout.EAST);

            // Create the main panel to hold the JTable and the button panel
            JPanel mainPanel = new JPanel(new BorderLayout());
            mainPanel.add(scrollPane, BorderLayout.CENTER);
            mainPanel.add(winnerTextField, BorderLayout.SOUTH);
            mainPanel.add(buttonPanel, BorderLayout.SOUTH);

            JFrame frame = new JFrame("JTable Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(mainPanel);

            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}