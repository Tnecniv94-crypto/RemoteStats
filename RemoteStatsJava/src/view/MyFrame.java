package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;

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
	private ReadWindowsNvidiaGpuTemp temp = null;
	private ReadWindowsNvidiaGpuPower power = null;
	private ReadWindowsNvidiaGpuFanSpeed fans = null;
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
		String token;
		
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
	    frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.jpg"));
	    
	    token = inputPanel.getTokenPanel().getToken();
	    temp = new ReadWindowsNvidiaGpuTemp(Constants.ip, Constants.port, token, Constants.sleepSec);
	    power = new ReadWindowsNvidiaGpuPower(Constants.ip, Constants.port, token, Constants.sleepSec);
	    fans = new ReadWindowsNvidiaGpuFanSpeed(Constants.ip, Constants.port, token, Constants.sleepSec);
	}
	
	public void run(boolean t, boolean p, boolean f) {
		String token = inputPanel.getTokenPanel().getToken();
		
		if(t) {
			System.out.println("Starting temperature report thread.");
			runWindowsNvidiaGpusTemperature(Constants.ip, Constants.port, token, Constants.sleepSec);
		}
		
		if(p) {
			System.out.println("Starting power report thread.");
			runWindowsNvidiaGpusPower(Constants.ip, Constants.port, token, Constants.sleepSec);
		}
		
		if(f) {
			System.out.println("Starting fan speed report thread.");
			runWindowsNvidiaGpusFanSpeed(Constants.ip, Constants.port, token, Constants.sleepSec);
		}
	}
	
	public void delete(boolean t, boolean p, boolean f) {
		String token = inputPanel.getTokenPanel().getToken();
		
		if(t) {
			System.out.println("Deleting temperatures file from server.");
			temp.deleteGpusTempFromServer(Constants.ip, Constants.port, token);
		}
		
		if(p) {
			System.out.println("Deleting power file from server.");
			power.deleteGpusPowerFromServer(Constants.ip, Constants.port, token);
		}
		
		if(f) {
			System.out.println("Deleting fans speed file from server.");
			fans.deleteGpusFanSpeedFromServer(Constants.ip, Constants.port, token);
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
		tTemp = new Thread(temp);
		tTemp.start();
	}
	
	private void runWindowsNvidiaGpusPower(String ip, int port, String token, int sleepSec) {
		tPower = new Thread(power);
		tPower.start();
	}
	
	private void runWindowsNvidiaGpusFanSpeed(String ip, int port, String token, int sleepSec) {
		tFans = new Thread(fans);
		tFans.start();
	}
	
	public InputPanel getInputPanel() {
		return inputPanel;
	}
	
	public ReadWindowsNvidiaGpuTemp getReadWindowsNvidiaGpuTemp() {
		return temp;
	}
	
	public ReadWindowsNvidiaGpuPower getReadWindowsNvidiaGpuPower() {
		return power;
	}
	
	public ReadWindowsNvidiaGpuFanSpeed getReadWindowsNvidiaGpuFans() {
		return fans;
	}
}
