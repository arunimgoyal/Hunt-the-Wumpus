import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

public class Trivia {
  // PROPERTIES 
  private ArrayList<Question> questions;
  private ArrayList<Question> usedQuestions; // For Purchase Secret

  // CONSTRUCTOR
  public Trivia() {
    this.questions = new ArrayList<Question>();
    this.usedQuestions = new ArrayList<Question>();
    initializeQuestions();
  }

  // METHODS 
  public void initializeQuestions(){
    try {
      Scanner reader = new Scanner(new File("questions.csv"));
      while (reader.hasNextLine()) {
        String[] arr = reader.nextLine().split(", ");
        questions.add(new Question(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]));
      }
      reader.close();
    } catch (FileNotFoundException e){}
  }

  public Question addTriviaQuestion(){
    Scanner s = new Scanner(System.in);
    System.out.println("Question?");
    String question = s.nextLine();
    System.out.println("Option A?");
    String optionA = s.nextLine();
    System.out.println("Option B?");
    String optionB = s.nextLine();
    System.out.println("Option C?");
    String optionC = s.nextLine();
    System.out.println("Option D?");
    String optionD = s.nextLine();
    System.out.println("Answer?");
    String answer = s.nextLine();
    
    return new Question(question, optionA, optionB, optionC, optionD, answer);
    
  }

  public void writeQuestion(Question question){
    try {
      // Create tempFile to put all the info + new questions into
      File mainFile = new File("questions.csv");
      File tempFile = new File("questions(copy).csv");
      Scanner reader = new Scanner(mainFile);
      FileWriter fw = new FileWriter(tempFile);

      while(reader.hasNext()){
        fw.write(reader.nextLine() + "\n"); // Write all the old questions
      } fw.write(question.getFileQuestion() + "\n"); //Write the new Question
      tempFile.renameTo(mainFile); // Make the tempFile the mainFile

      fw.close();
      reader.close();
    } catch (FileNotFoundException e){}
      catch (IOException e){}

    questions.add(question);
  }

  public boolean askMultipleQuestions(int qNum, int numCorrect){ //Amount of questions you need to ask
    int right = 0; //Amount of questions got right
    for(int i = 0; i < qNum; i++)
      if(getTriviaQuestion().displayQuestion()) right++ ;
    return (right >= numCorrect);
  }

  public Question getTriviaQuestion(){
    int rndQ = (int) (Math.random() * questions.size());
    usedQuestions.add(questions.get(rndQ));
    return questions.remove(rndQ);
  }

  public Question getUsedQuestion(){
    int rndQ = (int) (Math.random() * questions.size());
    return usedQuestions.get(rndQ);
  }

  public ArrayList<Question> getQuestions(){
    return this.questions;
  }
  
}
