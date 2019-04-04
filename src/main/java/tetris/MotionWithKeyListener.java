import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JLabel;

public class MotionWithKeyListener extends KeyAdapter
{
	
	RectangleComponent component;
	JLabel label;
	int deltaX = 1;
	int deltaY = 1;
	long pressedDownTime;
	long nowSystemTime;
	

	
	public MotionWithKeyListener(RectangleComponent component, JLabel label, int X, int Y)
	{
		this.deltaX = X;
		this.deltaY = Y;
		this.label = label;
		component.setOpaque(false);
		this.component = component;
	}
	
	public void repaint() 
	{
		component.repaint();
	}
	
	public void move(int x, int y)
	{
		component.move(x, y);
		label.setText("Score: " + component.getScore() + "    Difficulty: " + (int)((500000000 - component.getCooldown())/ 50000000));
		component.repaint();
	}
	
	public void addScore(int amt)
	{
		component.addScore(amt);
	}
	
	public void moveDown()
	{
		component.move(0, 1);
		component.repaint();
		label.setText("Score: " + component.getScore());
		pressedDownTime = System.nanoTime();
	}
	
	public void autoDown()
	{
		System.out.println(System.nanoTime() - pressedDownTime);
		
		if(System.nanoTime() - pressedDownTime > 500000000)
		{
			component.move(0, 1);
			label.setText("Score: " + component.getScore());
			component.repaint();
		}
		label.setText("Score: " + component.getScore());
		component.repaint();
	}
	
	public void clockwise()
	{
		component.clockwise();
		label.setText("Score: " + component.getScore());
		component.repaint();
	}
	
	public void counterClockwise()
	{
		component.counterClockwise();
		label.setText("Score: " + component.getScore());
		component.repaint();
	}
	
	public void snapDown()
	{
		component.snapDown();
		component.repaint();
	}
	
	public RectangleComponent getComponent()
	{
		return component;
	}
	
	public void save()
	{
		component.save();
		component.repaint();
	}
	
	public void clear()
	{
		component.clear();
		component.repaint();
	}
	
	@Override
	public void keyPressed(KeyEvent e)
	{
		if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_A)
		{
			System.out.println("Left");
			move(-deltaX, 0);
			label.setText("Score: " + component.getScore() + "    Difficulty: " + (int)((500000000 - component.getCooldown())/ 50000000));
			component.repaint();
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_D)
		{
			System.out.println("Right");
			move(deltaX, 0);
			label.setText("Score: " + component.getScore() + "    Difficulty: " + (int)((500000000 - component.getCooldown())/ 50000000));
			component.repaint();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_R)
		{
			clear();
			component.addScore(-component.getScore());
			component.setDifficulty(500000000);
			component.repaint();
		}
		
		/*
		else if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_W)
		{
			System.out.println("Up");
			move(0, -deltaY);
		}
		//*/
		
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			System.out.println("Snap Down");
			snapDown();
			addScore(10);
			label.setText("Score: " + component.getScore() + "    Difficulty: " + (int)((500000000 - component.getCooldown())/ 50000000));
			component.repaint();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_S)
		{
			System.out.println("Down");
			moveDown();
			label.setText("Score: " + component.getScore() + "    Difficulty: " + (int)((500000000 - component.getCooldown())/ 50000000));
			component.repaint();
		}
		
		else if (e.getKeyCode() == KeyEvent.VK_C )
		{
			System.out.println("Save");
			save();
			label.setText("Score: " + component.getScore() + "    Difficulty: " + (int)((500000000 - component.getCooldown())/ 50000000));
			component.repaint();
		}
		
		else if(e.getKeyCode() == 88 || e.getKeyCode() == KeyEvent.VK_E || e.getKeyCode() == KeyEvent.VK_UP)
		{
			System.out.println("Clockwise");
			clockwise();
			label.setText("Score: " + component.getScore() + "    Difficulty: " + (int)((500000000 - component.getCooldown())/ 50000000));
			component.repaint();
		}
		else if(e.getKeyCode() == 90 || e.getKeyCode() == KeyEvent.VK_Q)
		{
			System.out.println("CounterClockwise");
			counterClockwise();
			label.setText("Score: " + component.getScore() + "    Difficulty: " + (int)((500000000 - component.getCooldown())/ 50000000));
			component.repaint();

		}
	}
}
