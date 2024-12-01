public class Hazard{
  // PROPERTIES 
  private Location loc;
  private String type;

  // CONSTRUCTOR 
  public Hazard(String type){
    this.type = type;
  }

  // METHODS 
  public void wumpusAction(Cave cave){
    this.loc.setHazard(new Hazard("none")); // Set current Loc to a hazard with no value
    this.loc = cave.getRandomEmptyLocation(); // Set this hazards location = to the rand
    this.loc.setHazard(this); // Set the locations hazard into this
    System.out.println("Wumpus Location: " + loc);

  }

  public Location batsAction(Cave cave){
    //this.loc = cave.getRandomLocation(); // Set a new Loc
    this.loc.setHazard(new Hazard("none")); // Set current Loc to a hazard with no value
    this.loc = cave.getRandomEmptyLocation();; // Set this hazards location = to the rand
    this.loc.setHazard(this); // Set the locations hazard into this
    System.out.println("Bats Location: " + loc);
    return cave.getRandomEmptyLocation(); // Return a new Random Player Location
  }

  public Location pitsAction(Location prev){
    return prev;
  }

  public Location getLoc(){
    return loc;
  }

  public void setLoc(Location loc){
    this.loc = loc;
  }

  public String getType(){
    return type;
  }

  public void setType(String newType){
    this.type = newType;
  }
}
