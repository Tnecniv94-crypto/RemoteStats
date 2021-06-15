package hardware;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import network.MyTCPClient;

public class ReadWindowsNvidiaGpu {
	private Runtime rt = Runtime.getRuntime();
	private MyTCPClient client;
	private boolean nvidiaSMIEnvironment = true;
	
	/**
	 * reads the GPUs' temperature and sends it to the server
	 * @param ip
	 * @param port
	 */
	protected void readGpusTemperatureToServer(String ip, int port, String token, int sleepSec) {
		int gpus = getGpusNumber();
		String temps;
		
		if(gpus < 1) {
			System.out.println("Exiting. No GPU found!");
		}
		
		client = new MyTCPClient();
		
		while(true) {
			//client = new MyTCPClient();
			temps = readGpusTemp(gpus);
			System.out.println(client.sendMessage(temps, ip, port, token));
			
			try {
				TimeUnit.SECONDS.sleep(sleepSec);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * reads the GPUs' power and sends it to the server
	 * @param ip
	 * @param port
	 */
	protected void readGpusPowerToServer(String ip, int port, String token, int sleepSec) {
		int gpus = getGpusNumber();
		String temps;
		
		if(gpus < 1) {
			System.out.println("Exiting. No GPU found!");
		}
		
		client = new MyTCPClient();
		
		while(true) {
			temps = readGpusPower(gpus);
			System.out.println(client.sendMessage(temps, ip, port, token));
			
			try {
				TimeUnit.SECONDS.sleep(sleepSec);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * reads the GPUs' power and sends it to the server
	 * @param ip
	 * @param port
	 */
	protected void readGpusFanSpeedToServer(String ip, int port, String token, int sleepSec) {
		int gpus = getGpusNumber();
		String temps;
		
		if(gpus < 1) {
			System.out.println("Exiting. No GPU found!");
		}
		
		client = new MyTCPClient();
		
		while(true) {
			temps = readGpusFanSpeed(gpus);
			System.out.println(client.sendMessage(temps, ip, port, token));
			
			try {
				TimeUnit.SECONDS.sleep(sleepSec);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * calls the correct function to read GPUs' temperatures
	 * @param gpus
	 * @return
	 */
	private String readGpusTemp(int gpus) {
		if(gpus < 1) {
			System.out.println("No GPU found!");
			
			return null;
		}
		
		return getCurrentNvidiaWindowsTemperature(gpus);
	}
	
	/**
	 * calls the correct function to read GPUs' power
	 * @param gpus
	 * @return
	 */
	private String readGpusPower(int gpus) {
		if(gpus < 1) {
			System.out.println("No GPU found!");
			
			return null;
		}
		
		return getCurrentNvidiaWindowsPower(gpus);
	}
	
	/**
	 * calls the correct function to read GPUs' fan speeds
	 * @param gpus
	 * @return
	 */
	private String readGpusFanSpeed(int gpus) {
		if(gpus < 1) {
			System.out.println("No GPU found!");
			
			return null;
		}
		
		return getCurrentNvidiaWindowsFanSpeed(gpus);
	}
	
	private String[][] getWindowsNvidiaGpusIdNameMapping() {
		String[][] ret;
		String[] buf;
		ArrayList<String> smi = getNvidiaWindowsSMI("nvidia-smi.exe -L");
		
		ret = new String[smi.size()][2];
		
		for(int i = 0; i < ret.length; i++) {
			buf = smi.get(i).split(":");
			ret[i][0] = buf[0].trim();
			ret[i][1] = buf[1].split("UUID:")[0].trim();
			ret[i][1] = ret[i][1].substring(0, ret[i][1].length() - 6);
		}
		
		return ret;
	}
	
	private int getGpusNumber() {
		return getNvidiaWindowsSMI("nvidia-smi.exe -L").size();
	}
	
	/**
	 * 
	 * @return String with GPU-ids and their temperature
	 */
	private String getCurrentNvidiaWindowsTemperature(int gpus) {
		ArrayList<String> output = getNvidiaWindowsSMI("nvidia-smi.exe --query --display=TEMPERATURE");
		String line;
		String ret = "";
		
		for(int i = 0; i < gpus - 1; i++) {
			line = output.get(10 + i * 10);
			ret += "id=" + i + " TEMP=" + line.split(": ")[1] + ", ";
		}
		
		line = output.get(10 + (gpus - 1) * 10);
		ret += "id=" + (gpus - 1) + " TEMP=" + line.split(": ")[1];
		
		return ret;
	}
	
	/**
	 * string with GPU-ids and their current power draw
	 * @param gpus
	 * @return
	 */
	private String getCurrentNvidiaWindowsPower(int gpus) {
		ArrayList<String> output = getNvidiaWindowsSMI("nvidia-smi.exe --query --display=POWER");
		String line;
		String ret = "";
		
		for(int i = 0; i < gpus - 1; i++) {
			line = output.get(11 + i * 16);
			ret += "id=" + i + " POWER=" + line.split(": ")[1] + ", ";
		}
		
		line = output.get(11 + (gpus - 1) * 16);
		ret += "id=" + (gpus - 1) + " POWER=" + line.split(": ")[1];
		
		return ret;
	}
	
	/**
	 * string with GPU-ids and their current power draw
	 * @param gpus
	 * @return
	 */
	private String getCurrentNvidiaWindowsFanSpeed(int gpus) {
		ArrayList<String> output = getNvidiaWindowsSMI("nvidia-smi.exe");
		String line, buf;
		String ret = "";
		
		for(int i = 0; i < gpus - 1; i++) {
			line = output.get(9 + i * 4);
			buf = line.split("%")[0];
			ret += "id=" + i + " FAN=" + buf.substring(2, buf.length()) + "%, ";
		}
		
		line = output.get(9 + (gpus - 1) * 4);
		buf = line.split("%")[0];
		ret += "id=" + (gpus - 1) + " FAN=" + buf.substring(2, buf.length()) + "%";
		
		return ret;
	}
	
	/**
	 * 
	 * @return the whole nvidia-smi response for "nvidia-smi.exe --query --display=TEMPERATURE"
	 */
	private ArrayList<String> getNvidiaWindowsSMI(String query) {
		try {
			Process pr = rt.exec(query);
			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));
			LinkedList<String> lines = new LinkedList<>();
			ArrayList<String> ret; 
            String line = null;
            int exitVal;

            while((line = input.readLine()) != null) {
                lines.add(line);
            }
            
			try {
				exitVal = pr.waitFor();
				
				System.out.println("exited: '" + query + "' with exit value: " + exitVal);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ret = new ArrayList<>(lines);

			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * outputs the nvidia-smi response
	 * @param param=TEMP: "nvidia-smi.exe --query --display=TEMPERATURE", param=POWER: "nvidia-smi.exe --query --display=POWER", param=NAME: "nvidia-smi.exe -L"
	 */
	public void showSMI(String param) {
		ArrayList<String> smiLines;
		
		switch(param) {
			case "TEMP": {
				smiLines = getNvidiaWindowsSMI("nvidia-smi.exe --query --display=TEMPERATURE");
				
				for(String l : smiLines) {
					System.out.println(l);
				}
				System.out.println();
				
				break;
			}
			case "POWER": {
				smiLines = getNvidiaWindowsSMI("nvidia-smi.exe --query --display=POWER");
				
				for(String l : smiLines) {
					System.out.println(l);
				}
				System.out.println();
				
				break;
			}
			case "FAN": {
				smiLines = getNvidiaWindowsSMI("nvidia-smi.exe");
				
				for(String l : smiLines) {
					System.out.println(l);
				}
				System.out.println();
				
				break;
			}
			case "NAME": {
				smiLines = getNvidiaWindowsSMI("nvidia-smi.exe -L");
				
				for(String l : smiLines) {
					System.out.println(l);
				}
				System.out.println();
				
				break;
			}
			case "IDNAMEMAPPING": {
				String[][] mapping = getWindowsNvidiaGpusIdNameMapping();
				
				for(int i = 0; i < mapping.length; i++) {
					System.out.println(mapping[i][0] + " " + mapping[i][1]);
				}
				
				break;
			}
			default: {
				System.out.println("Unknown param.");
			}
		}
	}
}
