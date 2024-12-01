public class Player{
  // PROPERTIES 
  // Out of Game Info
  private String user;
  private String password;
  private int HighScore;
  
  // In-Game Properties
  private Location pLocation;
  public int arrows;
  public int gold;

  // CONSTRUCTORS 
  public Player(){
    this.arrows = 3;
    this.gold = 10;
  }

  public Player(Location location){
    this.pLocation = location;
    this.HighScore = 0;
  }

  // METHODS 
  public void setArrowCount(int arrowCount){
    this.arrows = arrowCount;
  }

  public int getArrowCount(){
    return arrows;
  }

  public void setGold(int gold){
    if(this.gold >= 100) return;
    this.gold = gold;
  }

  public int getGold(){
    return gold;
  }
  
  public void setLocation(Location location){
    this.pLocation = location;
  }

  public Location getLocation(){
    return this.pLocation;
  }

  public void setUser(String user){
    this.user = user;
  }

  public String getUser(){
    return user;
  }

  public void setPass(String pass){
    this.password = pass;
  }

  public String getPass(){
    return password;
  }

  public void setHighScore(int newScore){
    this.HighScore = newScore;
  }

  public int getHighScore(){
    return HighScore;
  }

  @Override
  public String toString(){
    return "Player: " + user + " Password: " + password + " HighScore:" + HighScore;
  }
}
