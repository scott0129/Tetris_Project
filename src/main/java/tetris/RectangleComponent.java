import javax.swing.JComponent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public class RectangleComponent extends JComponent
{
	
	private static final long serialVersionUID = 1L;
	int currentPieceInt;
	MainPiece savedObject;
	MainPiece currentPieceObj;
	
	int nextPiece1;
	int nextPiece2;
	int nextPiece3;
	
	double scale;
	
	boolean saved = false;
	
	long timeWhenDown = System.nanoTime();
	long autoDownCooldown = 500000000;
	
	int score = 0;
	
	int savedOffsetX;
	int savedOffsetY;
	
	int xOffset;
	int yOffset;
	
	List<List<Integer>> permGrid = new ArrayList<List<Integer>>();
	int x;
	int y;
	int width;
	int height;
	
	long downTime;
	
	boolean floor = false;
	int iteration = 0;
	
	int savedPiece = -1;
	
	int pieceID;
	int nextPieceID;
	int lastPiece = 7;
	int lastPiece2 = 7;
	
	
	
	HashMap<Integer, String> pieceLetter = new HashMap<Integer, String>();
	HashMap<Integer, Color> colorHash = new HashMap<Integer, Color>();
	
	
	public long getCooldown()
	{
		return autoDownCooldown;
	}
	
	public RectangleComponent(int x, int y, double scale)
	{
		this.x = x;
		this.y = y;
		this.scale = scale;
		
		savedOffsetX = (int)(800);
		savedOffsetY = (int)(400);
		
		xOffset = (int)(100);
		yOffset = (int)(100);
		

		pieceLetter.put(1, "i");
		pieceLetter.put(2, "l");
		pieceLetter.put(3, "j");
		pieceLetter.put(4, "s");
		pieceLetter.put(5, "z");
		pieceLetter.put(6, "t");
		pieceLetter.put(7, "o");
		
		colorHash.put(1, Color.cyan);
		colorHash.put(2, Color.decode("#F0A000"));
		colorHash.put(3, Color.decode("#0000F0"));
		colorHash.put(4, Color.decode("#00F000"));
		colorHash.put(5, Color.red);
		colorHash.put(6, Color.decode("#A000f0"));
		colorHash.put(7, Color.yellow);
		
		for(int i = 0; i < 20; i++)
		{
			permGrid.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
		}
	}
	
	public void setDifficulty(long i)
	{
		autoDownCooldown = i;
	}
	
	public void clear()
	{
		permGrid.clear();
		savedPiece = -1;
		y = -1;
		
		for(int i = 0; i < 20; i++)
		{
			permGrid.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
		}
	}
	
	public boolean blockFits(MainPiece tetrisPiece, int x, int y)
	{
		for(double[] coord : tetrisPiece.getCoords())
		{
			try
			{
				if(permGrid.get((int)(y + coord[1])).get((int)(x + coord[0])) != 0)
				{
					return false;
				}
			}
			catch(IndexOutOfBoundsException e)
			{
				return false;
			}
			
		}
		return true;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public boolean isRowFull(int row)
	{
		boolean returnBool = true;
		for(int eachBlock : permGrid.get(row))
		{
			if(eachBlock == 0)
				returnBool = false;
		}
		return returnBool;
	}
	
	public void save()
	{
		if(saved == false)
		{
			saved = true;
			int temp = savedPiece;
			if(savedPiece != -1)
			{
				savedPiece = currentPieceObj.getID();
				currentPieceInt = temp;
				currentPieceObj = new MainPiece(pieceLetter.get(currentPieceInt));
			}
			else
			{
				savedPiece = currentPieceObj.getID();
				currentPieceObj = null;
				newPiece();
			}
		
		savedObject = new MainPiece(pieceLetter.get(savedPiece));
		x = 5;
		y = -1;
		}
	}
	
	public void newPiece()
	{
		while(currentPieceObj == null)
		{	
			saved = false; 
			
			int tempPiece = (int)(Math.random() * 7) + 1; 
			
			if(nextPiece3 == tempPiece && currentPieceInt != 0)
			{
				continue;
			}
			
			lastPiece2 = lastPiece;
			lastPiece = currentPieceInt;
			currentPieceInt = nextPiece1;
			
			if(currentPieceInt != 0)
			{
				currentPieceObj = new MainPiece(pieceLetter.get(currentPieceInt));
			}
			
			nextPiece1 = nextPiece2;
			nextPiece2 = nextPiece3;
			nextPiece3 = tempPiece;

			
		}
	}
	
	public void counterClockwise()
	{
		currentPieceObj.rotateCounterClockwise();
		
		for(int i = 0; i < currentPieceObj.getCoords().length; i++)
		{
			if(currentPieceObj.getCoords()[i][0] + x >= 10)
			{
				x -= 10-x;
				break;
			}
			if(currentPieceObj.getCoords()[i][1] + y >= 20)
			{
				y -= 20-y;
				break;
			}
			
			if((int)currentPieceObj.getCoords()[i][0] + x < 0)
			{
				x += 2;
			}
			if((int)currentPieceObj.getCoords()[i][1] + y < 0)
			{
				y += 2;
			}
		}
	}
	
	public void clockwise()
	{
		currentPieceObj.rotateClockwise();
		
		//wall collision detection
		for(int i = 0; i < currentPieceObj.getCoords().length; i++)
		{
			if(currentPieceObj.getCoords()[i][0] + x >= 10)
			{
				x -= 10-x;
				break;
			}
			if(currentPieceObj.getCoords()[i][1] + y >= 20)
			{
				y -= 20-y;
				break;
			}
			
			if((int)currentPieceObj.getCoords()[i][0] + x < 0)
			{
				x += (int)(-currentPieceObj.getCoords()[i][0] + x + 1);
				i = 0;
				//break;
			}
			
			
			if((int)currentPieceObj.getCoords()[i][1] + y < 0)
			{
				y += (int)(-currentPieceObj.getCoords()[i][1] + y + 1);
				i = 0;
				
			}
			//*/
		}
	}
	
	public void addScore(int amt)
	{
		score += amt;
	}
	
	public void snapDown()
	{
		for(int i = 0; i < 20; i++)
		{
			move(0,1);
		}

		iteration = 2;
	}
	
	public void move(int deltaX, int deltaY)
	{
		if(deltaX == 0 && deltaY == 1)
		{
			timeWhenDown = System.nanoTime();
		}
		
		//get new piece, if necessary
		newPiece();

		
		x += deltaX;
		
		//can't go through blocks, can we?
		if(blockFits(currentPieceObj, x, y+1))
		{
		y += deltaY;
		}
		
		//wall collision detection
		for(double[] coord : currentPieceObj.getCoords())
		{
			if(coord[0] + x < 0 || coord[0] + x >= 10 || coord[1] + y >= 20 || permGrid.get((int)(coord[1] + y)).get((int)(coord[0] + x)) != 0)
			{
				System.out.println("Collision?");
				x -= deltaX;
				y -= deltaY;
				break;
			}
		}
		//*/
		
		System.out.println("moved");
		
		//floor collision detection
		for(double[] coord : currentPieceObj.getCoords())
		{
			try
			{
				if(permGrid.get((int)(coord[1] + 1 + y)).get((int)(coord[0] + x)) != 0)
				{
					floor = true;
					System.out.println(iteration);
					iteration++;
					break;
				}
			}
			catch(IndexOutOfBoundsException e)
			{
				floor = true;
				System.out.println(iteration);
				iteration++;
				break;
			}
		}
	}
	
	public void autoDown()
	{
		if(System.nanoTime() - timeWhenDown > autoDownCooldown)
		{
			move(0,1);
		}
	}
	
	public void paintComponent(Graphics g)
	{

		
		List<List<Integer>> grid = new ArrayList<List<Integer>>();
		for(int i = 0; i < 20; i++)
		{
			grid.add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0)));
		}
	
		
		
		newPiece();
		

		//set every "1" if it should be, catch any out of bounds exceptions
		for(double[] coord : currentPieceObj.getCoords())
		{

			try
			{
				grid.get((int)(y + coord[1])).set((int)(x + coord[0]), currentPieceObj.getID() + 1);
			}
			catch (ArrayIndexOutOfBoundsException e)
			{
				//e.printStackTrace();
			}
			catch (IndexOutOfBoundsException e)
			{
				//e.printStackTrace();
			}
		}
		
		//"filled line" deleting
		for(int i = 0; i < 20; i++)
		{
			if(isRowFull(i))
			{
				permGrid.remove(i);
				permGrid.add(0, Arrays.asList(0,0,0,0,0,0,0,0,0,0));
				score += 100;
				autoDownCooldown -= 10000000;
				System.out.println(autoDownCooldown);
			}
		}
	
		
		//draw the grid, give every "1" a color
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(2));
		
		for(int gridY = 0; gridY < grid.size(); gridY++)
		{
			for(int gridX = 0; gridX < grid.get(0).size(); gridX++)
			{
				if(grid.get(gridY).get(gridX) != 0 || permGrid.get(gridY).get(gridX) != 0)
				{
					if(grid.get(gridY).get(gridX) == 1 || permGrid.get(gridY).get(gridX) == 1)
					g2.setColor(Color.yellow);
					if(grid.get(gridY).get(gridX) == 2 || permGrid.get(gridY).get(gridX) == 2)
					g2.setColor(Color.cyan);
					if(grid.get(gridY).get(gridX) == 3 || permGrid.get(gridY).get(gridX) == 3)
					g2.setColor(Color.decode("#F0A000"));
					if(grid.get(gridY).get(gridX) == 4 || permGrid.get(gridY).get(gridX) == 4)
					g2.setColor(Color.decode("#0000F0"));
					if(grid.get(gridY).get(gridX) == 5 || permGrid.get(gridY).get(gridX) == 5)
					g2.setColor(Color.decode("#00F000"));
					if(grid.get(gridY).get(gridX) == 6 || permGrid.get(gridY).get(gridX) == 6)
					g2.setColor(Color.red);
					if(grid.get(gridY).get(gridX) == 7 || permGrid.get(gridY).get(gridX) == 7)
					g2.setColor(Color.decode("#A000F0"));
					
					g2.fillRect((int)((gridX * 50 + xOffset) * scale),  (int)((gridY * 50 + yOffset) * scale),  (int)(50 * scale),  (int)(50 * scale));
				}
				g2.setColor(Color.gray);
				g2.drawRect((int)((gridX * 50 + xOffset) * scale), (int)((gridY * 50 + yOffset) * scale), (int)(50 * scale), (int)(50 * scale));

			}
		}
		
		//draw the savedpiece
		if(savedObject != null)
		{
			for(double[] coords : savedObject.getCoords())
			{
				
				g2.setColor(colorHash.get(savedObject.getID()));
				g2.fillRect((int)((coords[0] * 50 + savedOffsetX + 200) * scale),  (int)((coords[1] * 50 + savedOffsetY - 200) * scale),  (int)(50 * scale),  (int)(50 * scale));
				
				g2.setColor(Color.black);
				g2.drawRect((int)((coords[0] * 50 + savedOffsetX + 200) * scale), (int)((coords[1] * 50 + savedOffsetY - 200) * scale), (int)(50 * scale), (int)(50 * scale));
			}
		}
		
		
		//draw the next pieces
		/**
		 * tempPiece.getCoords returns the coordinates of each piece, relative to its "Center of rotation".
		 */
		if(nextPiece1 != 0)
		{
			MainPiece tempPiece = new MainPiece(pieceLetter.get(nextPiece1));
			
			for(double[] coords : tempPiece.getCoords())
			{
				g2.setColor(colorHash.get(nextPiece1));
				g2.fillRect((int)((coords[0] * 50 + savedOffsetX - 100) * scale),  (int)((coords[1] * 50 + savedOffsetY - 200) * scale),  (int)(50 * scale),  (int)(50 * scale));
				
				g2.setColor(Color.black);
				g2.drawRect((int)((coords[0] * 50  + savedOffsetX - 100) * scale), (int)((coords[1] * 50 + savedOffsetY - 200) * scale), (int)(50 * scale), (int)(50 * scale));
			}
		}
		
		if(nextPiece2 != 0)
		{
			MainPiece tempPiece = new MainPiece(pieceLetter.get(nextPiece2));

			for(double[] coords : tempPiece.getCoords())
			{
				g2.setColor(colorHash.get(nextPiece2));
				g2.fillRect((int)((coords[0] * 50 + savedOffsetX - 100) * scale),  (int)((coords[1] * 50 + savedOffsetY) * scale),  (int)(50 * scale),  (int)(50 * scale));
				
				g2.setColor(Color.black);
				g2.drawRect((int)((coords[0] * 50 + savedOffsetX - 100) * scale), (int)((coords[1] * 50 + savedOffsetY) * scale), (int)(50 * scale),  (int)(50 * scale));
			}
		}
		
		if(nextPiece3 != 0)
		{
			MainPiece tempPiece = new MainPiece(pieceLetter.get(nextPiece3));

			for(double[] coords : tempPiece.getCoords())
			{
				g2.setColor(colorHash.get(nextPiece3));
				g2.fillRect((int)((coords[0] * 50 + savedOffsetX - 100) * scale),  (int)((coords[1] * 50 + savedOffsetY + 200) * scale),  (int)(50 * scale),  (int)(50 * scale));
				
				g2.setColor(Color.black);
				g2.drawRect((int)((coords[0] * 50 + savedOffsetX - 100) * scale), (int)((coords[1] * 50 + savedOffsetY + 200) * scale), (int)(50 * scale),  (int)(50 * scale));
			}
		}
		


		
		//give the player a chance to "shift left" or "shift right" after hitting the floor
		if(floor && iteration > 1)
		{
			for(double[] permBlockCoord : currentPieceObj.getCoords())
			{
				permGrid.get((int)(y + permBlockCoord[1])).set((int)(x + permBlockCoord[0]), currentPieceObj.getID() + 1);
			}
			currentPieceObj = null;
			lastPiece = pieceID;
			x = 5;
			y = 0;
			floor = false;
			iteration = 0;
		}
		
		//draw the ghost
		if(currentPieceObj != null)
		{
			for(int ghostY = y+1; ghostY < 21; ghostY++)
			{
				if(!blockFits(currentPieceObj, x, ghostY))
				{
					for(double[] coords : currentPieceObj.getCoords())
					{
						g2.setColor(Color.orange);		
						g2.setStroke(new BasicStroke((int)(10 * scale)));
						g2.drawRect((int)((((int)(coords[0] + x) * 50) + xOffset) * scale),  (int)((((int)(coords[1] + ghostY-1) * 50) + yOffset) * scale),  (int)(50 * scale),  (int)(50 * scale));
					}
					break;
				}
			}
		}
	}
	
}
