package view;

import java.awt.Frame;

import javax.swing.JFrame;

import hardware.ReadWindowsNvidiaGpuFanSpeed;
import hardware.ReadWindowsNvidiaGpuPower;
import hardware.ReadWindowsNvidiaGpuTemp;
import network.TokenGenerator;

public class MyFrame extends JFrame {
	public static void main(String[] args) {
		Frame frame= new Frame();
	    frame.setTitle("RemoteStats");
	    frame.setSize(1000, 620);
	    frame.setResizable(false);
	    frame.setLocation(50, 50);
	    frame.setVisible(true);
		
		String token = TokenGenerator.generateToken();
		
		runWindowsNvidiaGpusTemperature("localhost", Constants.port, token, Constants.sleepSec);
		runWindowsNvidiaGpusPower("localhost", Constants.port, token, Constants.sleepSec);
		runWindowsNvidiaGpusFanSpeed("localhost", Constants.port, token, Constants.sleepSec);
	}
	 
	private static void runWindowsNvidiaGpusTemperature(String ip, int port, String token, int sleepSec) {
		Thread temp = new Thread(new ReadWindowsNvidiaGpuTemp(ip, port, token, sleepSec));
		temp.start();
	}
	
	private static void runWindowsNvidiaGpusPower(String ip, int port, String token, int sleepSec) {
		Thread power = new Thread(new ReadWindowsNvidiaGpuPower(ip, port, token, sleepSec));
		power.start();
	}
	
	private static void runWindowsNvidiaGpusFanSpeed(String ip, int port, String token, int sleepSec) {
		Thread fans = new Thread(new ReadWindowsNvidiaGpuFanSpeed(ip, port, token, sleepSec));
		fans.start();
	}
}
