
public class BarrenLandAnalysis {

	public static void main(String[] args) {
		BLA_Farmland defaultFarm = new BLA_Farmland(399,599);
		
		if (args.length == 1)
		{
			defaultFarm.getBarrenLandCoordinates(args[0]);
		}
		else
		{
			defaultFarm.getBarrenLandCoordinates(null);
		}
		defaultFarm.printConnectedFertileArea();
	}
}
