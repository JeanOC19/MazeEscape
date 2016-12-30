//Class to store properties of a cell when generating maze
import java.util.*;

public class Cell {
	
	public boolean right, down;
	public List<Cell> set;
	public int x, y;
	
	Cell(int a, int b) {
		x = a;
		y = b;
		right = false;	//True if there is a wall to the right
		down = true;	//True if there is a wall below
		set = null;
	}

}