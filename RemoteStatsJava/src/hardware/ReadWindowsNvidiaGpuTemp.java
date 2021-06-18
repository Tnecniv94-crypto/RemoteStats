package hardware;

public class ReadWindowsNvidiaGpuTemp extends ReadWindowsNvidiaGpu implements Runnable {
	private String ip, token;
	private int port, sleepSec;
	
	public ReadWindowsNvidiaGpuTemp(String ip, int port, String token, int sleepSec) {
		this.ip = ip;
		this.port = port;
		this.token = token;
		this.sleepSec = sleepSec;
	}
	
	public void deleteGpusTempFromServer(String ip, int port, String token) {
		if(!isRunning()) {
			super.deleteGpusStatsFromServer(ip, port, token, "temps");
		}
		else {
			System.out.println("Can't delete temperature file, because instance is reporting.");
		}
	}
	
	@Override
	protected void readGpusTemperatureToServer(String ip, int port, String token, int sleepSec) {
		super.readGpusTemperatureToServer(ip, port, token, sleepSec);
	}
	
	@Override
	public void run() {
		super.run();
		readGpusTemperatureToServer(ip, port, token, sleepSec);
	}
	
	@Override
	public boolean stop() {
		return super.stop();
	}

}
