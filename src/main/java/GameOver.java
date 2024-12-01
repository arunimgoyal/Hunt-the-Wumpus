import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class GameOver {
    // PROPERTIES 
    private HighScores hs;

    // CONSTRUCTOR 
    public GameOver(Player player, int turns, int gold, int arrows, boolean wumpKilled){
        // Write Scores and get final score
        hs = new HighScores();
        int score = hs.calculateCurrentScore(turns, gold, arrows, wumpKilled);
        try{
            if(player.getHighScore() < score) player.setHighScore(score);
            hs.writeScore(player.getUser(), player.getPass(), player.getHighScore());
        } catch(IOException e){}

        // Create the frame
        JFrame frame = new JFrame("Game Over");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create a panel to hold all labels
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Create a label to display "Game Over"
        JLabel gameOverLabel = new JLabel("Game Over", SwingConstants.CENTER);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 30));
        gameOverLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        panel.add(gameOverLabel);

        // Add some vertical space
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create labels for the parameters
        JLabel turnsLabel = new JLabel("Turns: " + turns, SwingConstants.CENTER);
        turnsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        turnsLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        panel.add(turnsLabel);

        JLabel goldLabel = new JLabel("Gold: " + gold, SwingConstants.CENTER);
        goldLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        goldLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        panel.add(goldLabel);

        JLabel arrowsLabel = new JLabel("Arrows: " + arrows, SwingConstants.CENTER);
        arrowsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        arrowsLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        panel.add(arrowsLabel);

        JLabel wumpKilledLabel = new JLabel("Wumpus Killed: " + (wumpKilled ? "Yes" : "No"), SwingConstants.CENTER);
        wumpKilledLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        wumpKilledLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        panel.add(wumpKilledLabel);

        // Add some vertical space
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Create a label to display player score
        JLabel scoreLabel = new JLabel("Your Score: " + score, SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 18));
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label horizontally
        panel.add(scoreLabel);

        // Add the panel to the center of the frame
        frame.add(panel, BorderLayout.CENTER);

        // Create a panel for the buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        // Create Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });
        buttonPanel.add(exitButton);

        // Add the button panel to the frame
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);

        // Make the frame visible
        frame.setVisible(true);
    }

    // METHODS
}

