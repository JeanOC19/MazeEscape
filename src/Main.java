import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		
		JFrame myFrame = new JFrame("Maze Escape");
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setLocation(400, 150);
		myFrame.setSize(575, 650);
		
		JOptionPane.showMessageDialog(myFrame, "Welcome to Maze Solver. You have 20 seconds to escape the maze!");

		GameBoard myBoard = new GameBoard();
		myFrame.add(myBoard);
		myKeyListener keyboard = new myKeyListener();
		myFrame.addKeyListener(keyboard);
		myFrame.setVisible(true);
		
		//Infinite loop to repaint the screen and check if user has won
		while(true) {
			myFrame.repaint();
			myBoard.winConditions();
			try {
				Thread.sleep(16);
			} catch (InterruptedException e) {
				System.out.println("Unable to repaint panel.");
			}
		}
	}
}