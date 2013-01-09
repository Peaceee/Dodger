import java.awt.Color;
import java.awt.Graphics;


public class Enemy {
	
	private int x;
	private int y;
	private int size;
	private int speed;
	
	public Enemy(int x, int y, int size, int speed) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.speed = speed;
	}
	
	public int getY() {
		return y;
	}
	
	public int getHeight() {
		return size;
	}	
	
	public void move() {
		y += speed;
	}
	
	public void tick() {
		move();
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.green);
		g.fillRect(x, y, size, size);
	}
	
}
