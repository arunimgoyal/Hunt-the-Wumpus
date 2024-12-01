import javax.swing.*;
import java.awt.Color;
import java.util.ArrayList;

public class Controller {
  // PROPERTIES 
  private Cave   cave;
  private Player player;
  private GUI    gui;
  private Trivia trivia;
  private boolean shootArrowMode;
  private int turnsTaken;

  // CONSTRUCTOR 
  public Controller(Cave cave, Player player){
    this.trivia = new Trivia();
    this.cave = cave;
    this.shootArrowMode = false;
    this.player = player;
    this.gui = new GUI().setController(this);

  }

  // METHODS 
  public boolean locationClick(Location location){
    // All Locations
    location = checkAction(location);
    Location previous = player.getLocation();
    ArrayList<Location> adjacents = cave.getAccessibleAdjacents(location);
    gui.clearWarnings();
    
    // Disable previous buttons
    if(previous != null) {
      ArrayList<Location> prevAdjacents = cave.getAccessibleAdjacents(previous);
      for(Location prevAdj : prevAdjacents) prevAdj.initializeButton(new JButton().getBackground(), false);
      previous.initializeButton(new JButton().getBackground(), false, previous.getCellNum().toString());
    }
    
    // Enable Current Buttons
    location.initializeButton(Color.GREEN, true, "P");
    for(Location adjacent : adjacents){ 
      adjacent.initializeButton(Color.RED, true);
      gui.warnHazard(adjacent.getHazard().getType());
    } System.out.println("Location:" + location);
    player.setLocation(location);

    return true;
  }

  public Location checkAction(Location location){
    String val = location.getValue();
    updateTurns();
    if(val.equals("wumpus")){
      gui.showHazardDialog(val);
      if(/*!controlGold(-5) ||*/!trivia.askMultipleQuestions(5, 3)) endGame(false);
      location.getHazard().wumpusAction(this.cave);
    } if(val.equals("pit")){
      gui.showHazardDialog(val);
      if(/*!controlGold(-3) ||*/!trivia.askMultipleQuestions(3, 2)) endGame(false);
      location = location.getHazard().pitsAction(player.getLocation());
    } if(val.equals("bats")){
      gui.showHazardDialog(val);
      location = location.getHazard().batsAction(this.cave);
    } return location;
  }

  public boolean shoot(Location location){
    if(location.getHazard().getType().equals("wumpus")) endGame(true);
    else{
      shootArrowMode = false;
      player.setArrowCount(player.getArrowCount() - 1);
      gui.updateArrowLabel(player.getArrowCount());
    }
    return true;
  }
  
  public void shootArrow(){
    if(player.getArrowCount() > 0){
      shootArrowMode = true;
      JOptionPane.showMessageDialog(gui, "You are now in shoot arrow mode. Click on an available cell to shoot the arrow into the cell.");
    } else{
      JOptionPane.showMessageDialog(gui, "Not enough arrows.");
    }
  }

  public void getArrow(){
    updateTurns();
    if(controlGold(-3) && trivia.askMultipleQuestions(3, 2))
      player.setArrowCount(player.getArrowCount() + 2);
    gui.updateArrowLabel(player.getArrowCount());
  }

  public void purchaseSecret(){
    updateTurns();
    int secretType = (int)(Math.random() * 6) + 1; // Random num from 1-6
    if(!controlGold(-5) || !trivia.askMultipleQuestions(5, 3)) return;
    
    
    // Secrets - not useful to useful (1-6)
    if(secretType == 1 || secretType == 2)
      JOptionPane.showMessageDialog(gui, "Not useful! You are in room " + player.getLocation().getCellNum());
    else if(secretType == 3)
      JOptionPane.showMessageDialog(gui, "Not useful! The answer to a Trivia Question you answered is " + trivia.getUsedQuestion().getAnswer());
    else if(secretType == 4)
      JOptionPane.showMessageDialog(gui, "Useful! A swarm of bats are in room " + cave.getLocation("bats").getCellNum());
    else if(secretType == 5)
      JOptionPane.showMessageDialog(gui, "Useful! A pit is in room " + cave.getLocation("pit").getCellNum());
    else if(secretType == 6)
      JOptionPane.showMessageDialog(gui, "VERY Useful! A wumpus is in room " + cave.getLocation("wumpus").getCellNum());
  }

  public boolean controlGold(int goldNum){
    if(goldNum < 0 && player.getGold() < Math.abs(goldNum)){
      JOptionPane.showMessageDialog(gui, "You don't have enough gold. Collect gold by wandering through rooms!");
      return false;
    } else{
      player.setGold(player.getGold() + goldNum);
      gui.updateGoldLabel(player.getGold());
    } return true;
  }

  public void updateTurns(){
    this.turnsTaken++;
    gui.updateTurnsLabel(turnsTaken);
  }

  public Player getPlayer(){
    return this.player;
  }

  public boolean getSAMode(){
    return shootArrowMode;
  }

  public JButton[][] getRoomButtons(){
    return cave.getLocations();
  }

  public Trivia getTrivia(){
    return this.trivia;
  }
  
  public GameOver endGame(boolean wump){
    gui.dispose();
    return new GameOver(player, turnsTaken, player.getGold(), player.getArrowCount(), wump);
  }

}

