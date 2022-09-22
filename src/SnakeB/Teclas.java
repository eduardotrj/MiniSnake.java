package SnakeB;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Teclas extends KeyAdapter { //Detect any key pressed.
	
	int direccion = KeyEvent.VK_LEFT; //Default direcction. 
	
	
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { //If press ESC, exit()
			System.exit(0);
			//Allow take different direction less the opposite.
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			if(direccion != KeyEvent.VK_DOWN) {
				direccion = KeyEvent.VK_UP;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			if(direccion != KeyEvent.VK_UP) {
				direccion = KeyEvent.VK_DOWN;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(direccion != KeyEvent.VK_RIGHT) {
				direccion = KeyEvent.VK_LEFT;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(direccion != KeyEvent.VK_LEFT) {
				direccion = KeyEvent.VK_RIGHT;
			}
		}
		
	
	}
}
