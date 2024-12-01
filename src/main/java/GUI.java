import java.awt.*;
import javax.swing.*;

public class GUI extends JFrame {
  // PROPERTIES
  private final int height = 650;
  private final int width = 800;
  private Controller controller;
  private Cave cave;

  // JLabels, JButtons, JPanels
  private JPanel mainPanel;
  private JLabel arrowsLabel;
  private JLabel goldLabel;
  private JLabel turnsLabel;
  private JLabel warningsLabel;

  // CONSTRUCTOR  
  public GUI() {
    this.mainPanel = new JPanel(new BorderLayout());
  }

  // METHODS
  public GUI setController(Controller control) {
      
      this.controller = control;

      JButton[][] btns = controller.getRoomButtons();
      HexagonalLayout hexagonLayout = new HexagonalLayout(btns.length * btns[0].length);
      JPanel mapPanel = new JPanel(hexagonLayout);

      for (JButton[] row : btns) {
          for (JButton b : row)
              if (b != null) {
                  Location l = (Location) b;
                  l.setController(controller);
                  mapPanel.add(b);
              }
      }
    
      mainPanel.add(mapPanel, BorderLayout.CENTER);

      addRightSideComponents(mainPanel);

      this.add(mainPanel);
      this.setSize(new Dimension(width, height));
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //this.setExtendedState(JFrame.MAXIMIZED_BOTH);
      this.setVisible(true);

      return this;
  }

  private void addRightSideComponents(JPanel mainPanel) {
      JPanel rightSidePanel = new JPanel(new GridBagLayout());
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.insets = new java.awt.Insets(10, 10, 10, 10); // Padding around the warnings label

      // Add Warnings label
      JLabel warningsTitleLabel = new JLabel("Warnings: ");
      rightSidePanel.add(warningsTitleLabel, gbc);

      // Adjust padding for other components
      gbc.insets = new java.awt.Insets(10, 10, 10, 10);

      gbc.gridy++; // Move to the next row
      warningsLabel = new JLabel("<html></html>");
      warningsLabel.setVerticalAlignment(JLabel.TOP);
      warningsLabel.setPreferredSize(new Dimension(180, 60)); // Fixed size for the label
      rightSidePanel.add(warningsLabel, gbc);

      // Add labels
      gbc.gridy++;
      goldLabel = new JLabel("Gold: 10");
      rightSidePanel.add(goldLabel, gbc);

      gbc.gridy++;
      arrowsLabel = new JLabel("Arrows: 3");
      rightSidePanel.add(arrowsLabel, gbc);

      gbc.gridy++;
      turnsLabel = new JLabel("Turns Taken: 0");
      rightSidePanel.add(turnsLabel, gbc);

      gbc.gridy++;
      gbc.insets = new java.awt.Insets(20, 10, 10, 10); // Add extra space before buttons

      // Add buttons
      JButton shootArrowButton = new JButton("Shoot Arrow");
      shootArrowButton.addActionListener(e -> controller.shootArrow());
      rightSidePanel.add(shootArrowButton, gbc);

      gbc.gridy++;
      JButton getArrowButton = new JButton("Get Arrow");
      getArrowButton.addActionListener(e -> controller.getArrow());
      rightSidePanel.add(getArrowButton, gbc);

      gbc.gridy++;
      JButton purchaseSecretButton = new JButton("Purchase Secret");
      purchaseSecretButton.addActionListener(e -> controller.purchaseSecret());
      rightSidePanel.add(purchaseSecretButton, gbc);

      rightSidePanel.setPreferredSize(new Dimension(200, height)); // Fixed width for right-side panel

      mainPanel.add(rightSidePanel, BorderLayout.EAST);
  }

  public void updateTurnsLabel(int turns){
    turnsLabel.setText("Turns: " + turns);
  }

  public void updateArrowLabel(int arrows){
    arrowsLabel.setText("Arrows: " + arrows);
  }

  public void updateGoldLabel(int gold){
    goldLabel.setText("Gold: " + gold);
  }

  public void showHazardDialog(String type) {
      if (type.equals("bats"))
          JOptionPane.showMessageDialog(this, "You have encountered bats!");
      else if (type.equals("pit"))
          JOptionPane.showMessageDialog(this, "You have encountered a bottomless pit, Answer 2 out of 3 trivia questions correctly to survive! ");
      else if (type.equals("wumpus"))
          JOptionPane.showMessageDialog(this, "You have encountered the wumpus! Answer 3 out of 5 trivia questions correctly to escape!");
  }

  public void warnHazard(String type){
    if (type.equals("bats"))
      addTextToLabel(warningsLabel, "I hear wings flapping...");
    else if (type.equals("pit"))
      addTextToLabel(warningsLabel, "I feel a draft...");
    else if (type.equals("wumpus"))
      addTextToLabel(warningsLabel, "I smell a wumpus...");
  }

  public void addTextToLabel(JLabel label, String newText) {
      String currentText = label.getText();

      // Remove HTML tags if they already exist
      if (currentText.startsWith("<html>") && currentText.endsWith("</html>")) {
          currentText = currentText.substring(6, currentText.length() - 7);
      }

      // Append new text with a line break
      String updatedText = "<html>" + currentText + "<br>" + newText + "</html>";
      label.setText(updatedText);
  }

  public void clearWarnings(){
    warningsLabel.setText("<html></html>");
  }
}
