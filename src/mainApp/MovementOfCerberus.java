package mainApp;

public class MovementOfCerberus extends Thread {
	public static int movX;
	public static int movY;
	public static int movZ;
	
	public static int move() throws InterruptedException{
		int i = 0;
		while (i == 0){
			Thread.sleep(1000);
			System.out.println(movX + " , " + movY + ", " + movZ);
		}
		return -1;
	}

}
