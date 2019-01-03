import java.util.*;

public class BLA_Farmland {
	public enum InitializerOptions{INCREMENT, DECREMENT, UNIFORM};
	
	private static final BLA_RectangleLand.RectangleCoordinate X1RECT = BLA_RectangleLand.RectangleCoordinate.X1COORD;
	private static final BLA_RectangleLand.RectangleCoordinate Y1RECT = BLA_RectangleLand.RectangleCoordinate.Y1COORD;
	private static final BLA_RectangleLand.RectangleCoordinate X2RECT = BLA_RectangleLand.RectangleCoordinate.X2COORD;
	private static final BLA_RectangleLand.RectangleCoordinate Y2RECT = BLA_RectangleLand.RectangleCoordinate.Y2COORD;
	
	private BLA_RectangleLand farmlandDim;
	private Vector<BLA_RectangleLand> barrenLand;
	private Vector<BLA_RectangleLand> fertileLand;
	
	public BLA_Farmland(int x, int y)
	{
		barrenLand = new Vector<BLA_RectangleLand>(4,4);
		fertileLand = new Vector<BLA_RectangleLand>();
		farmlandDim = new BLA_RectangleLand();
		farmlandDim.setCoordinates(0, 0, x, y);
	}
	
	public void printConnectedFertileArea()
	{
		ArrayList<Integer> areaArr = new ArrayList<Integer>();
		collectConnectedFertileArea(areaArr);
		Collections.sort(areaArr);
		
		if (areaArr.size() > 0)
		{
			System.out.print("Connected fertile land area: ");
			for (int area : areaArr)
			{
				System.out.print(area + " ");
			}
			System.out.println("");
		}
		else
		{
			System.out.println("No fertile land.");
		}
		
	}

	private void collectConnectedFertileArea(ArrayList<Integer> areaArr)
	{
		int groupNum = 1;
		ArrayList<Integer> groupList = new ArrayList<Integer>(fertileLand.size());
		ArrayList<Integer> landList = new ArrayList<Integer>(fertileLand.size());
		
		initializeArrayList(InitializerOptions.INCREMENT, 0, landList, fertileLand.size());
		initializeArrayList(InitializerOptions.UNIFORM, 0, groupList, fertileLand.size());
		
		for (int index = 0; index < groupList.size(); index++)
		{
			if (groupList.get(index) == 0)
			{
				getConnectedLand(index, groupNum, landList, groupList, fertileLand);
				groupNum += 1;
			}
		}
		
		for (int printGroup = 1; printGroup< groupNum; printGroup++)
		{
			int sum = 0;
			for (int landInt = 0; landInt < groupList.size(); landInt++)
			{
				if (groupList.get(landInt) == printGroup)
				{
					BLA_RectangleLand land = fertileLand.elementAt(landInt);
					sum += land.getArea();
				}
			}
			areaArr.add(sum);
		}
	}
	
	private void getConnectedLand(int curElement, int group, ArrayList<Integer> landList, 
			ArrayList<Integer> groupList, Vector<BLA_RectangleLand> masterLandList)
	{
		if (groupList.get(curElement) == 0)
		{
			groupList.set(curElement, group);
			landList.remove(Integer.valueOf(curElement));
		}
		
		ArrayList<Integer> localConnected = new ArrayList<Integer>();
		BLA_RectangleLand currentLand = masterLandList.elementAt(curElement);
		
		for (int i = 0; i < landList.size(); i++)
		{
			int masterIndex = landList.get(i);
			BLA_RectangleLand compareLand = masterLandList.elementAt(masterIndex);
			if (currentLand.isConnected(compareLand))
			{
				int indexof = landList.indexOf(masterIndex);
				localConnected.add(landList.get(indexof));
			}
		}
		
		for (int landInt : localConnected)
		{
			groupList.set(landInt, group);
			landList.remove(Integer.valueOf(landInt));
		}
		
		for (int landInt : localConnected)
		{
			getConnectedLand(landInt, group, landList, groupList, masterLandList);
		}
	}
	
