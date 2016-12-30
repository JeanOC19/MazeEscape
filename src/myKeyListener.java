//Class for reading keyboard input
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

public class myKeyListener implements KeyListener  {
	
	@Override
	public void keyPressed(KeyEvent event) {
		Component c = event.getComponent();
		while (!(c instanceof JFrame)) {
			c = c.getParent();
			if (c == null) {
				return;
			}
		}
		JFrame myFrame = (JFrame) c;
		GameBoard myPanel = (GameBoard) myFrame.getContentPane().getComponent(0);
		
		int n = event.getKeyCode();
		
		if( n == KeyEvent.VK_UP || n == KeyEvent.VK_KP_UP) {
			myPanel.changePosition(myPanel.getPlayerX(), myPanel.getPlayerY() - 1);
		} else if ( n == KeyEvent.VK_DOWN || n == KeyEvent.VK_KP_DOWN) {
			myPanel.changePosition(myPanel.getPlayerX(), myPanel.getPlayerY() + 1);
		} else if ( n == KeyEvent.VK_LEFT || n == KeyEvent.VK_KP_LEFT) {
			myPanel.changePosition(myPanel.getPlayerX() - 1, myPanel.getPlayerY());
		} else if (n == KeyEvent.VK_RIGHT || n == KeyEvent.VK_KP_RIGHT) {
			myPanel.changePosition(myPanel.getPlayerX() + 1, myPanel.getPlayerY());
		}	
	}

	//Unused methods that must be implemented
	public void keyReleased(KeyEvent arg0) {
	}
	public void keyTyped(KeyEvent arg0) {		
	}

}
