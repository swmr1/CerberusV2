package mainApp;
import java.io.IOException;
import java.net.*;
import java.util.Random;

public class UDPServer extends Thread
{
	//TODO Edit to fit our needs
	public static int dir;
	public static double mag;
	
   public void run(){
	   try{
		   InetAddress host;

			host = InetAddress.getByName(Main.RemoteIp);
		   @SuppressWarnings("resource")
		   DatagramSocket serverSocket = new DatagramSocket();
		   serverSocket.connect(host, Main.portSending);

		   int i = 1;
	   
		   while(i == 1){
			   byte[] sendData = new byte[1024];
			   
			   
			   String toSend = Main.Prox1 +"," + Main.Prox2 + "," + Main.Prox3 + "," + Main.Prox4 +"," + Main.rangeFinder;

			   sendData = toSend.getBytes();
			   DatagramPacket receivePacket = new DatagramPacket(sendData, sendData.length);
			   System.out.println("SENT: " + toSend);
			   serverSocket.send(receivePacket);
			   Thread.sleep(1000);
		   }
		   
	   } catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	   } catch (SocketException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   } catch (IOException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   } catch (InterruptedException e) {
		   // TODO Auto-generated catch block
		   e.printStackTrace();
	   }
    }
}