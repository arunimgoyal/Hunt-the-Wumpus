import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;
import java.awt.*;
import javax.swing.*;

public class Question extends JPanel implements ActionListener{
  // PROPERTIES 
  private String question;
  private String answer;
  private String oA;
  private String oB;
  private String oC;
  private String oD;

  // CONSTRUCTOR
  public Question(String question, String optionA, String optionB, String optionC, String optionD, String answer){
    this.question = question;
    this.oA = optionA;
    this.oB = optionB;
    this.oC = optionC;
    this.oD = optionD;
    this.answer = answer;
  }

  // METHODS
  public String getFileQuestion(){
    return question + "," + oA + "," + oB + "," + oC + "," + oD + "," + answer;
  }

  public String printQuestion(){
    return question + " (" + oA + ", " + oB + ", " + oC + ", " + oD + ")";
  }

  public boolean displayQuestion(){
    String userAnswer = JOptionPane.showInputDialog(this, printQuestion(), "Trivia Question", JOptionPane.PLAIN_MESSAGE); // Prompt the user for an answer
    return (userAnswer != null && userAnswer.equalsIgnoreCase(answer));
  }

  public String getAnswer(){
    return answer;
  }

  @Override
  public void actionPerformed(ActionEvent e) {

  
  } 
  
  @Override
  public String toString(){
    return this.question;
  }
}
