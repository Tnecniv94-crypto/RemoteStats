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
	
	@Override
	protected void readGpusTemperatureToServer(String ip, int port, String token, int sleepSec) {
		super.readGpusTemperatureToServer(ip, port, token, sleepSec);
	}
	
	@Override
	public void run() {
		readGpusTemperatureToServer(ip, port, token, sleepSec);
	}

}
