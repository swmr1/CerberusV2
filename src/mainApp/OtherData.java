//package mainApp;
//import java.io.IOException;
//import java.net.DatagramPacket;
//import java.net.DatagramSocket;
//import java.net.SocketException;
//import java.net.SocketTimeoutException;
//
//public class OtherData extends Thread{
//	/**
//	 * XY + ", " + Yaw+ ", "+ Pitch + ", " + Roll + ", "+ Stop;
//	 */
//	@SuppressWarnings("deprecation")
//	public void run() { 
//			try {
//				DatagramSocket clientSocket = new DatagramSocket(Main.portOtherComands);
//				byte[] receiveData = new byte[1024];  
//			    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//			    while(!clientSocket.isClosed()){
//			    	clientSocket.receive(receivePacket);
//			    	
//			    	String input = new String(receivePacket.getData());
//			    	byte[] test = input.getBytes();
//			    	String SpaceRemoved = "";
//			    	for (byte temp:test){
//			    		if(temp != 0){
//			    			SpaceRemoved += (char)temp;
//			    		}
//			    	}
//
//			    	switch (SpaceRemoved) {
//					case "ModeFlight":
//						System.out.println("FF");
//						if (Main.right.isAlive()){
//							Main.right.stop();
//						}
//						if (Main.left.isAlive()){
//							Main.left.stop();
//						}
//						break;
//					case "ModeRobotics":
//						System.out.println("TEST");
//						System.out.println(Main.left.getState());
//						if (!Main.left.isAlive()){
//							Main.left.start();
//						}
//						
//						break;
//					default:
//						System.out.println("T");
//						break;
//					}
//			    }
//			    clientSocket.close();
//			      
//			} catch (SocketException e) {
//				System.out.println("SocketException");
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}  catch (SocketTimeoutException e){
//				System.out.println("Timeout");
//				emgShutdown.SystemSafeState();
//				
//			} catch (IOException e) {
//				System.out.println("IO exception");
//				e.printStackTrace();
//			}
//		    
//		
//                
//	}
//
//}
