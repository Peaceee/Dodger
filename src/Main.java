import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main extends JPanel implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	public static final int WIDTH = 160;
	public static final int HEIGHT = 120;
	public static final int SCALE = 4;
	
	boolean running;
	
	Game game;
	Thread thread;
	Image dbImage;
	Graphics dbg;
	
	
	public Main() {
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		setSize(size);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		//addKeyListener();
		
		game = new Game();
	}
	
	public synchronized void start() {
		if (running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop() {
		if (!running) return;
		running = false;
	}
	
	public void run() {
		int frames = 0;

		double unprocessedSeconds = 0;
		long lastTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		int tickCount = 0;

		while (running) {
			long now = System.nanoTime();
			long passedTime = now - lastTime;
			lastTime = now;
			if (passedTime < 0) passedTime = 0;
			if (passedTime > 100000000) passedTime = 100000000;

			unprocessedSeconds += passedTime / 1000000000.0;

			boolean ticked = false;
			while (unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;

				tickCount++;
				if (tickCount % 60 == 0) {
					System.out.println(frames + " fps");
					lastTime += 1000;
					frames = 0;
				}
			}

			if (ticked) {
				render();
				paintScreen();
				frames++;
			} else {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public void tick() {
		game.tick();
	}
	
	public void render() {
		if (dbImage == null){
	        dbImage = createImage(SCALE*WIDTH, SCALE*HEIGHT);
	        if (dbImage == null) {
	          System.out.println("dbImage is null");
	          return;
	        }
	        else
	          dbg = dbImage.getGraphics( );
	    }

	    // clear the background
	    dbg.setColor(Color.white);
	    dbg.fillRect (0, 0, SCALE*WIDTH, SCALE*HEIGHT);
	    
	    //dbg.setColor(Color.black);
	    // draw game elements
	    game.draw(dbg);
	}
	
	// use active rendering to put the buffered image on-screen
	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
			g.dispose();
		} catch (Exception e) { 
			System.out.println("Graphics context error: " + e);  
		}
    }

	public static void main(String[] args) {
		Main dodger = new Main();
		
		JFrame frame = new JFrame("Dodger!");

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(dodger, BorderLayout.CENTER);

		frame.setContentPane(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		dodger.start();
	}

}
