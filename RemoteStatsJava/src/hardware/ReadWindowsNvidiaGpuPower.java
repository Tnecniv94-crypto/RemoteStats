package hardware;

public class ReadWindowsNvidiaGpuPower extends ReadWindowsNvidiaGpu implements Runnable {
	private String ip, token;
	private int port, sleepSec;
	
	public ReadWindowsNvidiaGpuPower(String ip, int port, String token, int sleepSec) {
		this.ip = ip;
		this.port = port;
		this.token = token;
		this.sleepSec = sleepSec;
	}
	
	@Override
	protected void readGpusPowerToServer(String ip, int port, String token, int sleepSec) {
		super.readGpusPowerToServer(ip, port, token, sleepSec);
	}
	
	@Override
	public void run() {
		readGpusPowerToServer(ip, port, token, sleepSec);
	}
}
