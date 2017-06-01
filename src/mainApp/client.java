package mainApp;
import java.io.*;
import java.net.*;

import javax.swing.plaf.synth.SynthSeparatorUI;


public class client extends Thread{
	/**
	 * Mag + ", " + XY + ", " + Yaw+ ", "+ Pitch + ", " + Roll + ", "+ fire;
	 */
	public void run() { 
			try {
				
				DatagramSocket clientSocket = new DatagramSocket(Main.portMovement);
				byte[] receiveData = new byte[1024];  
			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			    while(!clientSocket.isClosed()){
			    	clientSocket.receive(receivePacket);
			    	String modifiedSentence = new String(receivePacket.getData());
			    	switch (modifiedSentence) {
					case "EMERGENCY":
						emgShutdown.SystemSafeState();
						break;
					default:
						String[] split = modifiedSentence.split(",");
						Main.mag = (int)(7.5)*(Integer.parseInt(split[4])) + 250;
						Main.AxisXY = Integer.parseInt(split[0]);
						Main.Yaw = Integer.parseInt(split[1]);
						Main.Pitch = Integer.parseInt(split[2]);
						Main.Roll = Integer.parseInt(split[3]);
						Main.fire = Integer.parseInt(split[5]);

						break;
					}
			    	System.out.println( modifiedSentence);
			    }
			    clientSocket.close();
			      
			} catch (SocketException e) {
				System.out.println("SocketException");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  catch (SocketTimeoutException e){
				System.out.println("Timeout");
				emgShutdown.SystemSafeState();
				
			} catch (IOException e) {
				System.out.println("IO exception");
				e.printStackTrace();
			}
		    
		
                
	}

}
