import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class Leaderboard{
  // PROPERTIES
  private static final int LENGTH = 5; // Length of leaderboard (Changing it makes leaderboard bigger or smaller)

  // CONSTRUCTOR
  public Leaderboard(){
    JFrame frame = new JFrame("Leaderboard");
    frame.setSize(300, 500);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setLayout(new BorderLayout()); 

    JPanel table = new JPanel(new GridBagLayout());
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.fill = GridBagConstraints.HORIZONTAL;
    gbc.insets = new java.awt.Insets(10, 10, 10, 10);

    gbc.gridy++;
    JLabel heading = new JLabel("NAME | HIGHSCORE");
    JLabel line = new JLabel("____________________");
    table.add(heading, gbc);
    table.add(line, gbc);

    try{
      File players = new File("players.csv");
      Scanner fileReader = new Scanner(players); // Read through the file for the high scores;
      int count = 0; 
      
      while(fileReader.hasNextLine() && count < LENGTH){
        String[] current = fileReader.nextLine().split(",");
        gbc.gridy++;
        JLabel label = new JLabel("" + current[0] + "|" + current[2]);
        table.add(label, gbc);
        count++;
      }
      
    } catch(IOException e){}

    frame.add(table);


    
    frame.setVisible(true);
  }

  // METHODS  
}
