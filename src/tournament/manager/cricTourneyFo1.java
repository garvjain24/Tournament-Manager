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
import java.util.ArrayList;
import java.util.List;


public class cricTourneyFo1 extends JFrame {
    private JTextField organizerNameField;
    private JTextField tournamentNameField;
    private JTextField numberOfTeamsField;
    private JButton submitButton;
    private JButton viewTableButton;
    private DefaultTableModel scheduleTableModel;
    private List<JTextField> teamNameFields;
    Frame oldFrame=this;

    public cricTourneyFo1() {
        // Set up the JFrame and layout
        setTitle("Tournament Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);
        

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel organizerLabel = new JLabel("Organizer's Name:");
        organizerNameField = new JTextField();
        JLabel tournamentLabel = new JLabel("Tournament Name:");
        tournamentNameField = new JTextField();
        JLabel teamsLabel = new JLabel("Number of Teams (Multiples of 2):");
        numberOfTeamsField = new JTextField();
        submitButton = new JButton("Submit");
        viewTableButton = new JButton("View Table");

        inputPanel.add(organizerLabel);
        inputPanel.add(organizerNameField);
        inputPanel.add(tournamentLabel);
        inputPanel.add(tournamentNameField);
        inputPanel.add(teamsLabel);
        inputPanel.add(numberOfTeamsField);
        inputPanel.add(submitButton);
        inputPanel.add(viewTableButton);

        // Create schedule panel
        JPanel schedulePanel = new JPanel(new BorderLayout());
        JLabel scheduleLabel = new JLabel("Tournament Schedule:");
        schedulePanel.add(scheduleLabel, BorderLayout.NORTH);

        // Create a table to display the schedule
        scheduleTableModel = new DefaultTableModel(new Object[]{"Round", "team 1 Vs team 2", "result"}, 0);
        JTable scheduleTable = new JTable(scheduleTableModel);
        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        schedulePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Add panels to the JFrame
        add(inputPanel, BorderLayout.NORTH);
        add(schedulePanel, BorderLayout.CENTER);

        // Add an ActionListener to the button
        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmission();
            }
        });
        viewTableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmission2();
            }
        });
        });

        viewTableButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmission2();
            }
        });
        goMainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmission3();
            }
        });
        SFSys.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmission4();
            }
        });
    }

    private void handleSubmission() {
        // Retrieve the input values
        String organizerName = organizerNameField.getText();
        String tournamentName = tournamentNameField.getText();
        int numberOfTeams = Integer.parseInt(numberOfTeamsField.getText());

        TableCricOps tfo = new TableCricOps();
        tfo.createTable(tournamentName);



        // Clear the previous schedule
        scheduleTableModel.setRowCount(0);

        // Create team name input dialog
        teamNames = new ArrayList<>();
        for (int i = 1; i <= numberOfTeams; i++) {
            String teamName = JOptionPane.showInputDialog(this, "Enter Team " + i + " Name:");
            if (teamName != null) {
                teamNames.add(teamName);
                tfo.insertTeams(teamName, tournamentName);  
            } else {
                return; // User canceled input
            }
        }
        if (numberOfTeams % 2 == 1){
            teamNames.add("Bye");
            numberOfTeams=numberOfTeams+1;
        }

        // Generate the round-robin schedule
        for (int round = 1; round < numberOfTeams; round++) {
            for (int i = 0; i < numberOfTeams / 2; i++) {
                int team1Index = i;
                int team2Index = numberOfTeams - 1 - i;

                String team1 = teamNames.get(team1Index);
                String team2 = teamNames.get(team2Index);

                // Create a Scores button for each match
                JButton scoresButton = new JButton("Scores");
                scoresButton.addActionListener(new cricTourneyFo.ScoresButtonActionListener(round, team1, team2));

                // Set preferred size for the button
                //scoresButton.setPreferredSize(new Dimension(80, 25));

                // Add the match to the schedule table along with the Scores button
                scheduleTableModel.addRow(new Object[]{round, team1 + " vs. " + team2, team1, team2, "", "","Scores"});
            }

            // Rotate teams for the next round
            String lastTeam = teamNames.remove(teamNames.size() - 1);
            teamNames.add(1, lastTeam);
        }
        
    }

    private void handleSubmission() {
        // Retrieve the input values
        String organizerName = organizerNameField.getText();
        String tournamentName = tournamentNameField.getText();
        int numberOfTeams = Integer.parseInt(numberOfTeamsField.getText());

        if (!isPowerOfTwo(numberOfTeams)) {
            JOptionPane.showMessageDialog(this, "Number of teams must be a power of 2.");
           return;
        }
        
        TableCricOps tfo = new TableCricOps();
        tfo.createTable(tournamentName);
                

        // Clear the previous schedule
        scheduleTableModel.setRowCount(0);

        // Create team name input dialog
        teamNameFields = new ArrayList<>();
        for (int i = 1; i <= numberOfTeams; i++) {
            String teamName = JOptionPane.showInputDialog(this, "Enter Team " + i + " Name:");
            if (teamName != null) {
                teamNameFields.add(new JTextField(teamName));
                tfo.insertTeams(teamName, tournamentName);
            } else {
                return; // User canceled input
            }
        }

        // Generate the round-robin schedule
        List<String> teams = new ArrayList<>();
        for (JTextField textField : teamNameFields) {
            teams.add(textField.getText());
        }

        for (int round = 1; round < numberOfTeams; round++) {
            for (int i = 0; i < numberOfTeams / 2; i++) {
                int team1Index = i;
                int team2Index = numberOfTeams - 1 - i;

                String team1 = teams.get(team1Index);
                String team2 = teams.get(team2Index);

                // Add the match to the schedule table
                scheduleTableModel.addRow(new Object[]{round, team1 + " vs. " + team2, });
            }

            // Rotate teams for the next round
            String lastTeam = teams.remove(teams.size() - 1);
            teams.add(1, lastTeam);
        }
    }

    private boolean isPowerOfTwo(int number) {
       return (number & (number - 1)) == 0;
   }
    
    private void handleSubmission2()
    {
        viewTable vt = new viewTable();
        String tournamentName = tournamentNameField.getText();
        vt.viewFrame(oldFrame, tournamentName);
        SwingUtilities.getWindowAncestor(viewTableButton).setVisible(false);
        
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new cricTourneyFo1().setVisible(true);
            }
        });
    }
};