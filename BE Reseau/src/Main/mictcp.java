package Main;



import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ClientSocket.MICclientSocket;
import ServerSocket.MICServerThread;

public class mictcp {
	
	private boolean modeServer;
	private boolean modeClient;

	private int Distant_Server_Port;
	private int Distant_Client_Port;
	private int Local_Server_Port;
	private int Local_Client_Port;
	private Map<String, Integer> Ports;

	private String Distant_IP;
	
	private MICServerThread ServerThread;
	private MICclientSocket clientSocket;
	
	private int errorRate;
	private int startingSequenceNumber;

	
	public mictcp(String mode, String Address, int LocalPort, int DistantPort, int ErrorRate) {
		
		
		
		this.setMode(mode);
		this.setAddress(Address);
		this.setLocalPort(LocalPort);
		this.setDistantPort(DistantPort);
		this.errorRate = ErrorRate;
		this.startingSequenceNumber = 0;
		
		if (modeServer) {
			this.ServerThread = new MICServerThread(Distant_IP, Ports, startingSequenceNumber);
			ServerThread.start();
			//System.wait(10000);
		}
		
		
		if (modeClient) {
			this.clientSocket = new MICclientSocket(Distant_IP, Ports, ErrorRate, startingSequenceNumber);
		}
		
	}
	public void setMode(String mode) {
		if (mode.equals("server")) {
			this.modeServer= true;
			this.modeClient= false;
		}
		else if (mode.equals("client")) {
			this.modeServer= false;
			this.modeClient= true;
		}
		else {
			System.out.println("mode must be 'server' or 'client'");
		}
	}
	public void setAddress(String address) {
		this.Distant_IP = address;
	}
	public void setLocalPort(int port) {
		this.Local_Server_Port = port;
		this.Local_Client_Port = port+1;
		

		this.Ports = new HashMap<String, Integer>();
		
		this.Ports.put("Local_Server_Port", Local_Server_Port);
		this.Ports.put("Local_Client_Port", Local_Client_Port);
	}
	public void setDistantPort(int port) {
		this.Distant_Server_Port = port;
		this.Distant_Client_Port = port+1;
		this.Ports.put("Distant_Server_Port", Distant_Server_Port);
		this.Ports.put("Distant_Client_Port", Distant_Client_Port);
	}
	public void send(String message) {
		this.clientSocket.sendMessage(message);
	}
	public ArrayList<String> recv() {
		return this.ServerThread.getMessages();
	}
	
	public int getLocalClientPort() {
		return this.Local_Client_Port;
	}
	public int getLocalServerPort() {
		return this.Local_Server_Port;
	}
	public int getDistantPort() {
		return this.Distant_Server_Port;
	}
}