	public void getBarrenLandCoordinates(String testCoordinates)
	{
		barrenLand.clear();
		BLA_SystemInScanner keyboard = new BLA_SystemInScanner();
		String setStr = "";
		
		System.out.print("Enter barren land coordinates: ");
		if(testCoordinates != null)
		{
			System.out.println(testCoordinates);
			setStr = testCoordinates;
		}
		else if(keyboard.hasNextLine())
		{
			setStr = keyboard.nextLine();
		}
			
		setStr = checkTrimFirstLast(setStr, '{', '}');
		String[] setStrArr = setStr.split(",");
		
		for (String rectCoordStr: setStrArr)
		{
			rectCoordStr = checkTrimFirstLast(rectCoordStr, '"', '"').trim();
			if (rectCoordStr.isEmpty())
			{
				continue;
			}
			
			String[] rectCoordStrArr = rectCoordStr.split(" ");
			
			int x1 = Integer.parseInt(rectCoordStrArr[0]);
			int y1 = Integer.parseInt(rectCoordStrArr[1]);
			int x2 = Integer.parseInt(rectCoordStrArr[2]);
			int y2 = Integer.parseInt(rectCoordStrArr[3]);
			
			BLA_RectangleLand newBarrenLand = new BLA_RectangleLand();
			newBarrenLand.setCoordinates(x1, y1, x2, y2);
			checkCoordinateBounds(newBarrenLand);
			barrenLand.add(newBarrenLand);
		}
		
		createFertileLand();
	}
	
	private String checkTrimFirstLast(String str, char validateFirst, char validateLast)
	{
		str = str.trim();
		int strLength = str.length();
		
		if (str.isEmpty())
		{
			str = "";
		}
		else if (!((str.charAt(0) == validateFirst) && (str.charAt(strLength-1) == validateLast)))
		{
			System.out.println("\tWarning: Ignoring invalid coordinate string \"" + str + "\"");
			str = "";
		}
		else
		{
			str = str.substring(1, (strLength-1));
		}
		
		return str;
	}
	
	private void checkCoordinateBounds(BLA_RectangleLand rect)
	{
		boolean outOfBounds = farmlandDim.isOutsideRectangle(rect);
		
		if (outOfBounds)
		{
			System.out.println("\tWarning: " + rect.toString() + " is out of bounds, using inbound portion.");
		}
	}
	
	private void updateBarrenVec(int curCoord, Vector<Integer> curBarrenLand)
	{
		removeSurpassedRect(curCoord, curBarrenLand);
		addNewBarrenLand(curCoord, curBarrenLand);
	}
	
	private void removeSurpassedRect(int curCoord, Vector<Integer> curBarrenLand)
	{
		List<Integer> surpassedList = new ArrayList<Integer>();
		for (int i : curBarrenLand)
		{
			BLA_RectangleLand element = barrenLand.elementAt(i);
			int elementX2Coord = element.getCoordinate(X2RECT);
			
			if (curCoord >= elementX2Coord)
			{
				surpassedList.add(i);
			}
		}
		curBarrenLand.removeAll(surpassedList);
	}
	
	private void addNewBarrenLand(int curCoord, Vector<Integer> curBarrenLand)
	{
		int barrenLandVecSize = barrenLand.size();
		for (int i = 0; i < barrenLandVecSize; i++) {
			BLA_RectangleLand land = barrenLand.elementAt(i);
			int x1Coord = land.getCoordinate(X1RECT);
			x1Coord -= (x1Coord > 0) ? 1 : 0;
			
			if (x1Coord == curCoord)
			{
				curBarrenLand.add(i);
			}
		}
	}
	
