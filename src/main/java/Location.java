import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.JButton;
import java.util.Arrays;

public class Location extends HexagonButton implements ActionListener {
  // PROPERTIES
  private Controller controller;
  private boolean[] walls;
  private Hazard hazard;
  private Player player;
  private boolean pExist;
  private int xVal;
  private int yVal;
  private String cellValue;
  private int cellNum;

  // CONSTRUCTOR
  public Location(JSONObject jObj){ 
    super(jObj.get("id").toString()); // Number the cells
    this.cellNum = Integer.parseInt(jObj.get("id").toString());
    this.cellValue = jObj.get("hazard").toString();
    this.hazard = new Hazard(cellValue);
    this.hazard.setLoc(this);
    this.pExist = Boolean.parseBoolean(jObj.get("player").toString());
    JSONArray wallList = (JSONArray) jObj.get("walls");
    this.walls = new boolean[wallList.size()];
    
    for(int i = 0; i < walls.length; i++) 
      this.walls[i] = Boolean.parseBoolean(wallList.get(i).toString());

    if(!pExist) setEnabled(false);
    if(pExist) setBackground(Color.GREEN);
    this.addActionListener(this);
    
  }
  
  // METHODS
  public void initializeButton(Color color, boolean enable){
    setBackground(color);
    setEnabled(enable);
    repaint();
  }

  public void initializeButton(Color color, boolean enable, String text){
    setText(text);
    setBackground(color);
    setEnabled(enable);
    repaint();
  }

  //**** GETTERS AND SETTERS ****//
  public void setXPos(int x){
    this.xVal = x;
  }

  public int getXPos(){
    return this.xVal;
  }

  public void setYPos(int y){
    this.yVal = y;
  }

  public int getYPos(){
    return this.yVal;
  }

  public void setHazard(Hazard hazard){
    this.hazard = hazard;
    this.cellValue = hazard.getType();
  }

  public Hazard getHazard(){
    return hazard;
  }

  public void setValue(String value){
    this.cellValue = value;
  }

  public String getValue(){
    return cellValue;
  }

  public Integer getCellNum(){
    return cellNum;
  }

  public boolean playerExists(){
    return pExist;
  }  

  public boolean[] getWalls(){
    return walls;
  }

  public Location setController(Controller controller){  
    this.controller = controller;
    return this;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    // TODO Auto-generated method stub
    if(controller.getSAMode()){
      if(controller != null && controller.shoot(this)){
        controller.updateTurns();
      }
    }
    else if(controller != null && controller.locationClick(this)){
      player = controller.getPlayer();
      controller.controlGold(1);
    }
  }
  
  @Override
  public String toString(){
    return cellNum + " " + cellValue;
  }
}
