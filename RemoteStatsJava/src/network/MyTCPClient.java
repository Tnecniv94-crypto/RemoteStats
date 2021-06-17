package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyTCPClient {
	private Socket client;
    private PrintWriter out;
    private BufferedReader in;
    
    public String sendMessage(String msg, String ip, int port, String token) {
    	SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
		Date date = new Date();
    	String resp;
    	 
    	if(startConnection(ip, port)) {
    		 out.println(msg + "time=" + f.format(date) + ", token=" + token);
    	       
    		try {
    			resp = in.readLine();
    			
    			return resp;
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
    			
    		stopConnection();
    	}
       
		return "No response reveived.";
    }
    
    public void stopConnection() {
        try {
			in.close();
			out.close();
	        client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public boolean startConnection(String ip, int port) {
        try {
			client = new Socket(ip, port);
			out = new PrintWriter(client.getOutputStream(), true);
	        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		} catch (IOException e) {
			System.out.println("Can't connect to socket on ip=" + ip + " and port=" + port);
			
			return false;
		}
        
        return true;
    }


}
