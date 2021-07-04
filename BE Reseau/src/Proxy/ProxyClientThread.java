package Proxy;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Map;

import ClientSocket.MICDatagram;

public class ProxyClientThread extends Thread  {
	
	private Map<String, Integer> Ports;
	private String IPServer;
	private String IPClient;
	public DatagramSocket ClientSocket;
	
	
	public ProxyClientThread(String IPServer, String IPClient, Map<String, Integer> Ports) {
		this.Ports = Ports;
		this.IPServer = IPServer;
		this.IPClient = IPClient;
		
		
		try {
			this.ClientSocket = new DatagramSocket(this.Ports.get("Local_Client_Port"));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	
	
	public void run() {
		
	}
	
	public void send(MICDatagram datagram) {
		//on construit le datagramme
		System.out.println("ProxyClientThread : envoi du message");
		
		//on envoie le datagramme
		try {
			ClientSocket.send(datagram.getDatagramPacket());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("ClientThread: message:"+ Packet);
	}

}
