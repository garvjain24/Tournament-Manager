/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tournament.manager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class footTourneyFo extends JFrame {
    private JTextField organizerNameField;
    private JTextField tournamentNameField;
    private JTextField numberOfTeamsField;
    private JButton submitButton;

    private JButton viewTableButton;
    private JButton goMainButton;
    private DefaultTableModel scheduleTableModel;
    private JTable scheduleTable;
    private List<String> teamNames;
    private List<String> matchWinners;
    Frame oldFrame=this;

    public footTourneyFo() {
        // Set up the JFrame and layout
        setTitle("Tournament Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel organizerLabel = new JLabel("Organizer's Name:");
        organizerNameField = new JTextField();
        JLabel tournamentLabel = new JLabel("Tournament Name:");
        tournamentNameField = new JTextField();
        JLabel teamsLabel = new JLabel("Number of Teams:");
        numberOfTeamsField = new JTextField();
        submitButton = new JButton("Submit");
        viewTableButton = new JButton("View Table");
        goMainButton = new JButton("Create new Tournament");

        inputPanel.add(organizerLabel);
        inputPanel.add(organizerNameField);
        inputPanel.add(tournamentLabel);
        inputPanel.add(tournamentNameField);
        inputPanel.add(teamsLabel);
        inputPanel.add(numberOfTeamsField);
        inputPanel.add(submitButton);
        inputPanel.add(viewTableButton);
        inputPanel.add(goMainButton);

        // Create schedule panel
        JPanel schedulePanel = new JPanel(new BorderLayout());
        JLabel scheduleLabel = new JLabel("Tournament Schedule:");
        schedulePanel.add(scheduleLabel, BorderLayout.NORTH);

        // Create a table to display the schedule
        scheduleTableModel = new DefaultTableModel(new Object[]{"Round", "Match", "Team 1", "Team 2", "Winner", "Scores","Determine"}, 0);
        scheduleTable = new JTable(scheduleTableModel);

        // Set a button renderer for the "Scores" column
        scheduleTable.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        scheduleTable.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JTextField()));

        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        schedulePanel.add(tableScrollPane, BorderLayout.NORTH);

        // Add panels to the JFrame
        add(inputPanel, BorderLayout.NORTH);
        add(schedulePanel, BorderLayout.CENTER);

        // Add an ActionListener to the "Submit" button
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
        goMainButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                handleSubmission3();
            }
        });
    }

    private void handleSubmission() {
        // Retrieve the input values
        String organizerName = organizerNameField.getText();
        String tournamentName = tournamentNameField.getText();
        int numberOfTeams = Integer.parseInt(numberOfTeamsField.getText());

        TableFootOps tfo = new TableFootOps();
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
                scoresButton.addActionListener(new ScoresButtonActionListener(round, team1, team2));

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
    private void handleSubmission2()
    {
        viewTable vt = new viewTable();
        String tournamentName = tournamentNameField.getText();
        vt.viewFrameFoot(oldFrame, tournamentName);
        SwingUtilities.getWindowAncestor(viewTableButton).setVisible(false);
        
        
    }
    private void handleSubmission3()
    {
        intropage introf= new intropage();
        introf.setVisible(true);
        dispose();
        
        
    }

    private class ScoresButtonActionListener implements ActionListener {
        private int round;
        private String team1;
        private String team2;

        public ScoresButtonActionListener(int round, String team1, String team2) {
            this.round = round;
            this.team1 = team1;
            this.team2 = team2;
        }

        public void actionPerformed(ActionEvent e) {
            //JButton scoresButton = (JButton) e.getSource();

            // Display match details
            JOptionPane.showMessageDialog(footTourneyFo.this, "Round " + round + " - " + team1 + " vs " + team2);

            // Prompt for scores
            /*String score1 = JOptionPane.showInputDialog(TournamentManagementSystem.this, "Enter score for " + team1 + ":");
            String score2 = JOptionPane.showInputDialog(TournamentManagementSystem.this, "Enter score for " + team2 + ":");

            // Determine the winner
            String winner;
            int scoreTeam1 = Integer.parseInt(score1);
            int scoreTeam2 = Integer.parseInt(score2);
            if (scoreTeam1 > scoreTeam2) {
                winner = team1;
            } else if (scoreTeam1 < scoreTeam2) {
                winner = team2;
            } else {
                winner = "Draw";
            }

            // Update the winner and scores in the table
            int row = scheduleTable.getSelectedRow();
            scheduleTableModel.setValueAt(score1 + " - " + score2, row, 4);
            scheduleTableModel.setValueAt(winner, row, 3);*/
        }
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new footTourneyFo().setVisible(true);
            }
        });
    }

    // Button renderer
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(false);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Button editor class
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;

        public ButtonEditor(JTextField textField) {
            super(textField);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {

                handleScoresButtonClicked();
            }
            isPushed = false;
            return label;
        }

        private void handleScoresButtonClicked() {

            int selectedRow =scheduleTable.getSelectedRow();
            String team1 = (String) scheduleTableModel.getValueAt(selectedRow, 2);
            String team2 = (String) scheduleTableModel.getValueAt(selectedRow, 3);

            JOptionPane.showMessageDialog(footTourneyFo.this, "COMMENCE MATCH: "+team1 + " vs " + team2);

            // Prompt for scores
            String score1 = JOptionPane.showInputDialog(footTourneyFo.this, "Number of goals scored by " + team1 + ":");
            String score2 = JOptionPane.showInputDialog(footTourneyFo.this, "Number of goals scored by " + team2 + ":");

            // Determine the winner
            String winner;
            boolean submit = false;
            int scoreTeam1 = Integer.parseInt(score1);
            int scoreTeam2 = Integer.parseInt(score2);
            if (scoreTeam1 > scoreTeam2) {
                winner = team1;
                submit = true;
            } else if (scoreTeam1 < scoreTeam2) {
                winner = team2;
                submit = true;
            } else {
                winner = "Draw";
                submit = true;
            }
            if (submit= true)
                {
                    TableFootOps vt = new TableFootOps();
                    String tournamentName = tournamentNameField.getText();
                    vt.updateScoreFoot(team1, team2, scoreTeam1, scoreTeam2, winner, tournamentName);
                }
            

            // Update the winner and scores in the table
            int row = scheduleTable.getSelectedRow();
            scheduleTableModel.setValueAt(score1 + " - " + score2, row, 5);
            scheduleTableModel.setValueAt(winner, row, 4);

        }
    }
}
