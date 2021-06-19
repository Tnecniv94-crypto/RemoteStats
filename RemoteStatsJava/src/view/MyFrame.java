package view;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

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
	private Thread tTemp, tPower, tFans;
	private ReadWindowsNvidiaGpuTemp temp = null;
	private ReadWindowsNvidiaGpuPower power = null;
	private ReadWindowsNvidiaGpuFanSpeed fans = null;
	private InputPanel inputPanel;
	private ConsolePanel consolePanel;
	private String ip;
	private int port;

	public static JFrame frame;

	public static void main(String[] args) {
		new MyFrame();
	}

	public MyFrame() {
		setUpView();
		setUpData();
	}

	private void setUpView() {
		frame = new JFrame();
		inputPanel = new InputPanel(this);
		consolePanel = new ConsolePanel();

		frame.setTitle("RemoteStats");
		frame.setResizable(false);
		frame.setSize(800, 550);
		frame.setLocation(100, 100);
		frame.setVisible(true);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(inputPanel, BorderLayout.NORTH);
		frame.add(consolePanel, BorderLayout.SOUTH);
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("icon.jpg"));
	}

	public void setUpData() {
		String token;
		
		ip = getIpFromConfig();
		port = getPortFromConfig();
		
		token = inputPanel.getTokenPanel().getToken();
		temp = new ReadWindowsNvidiaGpuTemp(ip, port, token, Constants.sleepSec);
		power = new ReadWindowsNvidiaGpuPower(ip, port, token, Constants.sleepSec);
		fans = new ReadWindowsNvidiaGpuFanSpeed(ip, port, token, Constants.sleepSec);
	}

	public void run(boolean t, boolean p, boolean f) {
		if (t) {
			System.out.println("Starting temperature report thread.");
			runWindowsNvidiaGpusTemperature();
		}

		if (p) {
			System.out.println("Starting power report thread.");
			runWindowsNvidiaGpusPower();
		}

		if (f) {
			System.out.println("Starting fan speed report thread.");
			runWindowsNvidiaGpusFanSpeed();
		}
	}

	public void delete(boolean t, boolean p, boolean f) {
		String token = inputPanel.getTokenPanel().getToken();

		if (t) {
			System.out.println("Deleting temperatures file from server.");
			temp.deleteGpusTempFromServer(ip, port, token);
		}

		if (p) {
			System.out.println("Deleting power file from server.");
			power.deleteGpusPowerFromServer(ip, port, token);
		}

		if (f) {
			System.out.println("Deleting fans speed file from server.");
			fans.deleteGpusFanSpeedFromServer(ip, port, token);
		}
	}

	public void stopAllThreads() {
		if (temp.stop()) {
			System.out.println("Stopped temperature report thread.");
		}

		if (power.stop()) {
			System.out.println("Stopped power report thread.");
		}

		if (fans.stop()) {
			System.out.println("Stopped fan speed report thread.");
		}
	}

	private void runWindowsNvidiaGpusTemperature() {
		tTemp = new Thread(temp);
		tTemp.start();
	}

	private void runWindowsNvidiaGpusPower() {
		tPower = new Thread(power);
		tPower.start();
	}

	private void runWindowsNvidiaGpusFanSpeed() {
		tFans = new Thread(fans);
		tFans.start();
	}

	private String getIpFromConfig() {
		Properties prop = new Properties();
		FileInputStream fStream;
		String ret;

		try {
			prop.load(fStream = new FileInputStream("config.properties"));
			ret = prop.getProperty("ip");
			fStream.close();

			return ret;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	private int getPortFromConfig() {
		Properties prop = new Properties();
		FileInputStream fStream;
		String ret;

		try {
			prop.load(fStream = new FileInputStream("config.properties"));
			ret = prop.getProperty("port");
			fStream.close();

			return Integer.valueOf(ret);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return -1;
	}

	public InputPanel getInputPanel() {
		return inputPanel;
	}
	
	public ConsolePanel getConsolePanel() {
		return consolePanel;
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
