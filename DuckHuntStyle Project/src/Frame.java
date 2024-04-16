import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Frame extends JPanel implements ActionListener, MouseListener, KeyListener {
	
	// timer related variables
	long time = 10; // animation timer
	long timer = 0; // actual wave / level timer
	boolean stopTime = false;
	
	long ghostTimer = 0;
	long ghostTime = 0;
	boolean startGhostTime = false;
	
	//frame width/height
	int width = 900;
	int height = 600;
	int score = 0;
	int shots = 3;
	int level = 1;
	
	
	//Add your object declaration and instantiations here
	Background b = new Background("skyBackground.gif");
	Background stars = new Background("stars.png");
	Background graves = new Background("gravesBackground.png");
	Ghost ghost = new Ghost("ghost.gif");
	Cat cat = new Cat("cat.gif");
	Foreground foreground = new Foreground("foreground.png");
	Score scoreBoard = new Score("score.png");
	Level levelBoard = new Level("level.png");
	Shots shotsBoard = new Shots("shots.png");
	TimeBoard timerBoard = new TimeBoard("timer.png");
	Hit hit = new Hit("hit.png");
	Icon white1 = new Icon("whiteIcon.png");
	Icon white2 = new Icon("whiteIcon.png");
	Icon white3 = new Icon("whiteIcon.png");
	Icon white4 = new Icon("whiteIcon.png");
	Icon white5 = new Icon("whiteIcon.png");
	Icon white6 = new Icon("whiteIcon.png");
	Icon white7 = new Icon("whiteIcon.png");
	Icon white8 = new Icon("whiteIcon.png");
	Icon white9 = new Icon("whiteIcon.png");
	Icon white10 = new Icon("whiteIcon.png");
	Music music = new Music("music.wav", false);
	Music meow = new Music("meow.wav", false);
	Music woosh = new Music("woosh.wav", false);
			
	
	public void paint(Graphics g) {
		super.paintComponent(g);
		
		//Call the paint method of your objects here
		b.paint(g);
		stars.paint(g);
		graves.paint(g);
		scoreBoard.paint(g);
		levelBoard.paint(g);
		cat.paint(g);
		ghost.paint(g);
		foreground.paint(g);
		hit.paint(g);
		shotsBoard.paint(g);
		timerBoard.paint(g);
		
		
		// make ghost bounce
		if (ghost.x >= 780 || ghost.x < 0) {
			ghost.vx *= -1;
		}
		
		// shot animation:
		if (ghost.y >= 450) {
			ghost.vy = 0;	 // when ghost is hidden, it stops moving		
		}
		if (ghostTime > 0 && ghostTime <= 2) {
			cat.vy = -4; // cat goes up
		}
		if (ghostTime > 2 && ghostTime <= 3) {
			cat.vy = 0; // cat stays there	
		}
		if(ghostTime > 3 && ghostTime < 6) {
			cat.vy = 9; // cat goes down 
		}
		// reset
		if (ghostTime == 5) {
			cat.vy = 0; 
			// cat stops moving and hides, ghost resets
			ghost.x = (int)(Math.random() * 400) + 50;
			ghost.y = (int)(Math.random() * 200) + 50;
			if((Math.random()) <= 0.5) {
				ghost.vx = -1 * (int)(Math.random() * 4) - 4 * level;
			}else {
				ghost.vx = (int)(Math.random() * 4) + 4 * level;
			}
			ghostTime = 0;
			time = 10;
			stopTime = false;
			startGhostTime = false;
			ghost.vy = 0;
		}
		
		
		
		timer += 40; // timer related
		if(timer%1000 == 0 && stopTime == false) {
			time --; // displayed time
		}
		
		if(startGhostTime) {
			ghostTimer += 40;
		}
		if(ghostTimer/1000 >= 1) {
			ghostTime ++;
			ghostTimer = 0;
		}
					
		// draw numbers on the screen
		Font f = new Font("sans serif", Font.PLAIN, 38);
		g.setFont(f);
		g.setColor(Color.white);
		g.drawString(" " + score, 745, 50);
		g.drawString(" " + shots, 85, 510);
		g.drawString(" " + level, 540, 50);
		g.drawString("" + time, 310, 510);
		
		// paint little ghost icons to match score
		if (score >= 1) white1.paint(g);
		if (score >= 2) white2.paint(g);
		if (score >= 3) white3.paint(g);
		if (score >= 4) white4.paint(g);
		if (score >= 5) white5.paint(g);
		if (score >= 6) white6.paint(g);
		if (score >= 7) white7.paint(g);
		if (score >= 8) white8.paint(g);
		if (score >= 9) white9.paint(g);
		if (score >= 10) white10.paint(g);
		white2.x = 580;
		white3.x = 610;
		white4.x = 640;
		white5.x = 670;
		white6.x = 700;
		white7.x = 730;
		white8.x = 760;
		white9.x = 790;
		white10.x = 820;
		
		// new level reset
		if(score >= 10 || shots <= 0 || time == 0) {
			level ++;
			shots = 3;
			score = 0;
			ghost.vx *= level;
			woosh.play();
			time = 10;
		}
		
	}
	
	public static void main(String[] arg) {
		Frame f = new Frame();
	}
	
	public Frame() {
		JFrame f = new JFrame("Duck Hunt");
		f.setSize(new Dimension(width, height));
		f.setBackground(Color.blue);
		f.add(this);
		f.setResizable(false);
 		f.addMouseListener(this);
		f.addKeyListener(this);
		music.play();
		
		// the cursor image must be outside of the src folder
		setCursor(Toolkit.getDefaultToolkit().createCustomCursor(
			new ImageIcon("paw.png").getImage(),
			new Point(0,0),"custom cursor"));
	
		
		Timer t = new Timer(16, this);
		t.start();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent m) {
		// TODO Auto-generated method stub
		System.out.println(m.getX() + ":" + m.getY());
		
		meow.play(); // play meow sound
		
		// perform a Rectangle collision for the two objects (mouse and ghost)
		Rectangle a = new Rectangle(m.getX(), m.getY(), 30, 30); // mouse representation
		Rectangle b = new Rectangle(ghost.x, ghost.y, ghost.width, ghost.height); // ghost representation
		
		shots--;
		
		// print the values of the Rectangle to confirm they're all reasonable values!
		System.out.println("mouse: " + a);
		System.out.println("ghost: " + b);
		
		// use the built-in intersect method of a rectangle
		if(a.intersects(b)) {
			System.out.println("collision!");
			
			ghost.vx = 0;
			ghost.vy = 6;
			cat.x = ghost.x - 180;
			cat.y = 400;
			score++;
			shots = 3;
			stopTime = true;
			startGhostTime = true;
		}
		
	
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println(arg0.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
