public class Main {
  
  public static void main(String[] args){
    
    Trivia trivia = new Trivia();
    //trivia.writeQuestion(trivia.addTriviaQuestion()); // For filewriting

    Map mainMap = new Map("map");
    Cave cave = new Cave(mainMap.getLocations());
    new MainMenu(cave); // Start Game

  }

}
