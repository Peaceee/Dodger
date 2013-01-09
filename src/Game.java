
import java.awt.Graphics;
import java.util.*;


public class Game {
	
	Player player;
	List<Enemy> enemies;
	Random rnd = new Random();
	int timeSinceLastEnemy = 0;
	
	public Game() {
		player = new Player(Main.WIDTH*Main.SCALE/2, Main.HEIGHT*Main.SCALE - 20);
		enemies = new ArrayList<Enemy>();
	}
	
	public void newEnemy() {
		int size = rnd.nextInt(150);
		int speed = rnd.nextInt(20);
		if (timeSinceLastEnemy > 5) {
			enemies.add(new Enemy(rnd.nextInt() % (Main.WIDTH * Main.SCALE), 0, size, speed));
			timeSinceLastEnemy = 0;
		}
	}
	
	public void removeEnemies() {
		for (Iterator<Enemy> i = enemies.iterator(); i.hasNext();) {
			Enemy enemy = i.next();
			if (enemy.getY() - enemy.getHeight() > Main.HEIGHT * Main.SCALE)
				enemies.remove(enemy);
		}
	}
	
	public void draw(Graphics g) {
		player.draw(g);
		for(Enemy enemy : enemies) {
			enemy.draw(g);
		}
	}
	
	public void tick() {
		player.tick();
		newEnemy();
		for (Enemy enemy : enemies) {
			enemy.tick();
		}
		//removeEnemies();
		timeSinceLastEnemy++;
	}

}
