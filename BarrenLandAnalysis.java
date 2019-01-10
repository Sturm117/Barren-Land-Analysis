
public class BarrenLandAnalysis {

	public static void main() {
		BLA_Farmland defaultFarm = new BLA_Farmland(399,599);
		
		defaultFarm.getBarrenLandCoordinates(null);
		defaultFarm.printConnectedFertileArea();
	}
	
	public static void main(String[] args) {
		BLA_Farmland defaultFarm = new BLA_Farmland(399,599);
		
		defaultFarm.getBarrenLandCoordinates(args[0]);
		defaultFarm.printConnectedFertileArea();
	}
}
