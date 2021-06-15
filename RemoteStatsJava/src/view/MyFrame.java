package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import hardware.ReadWindowsNvidiaGpuFanSpeed;
import hardware.ReadWindowsNvidiaGpuPower;
import hardware.ReadWindowsNvidiaGpuTemp;
import network.TokenGenerator;

public class MyFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3782873543529642549L;
	private static Thread temp, power, fans;
	
	public static void main(String[] args) {
		setUpView();
		
		/*String token = TokenGenerator.generateToken();
		
		runWindowsNvidiaGpusTemperature("localhost", Constants.port, token, Constants.sleepSec);
		runWindowsNvidiaGpusPower("localhost", Constants.port, token, Constants.sleepSec);
		runWindowsNvidiaGpusFanSpeed("localhost", Constants.port, token, Constants.sleepSec);*/
	}
	
	private static void setUpView() {
		JFrame frame = new JFrame();
		InputPanel inputPanel = new InputPanel();
		ConsolePanel consolePanel = new ConsolePanel();
		
	    frame.setTitle("RemoteStats");
	    frame.setResizable(false);
	    frame.setSize(800, 600);
	    frame.setLocation(100, 100);
	    frame.setVisible(true);
	    frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.add(inputPanel, BorderLayout.NORTH);
	    frame.add(consolePanel, BorderLayout.SOUTH);
	}
	
	public static void run(boolean temp, boolean power, boolean fans) {
		String token = TokenGenerator.generateToken();
		
		if(temp) {
			runWindowsNvidiaGpusTemperature("localhost", Constants.port, token, Constants.sleepSec);
		}
		
		if(power) {
			runWindowsNvidiaGpusPower("localhost", Constants.port, token, Constants.sleepSec);
		}
		
		if(fans) {
			runWindowsNvidiaGpusFanSpeed("localhost", Constants.port, token, Constants.sleepSec);
		}
	}
	 
	private static void runWindowsNvidiaGpusTemperature(String ip, int port, String token, int sleepSec) {
		temp = new Thread(new ReadWindowsNvidiaGpuTemp(ip, port, token, sleepSec));
		temp.start();
	}
	
	private static void runWindowsNvidiaGpusPower(String ip, int port, String token, int sleepSec) {
		power = new Thread(new ReadWindowsNvidiaGpuPower(ip, port, token, sleepSec));
		power.start();
	}
	
	private static void runWindowsNvidiaGpusFanSpeed(String ip, int port, String token, int sleepSec) {
		fans = new Thread(new ReadWindowsNvidiaGpuFanSpeed(ip, port, token, sleepSec));
		fans.start();
	}
}
