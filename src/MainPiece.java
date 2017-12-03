
public class MainPiece 
{
	double[][] blocks;
	String letter;
	int ID;
	
	public MainPiece(String type)
	{
		letter = type;
		if(type.equals("i"))
		{
			ID = 1;
			double[][] tempArray = {{0, -1.5}, {0, -0.5}, {0, 0.5}, {0, 1.5}};
			blocks = tempArray;
		}
		else if(type.equals("j"))
		{
			ID = 3;
			double[][] tempArray = {{0, -1}, {0, 0}, {0, 1}, {1, -1}};
			blocks = tempArray;
		}
		else if(type.equals("l"))
		{
			ID = 2;
			double[][] tempArray = {{0, -1}, {0, 0}, {0, 1}, {1, 1}};
			blocks = tempArray;
		}
		else if(type.equals("o"))
		{
			ID = 0;
			double[][] tempArray = {{0.5, -0.5}, {0.5, 0.5}, {-0.5, -0.5}, {-0.5, 0.5}};
			blocks = tempArray;
		}
		else if(type.equals("s"))
		{
			ID = 4;
			double[][] tempArray = {{0, -0.5}, {0, 0.5}, {1, -0.5}, {-1, 0.5}};
			blocks = tempArray;
		}
		else if(type.equals("z"))
		{
			ID = 5;
			double[][] tempArray = {{0, -0.5}, {0, 0.5}, {-1, -0.5}, {1, 0.5}};
			blocks = tempArray;
		}
		else if(type.equals("t"))
		{
			ID = 6;
			double[][] tempArray = {{0, 0}, {0, 1}, {1, 0}, {-1, 0}};
			blocks = tempArray;
		}
	}
	
	public String getLetter()
	{
		return letter;
	}
	
	public int getID()
	{
		return ID;
	}
	
	public void rotateClockwise()
	{
		double[][] newArray = new double[4][2];
		for(int i = 0; i < blocks.length; i++)
		{
			newArray[i][0] = -blocks[i][1];
			newArray[i][1] = blocks[i][0];
		}
		blocks = newArray;
	}
	
	public void rotateCounterClockwise()
	{
		double[][] newArray = new double[4][2];
		for(int i = 0; i < blocks.length; i++)
		{
			newArray[i][0] = blocks[i][1];
			newArray[i][1] = -blocks[i][0];
		}
		blocks = newArray;
	}
	
	public double[][] getCoords()
	{
		return blocks;
	}
}
