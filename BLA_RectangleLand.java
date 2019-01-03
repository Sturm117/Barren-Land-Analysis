import java.lang.Math;

public class BLA_RectangleLand {
	public enum RectangleCoordinate {X1COORD, Y1COORD, X2COORD, Y2COORD};
	
	private int x1;
	private int y1;
	private int x2;
	private int y2;
	
	public void setCoordinates(int inX1, int inY1, int inX2, int inY2)
	{
		x1 = inX1;
		y1 = inY1;
		x2 = inX2;
		y2 = inY2;
	}
	
	public int getCoordinate(RectangleCoordinate coordinate)
	{
		int value;
		
		switch (coordinate)
		{
			case X1COORD:
				value = x1;
				break;
			case Y1COORD:
				value = y1;
				break;
			case X2COORD:
				value = x2;
				break;
			case Y2COORD:
				value = y2;
				break;
			default:
				value = -1;
		}
			
		return value;
	}
	
	public int getArea()
	{
		assert (x2 >= x1) : "x2 smaller than x1";
		assert (y2 >= y1) : "y2 smaller than y1";
		
		int area = (x2-x1+1) * (y2-y1+1);
		return area;
	}
	
	public boolean isOutsideRectangle(BLA_RectangleLand compareRect)
	{
		int compareX1 = compareRect.getCoordinate(RectangleCoordinate.X1COORD);
		int compareY1 = compareRect.getCoordinate(RectangleCoordinate.Y1COORD);
		int compareX2 = compareRect.getCoordinate(RectangleCoordinate.X2COORD);
		int compareY2 = compareRect.getCoordinate(RectangleCoordinate.Y2COORD);
		
		boolean x1IsOutside = isOutsideRectangleHelper(compareX1, RectangleCoordinate.X1COORD);
		boolean y1IsOutside = isOutsideRectangleHelper(compareY1, RectangleCoordinate.Y1COORD);
		boolean x2IsOutside = isOutsideRectangleHelper(compareX2, RectangleCoordinate.X2COORD);
		boolean y2IsOutside = isOutsideRectangleHelper(compareY2, RectangleCoordinate.Y2COORD);
		
		boolean isOutside = x1IsOutside || y1IsOutside || x2IsOutside || y2IsOutside;
		
		return isOutside;
	}
	
	private boolean isOutsideRectangleHelper(int coordValue, RectangleCoordinate coordType)
	{
		boolean outside = false;
		int min, max;
		
		if ((coordType == RectangleCoordinate.X1COORD) || (coordType == RectangleCoordinate.X2COORD))
		{
			min = x1;
			max = x2;
		}
		else
		{
			// ASSERT y1 or y2 coords
			min = y1;
			max = y2;
		}
		
		if ((coordValue < min) || (coordValue > max))
		{
			outside = true;
		}
		
		return outside;
	}
	
	public boolean isConnected(BLA_RectangleLand compareRect)
	{
		boolean connected = false;
		int compareRectX1 = compareRect.getCoordinate(RectangleCoordinate.X1COORD);
		int compareRectY1 = compareRect.getCoordinate(RectangleCoordinate.Y1COORD);
		int compareRectX2 = compareRect.getCoordinate(RectangleCoordinate.X2COORD);
		int compareRectY2 = compareRect.getCoordinate(RectangleCoordinate.Y2COORD);
		
		if (((x1 - 1) == compareRectX2) || (x2 == (compareRectX1 - 1)))
		{
			connected = isConnectedHelper(y1, y2, compareRectY1, compareRectY2);
		}
		else if (((y1 - 1) == compareRectY2) || (y2 == (compareRectY1 - 1)))
		{
			connected = isConnectedHelper(x1, x2, compareRectX1, compareRectX2);
		}
		
		return connected;
	}
	
	private boolean isConnectedHelper(int coordA1, int coordA2, int coordB1, int coordB2)
	{
		boolean isConnected = false;
		boolean isCoordBShorter = (Math.abs(coordB2 - coordB1)) < (Math.abs(coordA2 - coordA1));
		
		if (isCoordBShorter)
		{
			if (((coordB1 >= coordA1) && (coordB1 <= coordA2)) || ((coordB2 >= coordA1) && (coordB2 <= coordA2)))
			{
				isConnected = true;
			}
		}
		else
		{
			if (((coordA1 >= coordB1) && (coordA1 <= coordB2)) || ((coordA2 >= coordB1) && (coordA2 <= coordB2)))
			{
				isConnected = true;
			}
		}
		
		return isConnected;
	}
	
	public String toString()
	{
		String str = "{" + x1 + " " + y1 + " " + x2 + " " + y2 + "}";
		
		return str;
	}
}
