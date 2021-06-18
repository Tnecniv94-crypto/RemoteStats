package hardware;

public class ReadWindowsNvidiaGpuFanSpeed extends ReadWindowsNvidiaGpu implements Runnable {
	private String ip, token;
	private int port, sleepSec;
	
	public ReadWindowsNvidiaGpuFanSpeed(String ip, int port, String token, int sleepSec) {
		this.ip = ip;
		this.port = port;
		this.token = token;
		this.sleepSec = sleepSec;
	}
	
	public void deleteGpusFanSpeedFromServer(String ip, int port, String token) {
		if(!isRunning()) {
			super.deleteGpusStatsFromServer(ip, port, token, "fans");
		}
		else {
			System.out.println("Can't delete fans speed file, because instance is reporting.");
		}
	}
	
	@Override
	protected void readGpusFanSpeedToServer(String ip, int port, String token, int sleepSec) {
		super.readGpusFanSpeedToServer(ip, port, token, sleepSec);
	}
	
	@Override
	public void run() {
		super.run();
		readGpusFanSpeedToServer(ip, port, token, sleepSec);
	}
	
	@Override
	public boolean stop() {
		return super.stop();
	}
}
