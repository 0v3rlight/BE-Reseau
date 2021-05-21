package ClientSocket;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

public class clientSocket {

	public InetAddress Local_IP_address;
	public InetAddress Distant_IP_address;
	public int Distant_Port;
	public ClientThread ClientThread;
	private int Local_Port;
	public Map<String, Integer> Ports;
	public int ErrorRate;

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

	public clientSocket(String Distant_IP_address, Map<String, Integer> Ports,  int ErrorRate) {
		try {
			this.Local_IP_address = InetAddress.getLocalHost();
			this.Ports = Ports;
			
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
		this.ClientThread = new ClientThread(this.Local_IP_address, this.Distant_IP_address, this.Ports, this.ErrorRate);
		this.ClientThread.start();
		System.out.println("clientSocket créé");
	}
	
	
	// ici on ajoute un message à envoyer
	public void sendMessage(String Message) {
		String temp = new String();
		int max_char = 511;
		
		while(Message.length()>=max_char) {
			temp = Message.substring(0,max_char);
			ClientThread.addMessage(temp);
			Message = Message.substring(max_char);
		}
		ClientThread.addMessage(Message);
		
	}

}
