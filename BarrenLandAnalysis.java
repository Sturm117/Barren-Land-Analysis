
public class BarrenLandAnalysis {
	BLA_Farmland defaultFarm = new BLA_Farmland(399,599);

	public static void main() {
		defaultFarm.getBarrenLandCoordinates(null);
		defaultFarm.printConnectedFertileArea();
	}
	
	public static void main(String[] args) {	
		defaultFarm.getBarrenLandCoordinates(args[0]);
		defaultFarm.printConnectedFertileArea();
	}
}
