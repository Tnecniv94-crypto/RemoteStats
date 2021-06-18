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
	
	public void deleteGpusPowerFromServer(String ip, int port, String token) {
		if(!isRunning()) {
			super.deleteGpusStatsFromServer(ip, port, token, "power");
		}
		else {
			System.out.println("Can't delete power file, because instance is reporting.");
		}
	}
	
	@Override
	protected void readGpusPowerToServer(String ip, int port, String token, int sleepSec) {
		super.readGpusPowerToServer(ip, port, token, sleepSec);
	}
	
	@Override
	public void run() {
		super.run();
		readGpusPowerToServer(ip, port, token, sleepSec);
	}
	
	@Override
	public boolean stop() {
		return super.stop();
	}
}
