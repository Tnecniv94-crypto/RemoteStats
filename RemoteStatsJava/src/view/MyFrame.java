package view;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import hardware.ReadWindowsNvidiaGpuFanSpeed;
import hardware.ReadWindowsNvidiaGpuPower;
import hardware.ReadWindowsNvidiaGpuTemp;

public class MyFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3782873543529642549L;
	private Thread tTemp, tPower, tFans;
	private ReadWindowsNvidiaGpuTemp temp;
	private ReadWindowsNvidiaGpuPower power;
	private ReadWindowsNvidiaGpuFanSpeed fans;
	private InputPanel inputPanel;
	private ConsolePanel consolePanel;

	public static JFrame frame;
	
	public static void main(String[] args) {
		new MyFrame();
	}
	
	public MyFrame() {
		setUpView();
	}
	
	private void setUpView() {
		frame = new JFrame();
		inputPanel = new InputPanel(this);
		consolePanel = new ConsolePanel();
		
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
	
	public void run(boolean temp, boolean power, boolean fans) {
		String token = inputPanel.getTokenPanel().getToken();
		
		if(temp) {
			System.out.println("Started temperature report thread.");
			runWindowsNvidiaGpusTemperature(Constants.ip, Constants.port, token, Constants.sleepSec);
		}
		
		if(power) {
			System.out.println("Started power report thread.");
			runWindowsNvidiaGpusPower(Constants.ip, Constants.port, token, Constants.sleepSec);
		}
		
		if(fans) {
			System.out.println("Started fan speed report thread.");
			runWindowsNvidiaGpusFanSpeed(Constants.ip, Constants.port, token, Constants.sleepSec);
		}
	}
	
	public void stopAllThreads() {
		consolePanel.getConsole().setText("");
		
		if(temp.stop()) {
			System.out.println("Stopped temperature report thread.");
		}
		
		if(power.stop()) {
			System.out.println("Stopped power report thread.");
		}
		
		if(fans.stop()) {
			System.out.println("Stopped fan speed report thread.");
		}
	}
	 
	private void runWindowsNvidiaGpusTemperature(String ip, int port, String token, int sleepSec) {
		tTemp = new Thread(temp = new ReadWindowsNvidiaGpuTemp(ip, port, token, sleepSec));
		tTemp.start();
	}
	
	private void runWindowsNvidiaGpusPower(String ip, int port, String token, int sleepSec) {
		tPower = new Thread(power = new ReadWindowsNvidiaGpuPower(ip, port, token, sleepSec));
		tPower.start();
	}
	
	private void runWindowsNvidiaGpusFanSpeed(String ip, int port, String token, int sleepSec) {
		tFans = new Thread(fans = new ReadWindowsNvidiaGpuFanSpeed(ip, port, token, sleepSec));
		tFans.start();
	}
	
	public InputPanel getInputPanel() {
		return inputPanel;
	}
}
