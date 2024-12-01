import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Map{
  // PROPERTIES
  private ArrayList<Location> rooms;

  // CONSTRUCTOR
  public Map(String file){
    this.rooms = new ArrayList<Location>();
    String JSON = "";
    JSONParser p = new JSONParser(); 

    try{
      Scanner s = new Scanner(new File(file + ".json"));
      while(s.hasNextLine()){
        JSON += s.nextLine();
      }
      s.close();

      JSONArray jsa = (JSONArray) p.parse(JSON);
      for(Object o:jsa){
        rooms.add(new Location((JSONObject) o));
      }
    }
    catch(Exception e){
      System.out.println("\n" + e);
    }
  }

  // METHODS
  public ArrayList<Location> getRooms(){
    return this.rooms;
  }

  public Location[] getLocations(){
    Location[] locs = new Location[rooms.size()];
    locs = rooms.toArray(locs);
    return locs;
  }

}
