package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.ScrollPane;
import java.io.PrintStream;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import hardware.ReadWindowsNvidiaGpuFanSpeed;
import hardware.ReadWindowsNvidiaGpuPower;
import hardware.ReadWindowsNvidiaGpuTemp;
import network.TokenGenerator;

public class MyFrame extends JFrame {
	public static void main(String[] args) {
		setUpView();
		
		/*String token = TokenGenerator.generateToken();
		
		runWindowsNvidiaGpusTemperature("localhost", Constants.port, token, Constants.sleepSec);
		runWindowsNvidiaGpusPower("localhost", Constants.port, token, Constants.sleepSec);
		runWindowsNvidiaGpusFanSpeed("localhost", Constants.port, token, Constants.sleepSec);*/
	}
	
	private static void setUpView() {
		JFrame frame= new JFrame();
		InputPanel inputPanel = new InputPanel();
		ConsolePanel consolePanel = new ConsolePanel();
		JTextArea console = new JTextArea(15, 40);
		JLabel consoleTitle = new JLabel("Console log:");
		
	    frame.setTitle("RemoteStats");
	    frame.setResizable(false);
	    frame.setSize(800, 600);
	    frame.setLocation(100, 100);
	    frame.setVisible(true);
	    frame.setLayout(new BorderLayout());
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    frame.add(inputPanel, BorderLayout.NORTH);
	    frame.add(consolePanel, BorderLayout.SOUTH);
	    console.setEditable(false);
	    consolePanel.setLayout(new BoxLayout(consolePanel, BoxLayout.PAGE_AXIS));
	    consoleTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
	    consolePanel.add(consoleTitle);
	    consolePanel.add(new JScrollPane(console));
	    
	    PrintStream printStream = new PrintStream(new CustomOutputStream(console));
	    System.setOut(printStream);
	    System.setErr(printStream);
	    
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
