import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class App 
{   
	public static double scale = .5;
	
	public static void main(String[] args) throws InterruptedException, AWTException
	{
		JFrame frame = new JFrame();
		
		//frame.setLayout(null);
		frame.setSize((int)(1400 * scale), (int)(100 + 1400 * scale));
		frame.setTitle("Blockmover");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel label = new JLabel("Score: 0    Difficulty: 0");
		label.setFont(new Font("Consolas", Font.PLAIN, (int)(50 * scale)));
		label.setVerticalAlignment((int) JFrame.TOP_ALIGNMENT);
		
		RectangleComponent pieces = new RectangleComponent(5, 0, scale);
		
		//Game Over Label
		JLabel GOLabel = new JLabel("Game Over!");
		GOLabel.setFont(new Font("Consolas", Font.PLAIN, (int)(200 * scale)));
		GOLabel.setHorizontalAlignment((int) JFrame.CENTER_ALIGNMENT);
		GOLabel.setVerticalAlignment((int) JFrame.CENTER_ALIGNMENT);
		
		KeyListener keyListener = new MotionWithKeyListener(pieces, label, 1, 1);

		frame.addKeyListener(keyListener);
		frame.add(pieces);
		frame.add(label, BorderLayout.NORTH);

		frame.setVisible(true);
		
		boolean gameLost = false;
		
		while(!gameLost)
		{
			try
			{
			// TimeUnit.NANOSECONDS.sleep(500000000);
			pieces.autoDown();
			pieces.repaint();
			/*
			r.keyPress(KeyEvent.VK_P);
			r.keyRelease(KeyEvent.VK_P);
			//*/
			}
			catch(ArrayIndexOutOfBoundsException e)
			{ //game lost
				break;
			}
		}
		System.out.println("lost!");
		frame.add(GOLabel, BorderLayout.CENTER);
		frame.repaint();
		pieces.repaint();
		
	}
}
