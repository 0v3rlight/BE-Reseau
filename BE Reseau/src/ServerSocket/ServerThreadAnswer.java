package ServerSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Map;

public class ServerThreadAnswer extends Thread {

	public DatagramSocket ClientSocket;
	public int Distant_Port;
	public InetAddress Distant_IP_address;
	private byte[] buffer;
	private int Local_Port;
	public Map<String, Integer> Ports;
	
	public ServerThreadAnswer() {
		try {
			this.ClientSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ServerThreadAnswer(InetAddress Distant_IP_address) {
		this.Distant_IP_address = Distant_IP_address;
		try {
			this.ClientSocket = new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ServerThreadAnswer(InetAddress Distant_IP_address, Map<String, Integer> Ports) {
		/*
		this.Local_Port = Local_Port;
		this.Distant_Port = Distant_Port;
		*/
		this.Distant_IP_address = Distant_IP_address;
		this.Ports = Ports;
		try {
			this.ClientSocket = new DatagramSocket(this.Ports.get("Local_Client_Port"));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void run() {
		
	}
	
	public void repondre(DatagramPacket datagram) {
		InetAddress address;
		String message;
		String reponseTxt;
		String strDatagram = new String(datagram.getData(), datagram.getOffset(),datagram.getLength());
		String[] temp = strDatagram.split("/",1);
		
		try {
			reponseTxt = "ACK";
			DatagramPacket reponse = new DatagramPacket(reponseTxt.getBytes(), reponseTxt.getBytes().length, this.Distant_IP_address, this.Ports.get("Distant_Server_Port"));
			ClientSocket.send(reponse);
			System.out.println("ip distante: " + this.Distant_IP_address + "and distant port : " + this.Ports.get("Distant_Server_Port"));
			System.out.println("ServerThreadAnswer : answer sent");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
