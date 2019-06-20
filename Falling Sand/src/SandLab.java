import java.awt.*;
import java.util.*;

public class SandLab
{
  public static void main(String[] args)
  {
    SandLab lab = new SandLab(120, 80);
    lab.run();
  }
  
  //add constants for particle types here
  public static final int EMPTY = 0;
  public static final int METAL = 1;
  public static final int SAND = 2;
  public static final int WATER = 3;
  
  //do not add any more fields
  private int[][] grid;
  private SandDisplay display;
  
  public SandLab(int numRows, int numCols)
  {
	grid = new int[numRows][numCols];
    String[] names;
    names = new String[4];
    names[EMPTY] = "Empty";
    names[METAL] = "Metal";
    names[SAND] = "Sand";
    names[WATER] = "Water";
    display = new SandDisplay("Falling Sand", numRows, numCols, names);
  }
  
  //called when the user clicks on a location using the given tool
  private void locationClicked(int row, int col, int tool)
  {
	  grid[row][col] = tool;
  }

  //copies each element of grid into the display
  public void updateDisplay()
  {
	  Color metal = new Color(153, 153, 153);
	  Color background = new Color(0, 0, 0);
	  Color sand = new Color(251, 251, 208);
	  Color water = new Color(102, 179, 255);
	  int rows = grid.length;
	  int cols = grid[0].length;
	  for(int i = 0; i < rows; i++) {
		  for(int j = 0; j < cols; j++) {
			  if(grid[i][j] == METAL) {
				  //color should be gray or whatever
				  display.setColor(i, j, metal);
			  }
			  else if(grid[i][j] == EMPTY) {
				  display.setColor(i, j, background);
			  }
			  else if(grid[i][j] == SAND) {
				  display.setColor(i,  j,  sand);
			  }
			  else {
				  display.setColor(i, j, water);
			  }
		  }
	  }
  }

  //called repeatedly.
  //causes one random particle to maybe do something.
  public void step()
  {
	  int rows = grid.length-1;
	  int cols = grid[0].length-1;
	  int direction = (int) (Math.random()*3);
	  direction--;
	  int row = (int) (Math.random()*rows);
	  //System.out.println(row);
	  int col = (int) (Math.random()*cols);
	  //System.out.println(col);
	  if(grid[row][col] == SAND && (grid[row+1][col] == EMPTY || grid[row+1][col] == WATER)) {
		  grid[row+1][col] = SAND;
		  grid[row][col] = EMPTY;
	  }
	  if(grid[row][col] == WATER && grid[row+1][col] == EMPTY) {
		  if(col != 0) {
			  grid[row+1][col+direction] = WATER;
			  grid[row][col] = EMPTY;
		  }
		  else {
			  grid[row+1][col] = WATER;
			  grid[row][col] = EMPTY;
		  }
	  }
  }
  
  //do not modify
  public void run()
  {
    while (true)
    {
      for (int i = 0; i < display.getSpeed(); i++)
        step();
      updateDisplay();
      display.repaint();
      display.pause(1);  //wait for redrawing and for mouse
      int[] mouseLoc = display.getMouseLocation();
      if (mouseLoc != null)  //test if mouse clicked
        locationClicked(mouseLoc[0], mouseLoc[1], display.getTool());
    }
  }
}