	private void createFertileLand()
	{
		fertileLand.clear();
		int isSecondXPass = 0;
		int isSecondYPass = 0;
		int curX1 = 0;
		int curY1, curX2, curY2;
		int maxX = farmlandDim.getCoordinate(X2RECT);
		int maxY = farmlandDim.getCoordinate(Y2RECT);
		Vector<Integer> curBarrenLand = new Vector<Integer>();
		
		updateBarrenVec(curX1, curBarrenLand);
		
		while (curX1 < maxX)
		{
			curY1 = 0;
			curX2 = findNextX(curX1 + isSecondXPass);
			isSecondXPass = (curX1 == curX2) ? 1 : 0;
			
			while (curY1 < maxY)
			{
				curY2 = findNextY((curY1 + isSecondYPass), curBarrenLand);
				isSecondYPass = (curY1 == curY2) ? 1 : 0;
				if (!hasYClash(curY1, curY2, curBarrenLand))
				{
					BLA_RectangleLand newFertileLand = new BLA_RectangleLand();
					newFertileLand.setCoordinates(curX1, curY1, curX2, curY2);
					fertileLand.add(newFertileLand);
				}
				
				curY1 = curY2;
			}
			
			updateBarrenVec(curX2, curBarrenLand);
			curX1 = curX2 + 1;
		}
	}
	
	private int findNextX(int curCoord)
	{
		int next = farmlandDim.getCoordinate(X2RECT);
		
		for (BLA_RectangleLand land : barrenLand)
		{
			next = findNextAxis(next, curCoord, land, X1RECT, X2RECT);
		}
		
		return next;
	}
	
	private int findNextY(int curCoord, Vector<Integer> curBarrenLand)
	{
		int next = farmlandDim.getCoordinate(Y2RECT);
		
		for (int i : curBarrenLand)
		{
			BLA_RectangleLand land = barrenLand.elementAt(i);
			next = findNextAxis(next, curCoord, land, Y1RECT, Y2RECT);
		}
		
		return next;
	}
	
	private int findNextAxis(int curNext, int curCoord, BLA_RectangleLand land,
			BLA_RectangleLand.RectangleCoordinate rect1Coord, BLA_RectangleLand.RectangleCoordinate rect2Coord)
	{
		assert ((rect1Coord == X1RECT) || (rect1Coord == Y1RECT)) : "Invalid Coordinate: " + rect1Coord.toString();
		assert ((rect2Coord == X2RECT) || (rect2Coord == Y2RECT)) : "Invalid Coordinate: " + rect2Coord.toString();
		
		int next = curNext;
		int axis1 = land.getCoordinate(rect1Coord) - 1;
		if (axis1 >= next)
		{
			return next;
		}
		
		if ((axis1) > curCoord)
		{
			next = axis1;
		}
		else
		{
			int axis2 = land.getCoordinate(rect2Coord);
			axis2 += (rect2Coord == Y2RECT) ? 1 : 0;
			
			if ((axis2 < next) && (axis2 > curCoord))
			{
				next = axis2;
			}
		}
		
		return next;
	}
	
	private boolean hasYClash(int coordY1, int coordY2, Vector<Integer> curBarrenLand)
	{
		boolean hasClash = false;
		
		for (int i: curBarrenLand)
		{
			BLA_RectangleLand land = barrenLand.elementAt(i);
			int y1 = land.getCoordinate(Y1RECT);
			int y2 = land.getCoordinate(Y2RECT);
			
			if (inBetween(coordY1, coordY2, y1) || inBetween(coordY1, coordY2, y2) ||
					(inBetween(y1, y2, coordY1) && inBetween(y1, y2, coordY2)))
			{
				hasClash = true;
				break;
			}
		}
		
		return hasClash;
	}
	
	private boolean inBetween(int low, int high, int value)
	{
		boolean inBetween = false;
		
		if ((low < value) && (high > value))
		{
			inBetween = true;
		}
		
		return inBetween;
	}
	
	private void initializeArrayList(InitializerOptions option, int startValue, ArrayList<Integer> arrList, int size)
	{
		int modValue;
		switch (option)
		{
			case INCREMENT:
				modValue = 1;
				break;
			case DECREMENT:
				modValue = -1;
				break;
			case UNIFORM:
				modValue = 0;
				break;
			default:
				throw new AssertionError(option);
		}
		
		for (int element = 0; element < size; element++)
		{
			arrList.add(startValue);
			startValue+= modValue;
		}
	}

}
