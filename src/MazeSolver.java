//Class for solving a maze using recursion and backtracking
import java.awt.Color;
import java.util.Stack;

public class MazeSolver {

	// Create a stack that will store the coordinates in the correct order
	private static Stack<Coordinates> solPath = new Stack<Coordinates>();
	private static int mazeSize;
	private static GameBoard maze;

	public MazeSolver(GameBoard sol) {
		maze = sol;
		mazeSize = sol.getMazeSize();
		//First two cells will always be the same
		solPath.push(new Coordinates(0, 1));
		solPath.push(new Coordinates(1, 1));

		surroundingPaths();
		paintPath();
	}
	
	//Recursive method to see if the surrounding cells could lead to a possible path
	public static void surroundingPaths() {
		Coordinates currentCell = solPath.peek();
		int currentX = currentCell.getX();
		int currentY = currentCell.getY();
		
		//If the x coordinate is the right edge coordinate, a solution has been found
		if (currentX == mazeSize - 1) {
			return;
		}
		//If the adjacent tiles are not walls and not in the solution stack, add them to the solution stack
		if (maze.getColor(currentX, currentY + 1) != Color.BLUE && !hasCoordinates(currentX, currentY + 1)) {
			solPath.push(new Coordinates(currentX, currentY + 1));
			surroundingPaths();
		} 
		if (maze.getColor(currentX, currentY - 1) != Color.BLUE && !hasCoordinates(currentX, currentY - 1)) {
			solPath.push(new Coordinates(currentX, currentY - 1));
			surroundingPaths();
		}
		if (maze.getColor(currentX + 1, currentY) != Color.BLUE && !hasCoordinates(currentX + 1, currentY)) {
			solPath.push(new Coordinates(currentX + 1, currentY));
			surroundingPaths();
		}  
		if (maze.getColor(currentX - 1, currentY) != Color.BLUE && !hasCoordinates(currentX - 1, currentY)) {
			solPath.push(new Coordinates(currentX - 1, currentY));
			surroundingPaths();
		} 
		//If no cells where added to the stack, delete the last one.
		if(solPath.peek() == currentCell) {
			solPath.pop();
		}
		
	}
	
	//Method to paint the cells of the solution path
	public void paintPath() {
		//While there are still elements in the stack, remove them and paint the tiles
		while(!solPath.isEmpty()) {
			Coordinates currentCell = solPath.pop();
			maze.setColor(currentCell.getX(), currentCell.getY(), Color.RED);
			maze.repaint();
		}
	}
	
	//Method to see if coordinates are in the solution stack
	public static boolean hasCoordinates(int x, int y) {
		for(Coordinates s : solPath) {
			if (x == s.getX() && y == s.getY()) {
				return true;
			}
		}
		return false;
	}
}
