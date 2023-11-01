package tournament.manager;

import java.awt.*;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import tournament.manager.SFinalSystem;
import tournament.manager.TableCricOps;
import tournament.manager.cricTourneyFo;
import tournament.manager.cricTourneyFo;
import tournament.manager.intropage;
import tournament.manager.viewTable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author garv
 */
public class NewJFrame extends javax.swing.JFrame {

    private JTextField organizerNameField;
    private JTextField tournamentNameField;
    private JTextField numberOfTeamsField;
    private JButton submitButton;

    private JButton viewTableButton;
    private DefaultTableModel scheduleTableModel;
    private JTable scheduleTable;
    private JButton goMainButton;
    private List<String> teamNames;
    private JButton SFSys;
    
    private List<String> matchWinners;
    Frame oldFrame=this;
    private String databaseNameSend;
   
    public NewJFrame() {
        setTitle("Tournament Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        JLabel organizerLabel = new JLabel("Organizer's Name:");
        organizerNameField = new JTextField();
        JLabel tournamentLabel = new JLabel("Tournament Name:");
        tournamentNameField = new JTextField();
        JLabel teamsLabel = new JLabel("Number of Teams :");
        numberOfTeamsField = new JTextField();
        submitButton = new JButton("Submit");
        viewTableButton = new JButton("View Table");
        goMainButton = new JButton("Create new Tournament");
        databaseNameSend = tournamentNameField.getText();
        SFSys = new JButton("Semifinal System");

        inputPanel.add(organizerLabel);
        inputPanel.add(organizerNameField);
        inputPanel.add(tournamentLabel);
        inputPanel.add(tournamentNameField);
        inputPanel.add(teamsLabel);
        inputPanel.add(numberOfTeamsField);
        inputPanel.add(submitButton);
        inputPanel.add(viewTableButton);
        inputPanel.add(goMainButton);
        inputPanel.add(SFSys);

        JPanel schedulePanel = new JPanel(new BorderLayout());
        JLabel scheduleLabel = new JLabel("Tournament Schedule:");
        schedulePanel.add(scheduleLabel, BorderLayout.NORTH);

        scheduleTableModel = new DefaultTableModel(new Object[]{"Round", "Match", "Team 1", "Team 2", "Winner", "Scores","Determine"}, 0);
        scheduleTable = new JTable(scheduleTableModel);

        scheduleTable.getColumnModel().getColumn(6).setCellRenderer(new NewJFrame.ButtonRenderer());
        scheduleTable.getColumnModel().getColumn(6).setCellEditor(new NewJFrame.ButtonEditor(new JTextField()));

        JScrollPane tableScrollPane = new JScrollPane(scheduleTable);
        schedulePanel.add(tableScrollPane, BorderLayout.NORTH);

        add(inputPanel, BorderLayout.NORTH);
        add(schedulePanel, BorderLayout.CENTER);


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
                scoresButton.addActionListener(new NewJFrame.ScoresButtonActionListener(round, team1, team2));

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
        vt.viewFrame(oldFrame, tournamentName);
        SwingUtilities.getWindowAncestor(viewTableButton).setVisible(false);
    }
    private void handleSubmission3()
    {
        intropage introf= new intropage();
        introf.setVisible(true);
        dispose();
        
        
    }
    private void handleSubmission4()
    {
        SFinalSystem introf= new SFinalSystem();
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
            JButton scoresButton = (JButton) e.getSource();

            // Display match details
            JOptionPane.showMessageDialog(NewJFrame.this, "Round " + round + " - " + team1 + " vs " + team2);

            //Prompt for scores
            //String score1 = JOptionPane.showInputDialog(TournamentManagementSystem.this, "Enter score for " + team1 + ":");
            //String score2 = JOptionPane.showInputDialog(TournamentManagementSystem.this, "Enter score for " + team2 + ":");

            // Determine the winner
            // String winner;
            // int scoreTeam1 = Integer.parseInt(score1);
            // int scoreTeam2 = Integer.parseInt(score2);
            // if (scoreTeam1 > scoreTeam2) {
            //     winner = team1;
            // } else if (scoreTeam1 < scoreTeam2) {
            //     winner = team2;
            // } else {
            //     winner = "Draw";
            // }

            // Update the winner and scores in the table
            // int row = scheduleTable.getSelectedRow();
            // scheduleTableModel.setValueAt(score1 + " - " + score2, row, 4);
            // scheduleTableModel.setValueAt(winner, row, 3);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new cricTourneyFo().setVisible(true);
            }
        });
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewJFrame().setVisible(true);
            }
        });
    }
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
             JFrame frame = new JFrame("Cricket Match Stats Input");
        
         int selectedRow =scheduleTable.getSelectedRow();
            String team1 = (String) scheduleTableModel.getValueAt(selectedRow, 2);
            String team2 = (String) scheduleTableModel.getValueAt(selectedRow, 3);    
        JPanel parentPanel = new JPanel(new GridLayout(2, 1));
            JPanel teamPanel1 = createTeamPanel(team1);
        JPanel teamPanel2 = createTeamPanel(team2);

        // Add team panels to the parent panel
        parentPanel.add(teamPanel1);
        parentPanel.add(teamPanel2);

        // Create a custom dialog with the parent panel
        int result = JOptionPane.showConfirmDialog(frame, parentPanel, "Enter Cricket Match Stats", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
             int team1Runs = Integer.parseInt(((JTextField) teamPanel1.getComponent(1)).getText());
            int team1Wickets = Integer.parseInt(((JTextField) teamPanel1.getComponent(3)).getText());
            int team1Overs = Integer.parseInt(((JTextField) teamPanel1.getComponent(5)).getText());
            int team1Balls = Integer.parseInt(((JTextField) teamPanel1.getComponent(7)).getText());

            int team2Runs = Integer.parseInt(((JTextField) teamPanel2.getComponent(1)).getText());
            int team2Wickets = Integer.parseInt(((JTextField) teamPanel2.getComponent(3)).getText());
            int team2Overs = Integer.parseInt(((JTextField) teamPanel2.getComponent(5)).getText());
            int team2Balls = Integer.parseInt(((JTextField) teamPanel2.getComponent(7)).getText());
            TableCricOps dt = new TableCricOps();
             String tournamentName = tournamentNameField.getText();
            String winner="";
            boolean submit = false;
            if (team1Runs > team2Runs)
            {
                winner = team1;
                submit = true;
                dt.updateWinner(winner, tournamentName);
            }
            else if (team1Runs < team2Runs)
            {
                winner = team2;
                submit = true;
                dt.updateWinner(winner, tournamentName);
            }
            else if (team1Runs == team2Runs)
            {
                winner = "Draw";
                submit = true;
                dt.updateDrawwer(team1, team2, tournamentName);
            }
            System.out.println(winner);
            
            
            if(team1Balls>=6)
            {
                int oversToAdd;
                oversToAdd=team1Balls/6;
                team1Overs+=oversToAdd;
                team1Balls%=6;
                
            }
            if(team2Balls>=6)
            {
                int oversToAdd;
                oversToAdd=team1Balls/6;
                team1Overs+=oversToAdd;
                team2Balls%=6;
                
            }
            
            double team1Oversd =oversAndBallsToDecimal(team1Overs, team1Balls);
            double team2Oversd =oversAndBallsToDecimal(team2Overs, team2Balls); 
      
            // Determine the winner
          
            if (submit= true)
                {
                    TableCricOps vt = new TableCricOps();
                    
                    vt.updateScoresCric(team1, team1Runs, team1Oversd, team2Runs, team2Oversd, tournamentName);
                    vt.updateScoresCric(team2, team2Runs, team2Oversd, team1Runs, team1Oversd, tournamentName);
                }
            

            // Update the winner and scores in the table
            int row = scheduleTable.getSelectedRow();
            scheduleTableModel.setValueAt(team1Runs+"/"+team1Wickets+"-"+team2Runs+"/"+team2Wickets, row, 5);
            scheduleTableModel.setValueAt(winner, row, 4);

        }
        }
        
    private static JPanel createTeamPanel(String teamName) {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.setBorder(BorderFactory.createTitledBorder(teamName));

        JTextField runsField = new JTextField(10);
        JTextField wicketsField = new JTextField(10);
        JTextField oversField = new JTextField(10);
        JTextField ballsField = new JTextField(10);

        panel.add(new JLabel("Runs:"));
        panel.add(runsField);
        panel.add(new JLabel("Wickets:"));
        panel.add(wicketsField);
        panel.add(new JLabel("Overs:"));
        panel.add(oversField);
        panel.add(new JLabel("Balls:"));
        panel.add(ballsField);

        return panel;
    }
   
    public static double oversAndBallsToDecimal(int overs, int balls) {
    if (overs < 0 || balls < 0) {
        throw new IllegalArgumentException("Overs and balls must be non-negative.");
    }
    
    return overs + (double) balls / 6;
}
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

