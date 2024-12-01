import java.util.Arrays;
import java.util.ArrayList;

public class Cave{
  // PROPERTIES
  public static final int CELL  = 6;
  public static final int WIDTH = 6;
  private static int      HEIGHT;
  private Location[][]    rooms;

  // CONSTRUCTOR 
  public Cave(Location[] locs){
    HEIGHT = (int) (locs.length/WIDTH); // Find the HEIGHT of the map given amt of rooms
    this.rooms = new Location[HEIGHT][WIDTH]; // Create a double ArrayList with the height and width 
    for(int i = 0; i < locs.length; i++){
      this.rooms[i/WIDTH][i%WIDTH] = locs[i];
      locs[i].setXPos(i%WIDTH);
      locs[i].setYPos(i/WIDTH);
    }
  }

  // METHODS
  public Location[] getAdjacent(int x, int y){
    Location[] l = new Location[CELL];
    l[0] = rooms[y][next(x, WIDTH)];
    l[1] = rooms[y][prev(x, WIDTH)];
    l[2] = rooms[next(y, HEIGHT)][x];
    l[3] = rooms[prev(y, HEIGHT)][x];
    if(x % 2 == 0){
      l[4] = rooms[prev(y, HEIGHT)][next(x, WIDTH)];
      l[5] = rooms[prev(y, HEIGHT)][prev(x, WIDTH)];
    }  
    else{
      l[4] = rooms[next(y, HEIGHT)][next(x, WIDTH)];
      l[5] = rooms[next(y, HEIGHT)][prev(x, WIDTH)];
    }
    return l;
  }

  public Location[] getAdjacent(Location loc){
    int x = loc.getXPos();
    int y = loc.getYPos();
    return getAdjacent(x, y);
  }

  public ArrayList<Location> getAccessibleAdjacents(Location location){
    ArrayList<Location> accessible = new ArrayList<Location>();
    Location[] adjacents = getAdjacent(location);
    boolean[] locWalls = location.getWalls();
    for(int i = 0; i < adjacents.length; i++){
      if(locWalls[i]) accessible.add(adjacents[i]);
    }
    return accessible;
  }

  public Location getRandomLocation(){
    int rand = (int)(Math.random() * (HEIGHT * WIDTH));
    for(int y = 0; y < rooms.length; y++){
      for(int x = 0; x < rooms[y].length; x++){
        if(rooms[y][x].getCellNum() == rand)
          return rooms[y][x];
      }
    }
    return getRandomLocation(); // This RETURN won't happen unless rand is bigger than the array (not possible unless there is an error on the coder's part)
  }

  public Location getRandomEmptyLocation(){
    Location rand = getRandomLocation();
    while(!rand.getHazard().getType().equals("none")) // Make sure nothing is inside
      rand = getRandomLocation();
    return rand;
  }

  public Location getLocation(String type){
    for(int i = 0; i < HEIGHT*WIDTH; i++){
      if(rooms[i/WIDTH][i%WIDTH].getHazard().getType().equals(type)){
        return rooms[i/WIDTH][i%WIDTH];
      }
    }
    return null;
  }
  

  public Location[][] getLocations(){
    return this.rooms;
  }
  
  private int next(int n, int edge){
    return (n + 1) % edge;
  }

  private int prev(int n, int edge){
    return (n - 1 + edge) % edge;
  }
  
  @Override
  public String toString(){
    return Arrays.deepToString(rooms);
  }
  
}
