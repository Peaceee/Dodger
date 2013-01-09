import java.awt.Color;
import java.awt.Graphics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



public class Player {
	
	private int x;
	private int y;
	private int width = 20;
	private int height = 20;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void move(int moveAmount) {
		x += moveAmount;
	}
	
	public void tick() {
		
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.red);
		g.fillRect(x, y, width, height);
	}

	class TAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keycode = e.getKeyCode();
			
			switch(keycode) {
			case KeyEvent.VK_LEFT:
				move(5);
				break;
				
			case KeyEvent.VK_RIGHT:
				move(-5);
				break;
			}
		}
	}
	
}
