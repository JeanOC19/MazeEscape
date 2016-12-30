//Class for generating a random maze using Eller's algorithm. 
//Modified version of the code found on http://rawjava.blogspot.com/2015/05/java-program-to-implement-ellers.html

import java.awt.Color;
import java.util.*;

public class Maze {

	private static Random random = new Random();
	private static int realSize;
	private static int[][] maze;

	public Maze(GameBoard panel, int size) {
		Maze.realSize = size * 2 + 1;
		
		//Initialize 2 dimension cell array and row array
		maze = new int[realSize][realSize];
		Cell[] rowArray = new Cell[size];
		for (int i = 0; i < size; i++) {
			rowArray[i] = new Cell(0, i);
		}
		
		for (int i = 0; i < size; i++) {
			makeSet(rowArray);
			makeRightWalls(rowArray);
			makeLowerWalls(rowArray);
			if (i == size - 1) {
				makeLastRow(rowArray);
				makeWalls(rowArray, i);
			} else {
				makeWalls(rowArray, i);
				genNextRow(rowArray);
			}
		}
		
		//Paint all the walls in the plane, starting by the borders
		for (int i = 0; i < realSize; i++) {
			panel.setColor(i, 0, Color.BLUE);
			panel.setColor(0, i, Color.BLUE);
			panel.setColor(i, 2 * size, Color.BLUE);
			panel.setColor(2 * size, i, Color.BLUE);
		}
		
		//Paint the inner walls of the maze
		for (int i = 1; i < realSize - 1; i++) {
			for (int j = 1; j < realSize - 1; j++) {
				if(i % 2 == 0 && j % 2 == 0) {
					panel.setColor(i, j, Color.BLUE);
				}
				if (maze[i][j] == 1) {
					panel.setColor(j, i, Color.BLUE);
				}
			}
		}
		
		//Paint an entry and an exit into the maze.
		panel.setColor(0, 1, Color.GRAY);
		int exit;
		do {
			exit = (1 + random.nextInt(realSize - 2))/2;
		}while(panel.getColor(realSize - 2, exit) == Color.BLUE);
		panel.setColor(size * 2, exit, Color.GRAY);
	}
	//Generate sets for all the cells in the array
	static void makeSet(Cell[] row) {
		for (int i = 0; i < row.length;i++) {
			if (row[i].set == null) {
				List<Cell> list = new ArrayList<Cell>();
				list.add(row[i]);
				row[i].set = list;
			}
		}
	}
	//Method for creating right walls in a row
	static void makeRightWalls(Cell[] row) {
		for (int i = 1; i < row.length; i++) {
			//If they are part of the same set, make a wall
			if (row[i - 1].set.contains(row[i])) {
				row[i - 1].right = true;
				continue;
			}
			//Choose randomly to add a wall
			if (random.nextBoolean()) {
				row[i - 1].right = true;
			} else {
				merge(row, i);
			}
		}
	}
	//Method to join two sets
	static void merge(Cell[] row, int i) {
		for (Cell j : row[i].set) {
			row[i - 1].set.add(j);
			j.set = row[i - 1].set;
		}
	}
	//Method to make lower walls of a row
	static void makeLowerWalls(Cell[] row) {
		for (int i = 0; i < row.length; i++) {
			//Set all cells to have a lower wall in a set
			for (Cell x : row[i].set) {
				x.down = true;
			}
			//For each set choose at least one cell to not have a wall
			do{
				row[i].set.get(random.nextInt(row[i].set.size())).down = false;
			} while(random.nextBoolean());
		}
	}
	//Method to create the last row of the maze
	static void makeLastRow(Cell[] row) {
		for (int i = 1; i < row.length; i++) {
			//If the two cells are not part of the same set, join them
			if (row[i-1].set.indexOf(row[i]) == -1) {
				row[i - 1].right = false;
				merge(row, i);
			}
		}
	}
	//Method to create the next row in the maze
	static void genNextRow(Cell[] pre) {
		//Copy the row and remove all right walls
		for (int i = 0; i < pre.length; i++) {
			pre[i].right = false;
			pre[i].x++;
			//If cell has a lower wall, empty its set
			if (pre[i].down) {
				pre[i].set.remove(pre[i].set.indexOf(pre[i]));
				pre[i].set = null;
				pre[i].down = true;
			}
		}
	}
	//Method to add walls from cells to maze
	static void makeWalls(Cell[] row, int rowPos) {
		rowPos = 2 * rowPos + 1;
		for (int i = 0; i < row.length; i++) {
			if (row[i].right)
				maze[rowPos][2 * i + 2] = 1;
			if (row[i].down)
				maze[rowPos + 1][2 * i + 1] = 1;
		}
	}
}