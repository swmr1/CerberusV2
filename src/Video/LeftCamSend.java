package Video;

import java.io.IOException;
import java.net.*;
import java.util.Arrays;

import org.opencv.core.Mat;

import mainApp.Main;

public class LeftCamSend extends Thread {
	// TODO Edit to fit our needs
	public static int dir;
	public static double mag;

	public void run() {
		try {
			InetAddress host;

			host = InetAddress.getByName(Main.RemoteIp);
			@SuppressWarnings("resource")
			DatagramSocket serverSocket = new DatagramSocket();
			serverSocket.connect(host, 4444);
			VideoCap vid = new VideoCap(0);
			int i = 1;
			//DatagramSocket clientSocket = new DatagramSocket(4444);


			while (i == 1) {
				byte[] ready = "start".getBytes();
				DatagramPacket receivePacket = new DatagramPacket(ready, ready.length);
				System.out.println(ready.length);
				serverSocket.send(receivePacket);
				
				Mat sendData = vid.getOneFrame();
				System.out.println(sendData);
				byte[] data = new byte[(int)(sendData.total() * sendData.channels())];
				sendData.get(0, 0, data);
				
				for(int k = 0; k < sendData.height(); k++){
					byte[] temp = new byte[sendData.width()];
					for(int j = 0; j < sendData.width(); j++){
						int index = j + (k*sendData.width());
						temp[j] = data[index];
					}
//					System.out.println(k + " : " + temp.length + " : " +  Arrays.toString(temp));
					receivePacket = new DatagramPacket(temp, temp.length);
					serverSocket.send(receivePacket);
					Thread.sleep(1);
				}
//				Thread.sleep(10);

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