import java.util.Scanner;

public class BLA_SystemInScanner {
	private static Scanner keyboard = new Scanner(System.in);
	
	public boolean hasNextLine()
	{
		return (keyboard.hasNextLine());
	}
	
	public String nextLine()
	{
		return (keyboard.nextLine());
	}
}
