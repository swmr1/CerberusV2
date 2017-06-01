package mainApp;

import java.io.*;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Random;

import gnu.io.*;

public class Thrusters extends Thread implements SerialPortEventListener {
	SerialPort serialPort;
	/** The port we're normally going to use. */
	private static final String PORT_NAMES[] = { "COM5", // Windows
	};
	/**
	 * A BufferedReader which will be fed by a InputStreamReader converting the
	 * bytes into characters making the displayed results codepage independent
	 */
	private BufferedReader input;
	/** The output stream to the port */
	private static OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 115200;

	public void initialize() {
		// the next line is for Raspberry Pi and
		// gets us into the while loop and was suggested here was suggested
		// http://www.raspberrypi.org/phpBB3/viewtopic.php?f=81&t=32186
		// System.setProperty("gnu.io.rxtx.SerialPorts", "/dev/ttyACM0");

		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

		// First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}

		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);

		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port. This will prevent
	 * port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
			try {
				String inputLine = input.readLine();
				// System.out.println(inputLine);
			} catch (Exception e) {
				System.err.println(e.toString());
			}
		}
		// Ignore all the other eventTypes, but you should consider the other
		// ones.
	}

	@Override
	public void run() {
		initialize();
		try {
			while (true) {
				// Random temp = new Random();
				String toSend = "";

				if (Main.AxisXY <= 135 && Main.AxisXY >= 45) {
					System.out.println("Forward");
					toSend = "0,59,0,59,0,59,";
					if (Main.fire == 0) {
						toSend += "0,0,0,0,";
					} else {
						toSend += "1,1,1," + Main.mag + ",";
					}

				} else if (Main.AxisXY <= 225 && Main.AxisXY >= 136) {
					System.out.println("right");
					toSend = "90,59,90,59,120,59,";
					if (Main.fire == 0) {
						toSend += "0,0,0,0,";
					} else {
						toSend += "1,1,0," + Main.mag + ",";
					}

				} else if (Main.AxisXY <= 315 && Main.AxisXY >= 225) {
					System.out.println("Back");
					toSend = "180,59,180,59,180,59,";
					if (Main.fire == 0) {
						toSend += "0,0,0,0,";
					} else {
						toSend += "1,1,1," + Main.mag + ",";
					}

				} else if (Main.Yaw > 20) {
					System.out.println("Cw");
					toSend = "0,59,0,59,180,59,";
					if (Main.fire == 0) {
						toSend += "0,0,0,0,";
					} else {
						toSend += "0,1,1," + Main.mag + ",";
					}
				} else if (Main.Yaw < -20) {
					System.out.println("Ccw");
					toSend = "0,59,180,59,0,59,";
					if (Main.fire == 0) {
						toSend += "0,0,0,0,";
					} else {
						toSend += "0,1,1," + Main.mag + ",";
					}
				} else if (Main.Pitch == 100) {
					System.out.println("Up");
					toSend = "0,59,90,89,90,209,";
					if (Main.fire == 0) {
						toSend += "0,0,0,0,";
					} else {
						toSend += "0,1,1," + Main.mag + ",";
					}
				} else if (Main.Pitch == -100) {
					System.out.println("down");
					toSend = "0,149,0,59,0,59,";
					if (Main.fire == 0) {
						toSend += "0,0,0,0,";
					} else {
						toSend += "1,0,0," + Main.mag + ",";
					}
				} else {
					System.out.println("Left");
					toSend = "270,59,0,59,90,119,";
					if (Main.fire == 0) {
						toSend += "0,0,0,0,";
					} else {
						toSend += "1,0,1," + Main.mag + ",";
					}

				}
				//toSend += "1,1,1,";
				output.write(toSend.getBytes());
				System.out.println(toSend );
				Thread.sleep(1000);
			}
		} catch (IOException | InterruptedException ie) {
		}
	}
}