//Class for painting the board
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GameBoard extends JPanel {
	
	private static final long serialVersionUID = -7617570394902017894L;
	private static final int COLUMNS = 15;
	private static final int ROWS = 15;
	private static final int CELL_SIZE = 17;
	private static final int SIZE = 31;
	private static final int DELAY = 10;
	private static final int TOTAL_TIME = 2000;
	private static final Color PLAYER_COLOR = Color.GREEN;
	private static int timeLeft;
	private static Coordinates currentPos;
	private Timer timer;
	private HashMap<Integer, HashMap<Integer,Color>> colorMap = new HashMap<Integer, HashMap<Integer,Color>>(SIZE);

	public GameBoard() {
		 //Initialize all the maps
		for (int x = 0; x < SIZE; x++) {
			colorMap.put(x, new HashMap<Integer, Color>());
		}	
		this.setUp();
	}
	
	//Method for painting cells
	public void paintComponent(Graphics g) {	
		super.paintComponent(g);

		//Calculate dimensions of board
		int width = (2*COLUMNS + CELL_SIZE * SIZE ) - 1;
		int height = (2*COLUMNS + CELL_SIZE * SIZE ) - 51;

		//Paint the background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, width + 1, height + 101);
		
		//Paint the time counter
		g.setColor(Color.GRAY);
		g.fillRect(width/2 - 10, 15, 55, 30);
		g.setColor(Color.RED);
		g.setFont(new Font("default", Font.BOLD, 22));
		g.drawString("" + timeLeft, width/2 - 7, 37);

		//Paint respective cell colors
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				Color c = this.getColor(x, y);
				g.setColor(c);
				g.fillRect(COLUMNS + (x * (CELL_SIZE)), 50 + ROWS + (y * (CELL_SIZE)), CELL_SIZE, CELL_SIZE);
			}
		}
	}	
	//This method generates the maze and paints all the cells
	public void setUp() {
		//Paint all tiles to a background color
		for (int x = 0; x < SIZE; x++) {
			for (int y = 0; y < SIZE; y++) {
				this.setColor(x, y, Color.GRAY);
			}
		}
		//Generate a new maze, place the player in the maze and generate a new timer.
		new Maze(this, (SIZE-1)/2);
		currentPos = new Coordinates(0,1);
		setColor(currentPos.getX(), currentPos.getY(), PLAYER_COLOR);
		timer = new Timer(DELAY, new TimerListener(TOTAL_TIME));
		timer.start();
	}
	//Change a player's position in the maze
	public void changePosition(int x, int y) {
		//If the cell is not a wall and the coordinates are not outside the maze, move to it
		if(x >= 0 && x <= SIZE - 1 && getColor(x, y) == Color.GRAY) {
			setColor(currentPos.getX(), currentPos.getY(), Color.GRAY);
			setColor(x, y, PLAYER_COLOR);
			currentPos.setX(x);
			currentPos.setY(y);
		}
	}
	// Show option to play again if user has won or lost
	public void winConditions() {
		if(timeLeft > 0) {
			if (currentPos.getX() == SIZE - 1) {
				//Show option pane with winner message if player has escaped the maze
				int n = JOptionPane.showConfirmDialog(this, "You Won!, want to play again?", "You're a wizard Harry!",
						JOptionPane.YES_NO_OPTION);
				if (n == JOptionPane.YES_OPTION) {
					timer.stop();
					this.setUp();
				} else {
					System.exit(0);
				}
			} else {
				return;
			}
		} else {
			//Show option pane with losing message if time has run out. Solve the maze
			new MazeSolver(this);
			int n = JOptionPane.showConfirmDialog(this, "I'll let you go again", "Game Over",
					JOptionPane.YES_NO_OPTION);
			if (n == JOptionPane.YES_OPTION) {
				timer.stop();
				this.setUp();
			} else {
				System.exit(0);
			}
		}
	}
	//Returns the color of the cell
	public Color getColor(int x, int y) {
		return colorMap.get(x).get(y);
	}
	//Sets the color of a specific cell
	public void setColor(int x, int y, Color color) {
		colorMap.get(x).put(y, color);
		return;
	}
	//Returns the size of the maze
	public int getMazeSize() {
		return SIZE;
	}
	//Returns the coordinates of the player
	public int getPlayerX() {
		return currentPos.getX();
	}
	public int getPlayerY() {
		return currentPos.getY();
	}
	//Inner class to handle the timer 
	class TimerListener implements ActionListener{
		
		public TimerListener(int n) {
			timeLeft = n;
		}	
		
	    public void actionPerformed(ActionEvent evt){
	          if(timeLeft > 0) {
	        	  timeLeft-= 1;
	          } else {
	        	  return;
	          }
	    }

	}
}