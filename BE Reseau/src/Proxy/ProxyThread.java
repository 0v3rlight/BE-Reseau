package Proxy;

import java.util.Map;

public class ProxyThread extends Thread {
	
	private Map<String, Integer> Ports;
	private String IPServer;
	private String IPClient;
	private String IPProxy;

	public ProxyThread(String IPServer, String IPClient, Map<String, Integer> Ports) {
		this.Ports = Ports;
		this.IPServer = IPServer;
		this.IPClient = IPClient;
		
	}
	
	public void run() {
		
		// scenario 1 : 
		// scenario 2 : 
		// scenario 3 : 
		// scenario 4 : 
		// scenario 5 : 
		
	}
}
