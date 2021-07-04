package ClientSocket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class MICclientSocket {

	private InetAddress Local_IP_address;
	private InetAddress Distant_IP_address;
	private int Distant_Port;
	private MICclientThread MICclientThread;
	private int Local_Port;
	private Map<String, Integer> Ports;
	private int ErrorRate;
	private int startingSequenceNumber;

	/*
	public clientSocket(String Distant_IP_address) {
		try {
			this.Local_IP_address = InetAddress.getLocalHost();
			// à vérifier mais il faut probablement faire ca:
			//this.Local_IP_address = InetAddress(InetAddress.getHostAddress().getBytes());
			this.Distant_IP_address = InetAddress.getByName(Distant_IP_address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.ClientThread = new ClientThread(this.Local_IP_address, this.Distant_IP_address);
		this.ClientThread.start();
		System.out.println("clientSocket créé");
	}
	*/

	public MICclientSocket(String Distant_IP_address, Map<String, Integer> Ports,  int ErrorRate, int startingSequenceNumber) {
		try {
			this.Local_IP_address = InetAddress.getLocalHost();
			this.Ports = Ports;
			this.startingSequenceNumber = startingSequenceNumber;
			
			/*
			this.Distant_Port = Distant_Port;
			this.Local_Port = Local_Port;
			*/
			
			// à vérifier mais il faut probablement faire ca:
			//this.Local_IP_address = InetAddress(InetAddress.getHostAddress().getBytes());
			this.Distant_IP_address = InetAddress.getByName(Distant_IP_address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.MICclientThread = new MICclientThread(this.Local_IP_address, this.Distant_IP_address, this.Ports, this.ErrorRate, this.startingSequenceNumber);
		this.MICclientThread.start();
		System.out.println("clientSocket créé");
	}
	
	
	// ici on ajoute un message à envoyer
	public void sendMessage(String Message) {
		String temp = new String();
		int max_char = 511;
		
		while(Message.length()>=max_char) {
			temp = Message.substring(0,max_char);
			MICclientThread.addMessage(temp);
			Message = Message.substring(max_char);
		}
		MICclientThread.addMessage(Message);
		
	}

}
