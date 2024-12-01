import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.*;


public class HighScores {
  // PROPERTIES 
  private ArrayList<String> scores;
  private String curName;
  private String curPass;

  // CONSTRUCTOR
  public HighScores() {
    scores = new ArrayList<String>();
    try{
      File players = new File("players.csv");
      Scanner fileReader = new Scanner(players);
      while(fileReader.hasNextLine())
        scores.add(fileReader.nextLine());
    } catch(FileNotFoundException e){}
    
  }

  // METHODS
  public int calculateCurrentScore(int turns, int gold, int arrows, boolean wumpKilled){
    int wumpPoints = 0;
    if(wumpKilled) wumpPoints = 50;
    return 100 - turns + gold + 5 * arrows + wumpPoints;
  }

  public void writeScore(String username, String password, int highscore) throws IOException {
    try{
      File players = new File("players.csv");
      FileWriter fw = new FileWriter(players);
      for(int i = 0; i < scores.size(); i++){
        String[] pInfo = scores.get(i).split(",");
        if(pInfo[0].equals(username) && pInfo[1].equals(password)){
          if(highscore > Integer.parseInt(pInfo[2]))
            scores.set(i, username + "," + password + "," + highscore);
        }
      }
      
      sortScores();
      for(String info : scores) fw.write(info + "\n");
      fw.close();
  
      
    } catch(IOException e){}
    
  }

  public void sortScores(){
    Collections.sort(scores, 
    new Comparator<String>(){
      @Override
      public int compare(String s1, String s2){
        int hs1 = Integer.parseInt(s1.split(",")[2]);
        int hs2 = Integer.parseInt(s2.split(",")[2]);

        return Integer.compare(hs2, hs1);
      }
    });
  }
}
