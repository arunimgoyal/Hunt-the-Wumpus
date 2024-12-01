import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Scanner;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame implements ActionListener {
    // PROPERTIES 
    private JTextField nameField;
    private JPasswordField passwordField;
    private JButton playButton;
    private JButton leaderboardButton;
    private Cave cave;
    private Player player;
    
    // CONSTRUCTOR
    public MainMenu(Cave cave) {
        this.player = new Player();
        this.cave = cave;
        
        setTitle("Main Menu"); // Set the title of the JFrame
        setSize(650, 800); // Set the size of the JFrame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the application when the window is closed
        setLayout(new BorderLayout()); // Use BorderLayout for the JFrame's layout

        // Create and configure the title label
        JLabel titleLabel = new JLabel("Hunt the Wumpus", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 50)); // Set font of the title
        titleLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0)); // Add padding around the title
        add(titleLabel, BorderLayout.NORTH); // Add the title label to the top of the JFrame
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Create a panel for the name input
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridLayout(4, 2));
        //loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        // Create and configure the name label and text field
        JLabel nameLabel = new JLabel("Enter your name: ");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font of the name label

        nameField = new JTextField(20); // Set the text field with a width of 20 characters  
        nameField.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font of the text field


        loginPanel.add(nameLabel); // Add the name label to the name panel
        loginPanel.add(nameField); // Add the text field to the name panel
        //add(namePanel, BorderLayout.CENTER); // Add the name panel to the center of the JFrame

        JLabel passwordLabel = new JLabel("Enter password: ");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 30));

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 30));

        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        add(loginPanel, BorderLayout.CENTER);
        // Create and configure the play button
        playButton = new JButton("Play");
        playButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font of the button text
        playButton.setPreferredSize(new Dimension(200, 100)); // Set preferred size of the button
        playButton.addActionListener(this); // Add an action listener to handle button clicks

        leaderboardButton = new JButton("leaderboard");
        leaderboardButton.setFont(new Font("Arial", Font.PLAIN, 30)); // Set font of the button text
        leaderboardButton.setPreferredSize(new Dimension(300, 100)); // Set preferred size of the button
        leaderboardButton.addActionListener(this); // Add an action listener to handle button clicks

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // Use GridBagLayout to center the button
        buttonPanel.add(playButton); // Add the play button to the panel
        buttonPanel.add(leaderboardButton);
        add(buttonPanel, BorderLayout.SOUTH); // Add the panel to the bottom of the JFrame
        setVisible(true);
    }

    // METHODS
    @Override
    public void actionPerformed(ActionEvent e) { // Method to handle button click events

        if (e.getSource() == playButton) {
            // Code to execute when the play button is clicked
            String name = new String(nameField.getText());
            String pass = new String(passwordField.getPassword());
            if(checkValidP(name, pass)){
                new Controller(cave, player);
                this.dispose();
                System.out.println("Play button clicked!");
            }
            else JOptionPane.showMessageDialog(this, "Not Valid!");
        }

        else if (e.getSource() == leaderboardButton) {
            // Code to execute when the play button is clicked
            new Leaderboard();
            System.out.println("leaderboard button clicked!");
        }
    }
    
    public boolean checkValidP(String user, String password){
        try{
            player.setUser(user);
            player.setPass(password);
            File mainFile = new File("players.csv");
            Scanner fileReader = new Scanner(mainFile);
            while(fileReader.hasNextLine()){
                String[] info = fileReader.nextLine().split(",");
                player.setHighScore(Integer.parseInt(info[2]));
                if(info[0].equals(user)){
                    if(info[1].equals(password)){
                        return true;
                    } else{
                      return false;
                    }
                }
            }
            // Write New Player into File
            FileWriter fw = new FileWriter(mainFile, true);
            fw.write(user + "," + password + "," + 0 + "\n");
            fw.close();
        } catch(FileNotFoundException e){}
          catch(IOException e){}
        return true;
    }
}
