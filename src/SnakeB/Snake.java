package SnakeB;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import java.awt.Point;


/*
 *  Snake game by Eduardo Trujillo
 * 
 * 
 */

public class Snake extends JFrame {
	
	int width = 640;	// Determine the window size.
	int height = 480;
	
	Point snake;
	Point comida;
	
	boolean gameOver = false;
	
	ArrayList<Point> lista= new ArrayList<Point>();
	
	int widthPoint = 10;
	int heightPoint = 10;
	int direccion = KeyEvent.VK_LEFT; // Direction by default.
	
	// ? This can be use to select the difficult
	ImagenSnake imagenSnake;
	long frecuencia = 50; // Speed in ms.  -- frecuencia = ++ speed 
	
	
	public Snake() { // Build the window
		setTitle("Snake Game");
		
		setSize(width+20, height+40); // Prconfigure window size.
		// Add +2- and + 40 to avoid draw outside of the screen.
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // save the screen size.	
		this.setLocation(dim.width/2-width/2, dim.height/2-height/2); // Set at the center
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the window.
		
		Teclas teclas = new Teclas();
		this.addKeyListener(teclas); // Add key listened.
		
		startGame();
		
		imagenSnake = new ImagenSnake(); // Call draw snake class.
		this.getContentPane().add(imagenSnake);
		
		setVisible(true);
		
		Momento momento = new Momento(); // Active movement and position
		Thread trid = new Thread(momento);
		trid.start();
	}
	
	//++++++++++++++ START ++++++++++++
	public void startGame() {
		comida = new Point(200,200);
		snake = new Point(width/2, height/2); // Start in the center.
		
		lista= new ArrayList<Point>();
		lista.add(snake); // Add the snake.
		crearComida();
	}
	
	public void crearComida() { // Create the food position.
		Random rnd = new Random();// Generate a random value,()=seed.
		comida.x = rnd.nextInt(64)*10; // Generate values multiple of 10
		comida.y = rnd.nextInt(48)*10;
		// comida.y = 430;
		// comida.x = 610;
		System.out.println(rnd);
		System.out.println(comida.x + " Eje x   " + comida.y + " eje y");
	}
	
	//______________________________________________________________________________________
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		
		Snake s = new Snake();
	}
	
	public void actualizar() { // Draw again.
		imagenSnake.repaint();
		
		lista.add(0,new Point(snake.x,snake.y));
		lista.remove((lista.size()-1)); // Remove the last element.
		
		for(int i=1;i<lista.size();i++) { // check all positions of the list (snake)
			Point punto = lista.get(i);
			if(snake.x == punto.x && snake.y == punto.y) { // If any match with the head position → Game Over
				gameOver = true;
			}
			
		}
		
		//if((snake.x > (comida.x)) && (snake.x < (comida.x)) && (snake.x > (comida.y)) && (snake.y > (comida.y))) {
		if((snake.x == comida.x) && (snake.y == comida.y)) { // If snake match with food,
			lista.add(0,new Point(snake.x,snake.y)); // Add one snake block for each food.
			crearComida();
		}
	}
	
	public class ImagenSnake extends JPanel { // create class to overwride JPanel
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			g.setColor(new Color(0,255,0)); // Snake color.
			g.fillRect(snake.x, snake.y, widthPoint, heightPoint); // Draw a square.
			for(int i=0;i<lista.size();i++) { // Use the array to draw the snake body
				Point point = (Point)lista.get(i);
				g.fillRect(point.x, point.y, widthPoint, heightPoint); // Draw a rectangle
			}
			
			
			g.setColor(new Color(255,0,0)); // food color
			g.fillRect(comida.x, comida.y, widthPoint, heightPoint); //dibuja rectangle
			
		}
	}
	
	
	
	//class MainScreen extends JFrame {
		
	//if(gameOver) {
		
		//public MainScreen() {
			//LaminaEnd pantallaFinal = new LaminaEnd();

			//add(pantallaFinal);
			//g.drawString("Game Over", 300, 200);
		//}
		
	//}
	////////////////////////////////////////////
	
	
	public class Momento extends Thread {
		long last = 0;
		public void run() { // Keep on
			while(true) {
				if((java.lang.System.currentTimeMillis() - last) > frecuencia) {
					// Check is x time ran.
					if(!gameOver) { // Stop if gameOver
					
						// ? THIS PART CAN BE REDUCE by a new class which take the direction.
						if (direccion == KeyEvent.VK_UP) {
							snake.y = snake.y - heightPoint; // Move to UP in 10px.
							if (snake.y < 0) {
								snake.y = height - heightPoint; // Move UP 10px by default
							}

							if (snake.y > height) { // If position is biggest than screen, reset it.
								snake.y = 0;
							}

						} else if (direccion == KeyEvent.VK_DOWN) {
							snake.y = snake.y + heightPoint; //  Move to DOWN in 10px.
							if (snake.y < 0) {
								snake.y = height - heightPoint; // Move DOWN 10px by default
							}

							if (snake.y > height) { // If position is biggest than screen, reset it.
								snake.y = 0;
							}

						} else if (direccion == KeyEvent.VK_LEFT) {
							snake.x = snake.x - widthPoint; // Move LEFT in 10px 
							if (snake.x < 0) {
								snake.x = width - widthPoint; //Move LEFT 10px by default
							}

							if (snake.x > width) { // If position is biggest than screen, reset it.
								snake.x = 0;
							}

						} else if (direccion == KeyEvent.VK_RIGHT) {
							snake.x = snake.x + widthPoint; // Move BOTTOM in 10px
							if (snake.x < 0) {
								snake.x = width - widthPoint; // Move BOTTOM 10px by default
							}

							if (snake.x > width) { // If position is biggest than screen, reset it.
								snake.x = 0;
							}

						}
					}
					actualizar();
					last = java.lang.System.currentTimeMillis();
					
				}
			}
		}
	}
	
	
	public class Teclas extends KeyAdapter { //Detect keyboards
		
		
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE) { // if SCAPE → exit
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
}
