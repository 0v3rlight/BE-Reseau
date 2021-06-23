package ClientSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Map;

public class MICclientThread extends Thread{
	
	public InetAddress Local_IP_address;
	public InetAddress Distant_IP_address;
	public int Distant_Port;
	public int Local_Port;
	public Map<String, Integer> Ports;
	public LinkedList<String> MessageList;
	public DatagramSocket ServerSocket;
	public DatagramSocket ClientSocket;
	private byte[] buffer = new byte[256];
	boolean erreurAcceptable;
	
	private int ErrorRate;
	private int ErrorNbr;
	private int sequenceNumber;
	
	/*
	public ClientThread(InetAddress Local_IP_address, InetAddress Distant_IP_address) {
		
		this.Local_IP_address= Local_IP_address;
		this.Distant_IP_address = Distant_IP_address;	
		this.Local_Port = -3;			
		MessageList = new LinkedList<String>();
		System.out.println("ClientThread cr��");
	}
	public ClientThread(InetAddress Local_IP_address, InetAddress Distant_IP_address, int Distant_Port) {
		
		this.Local_IP_address= Local_IP_address;
		this.Distant_IP_address = Distant_IP_address;
		this.Distant_Port = Distant_Port;
		this.Local_Port = -3;			
		MessageList = new LinkedList<String>();
		System.out.println("ClientThread cr��");
	}
	*/
	
	public MICclientThread(InetAddress Local_IP_address, InetAddress Distant_IP_address,  Map<String, Integer> Ports, int ErrorRate) {
		
		this.Local_IP_address= Local_IP_address;
		this.Distant_IP_address = Distant_IP_address;
		this.Ports = Ports;
		this.ErrorRate = ErrorRate;
		this.ErrorNbr = 0;
		
		/*
		this.Distant_Port = Distant_Port;
		this.Local_Port = Local_Port;
		*/
		
		MessageList = new LinkedList<String>();
		System.out.println("ClientThread cr��");
	}

	@Override
	public void run() {

		//on cr�e le socket qui enverra les messages
		try {
			this.ServerSocket = new DatagramSocket(this.Ports.get("Local_Server_Port"));
			this.ClientSocket = new DatagramSocket(this.Ports.get("Local_Client_Port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//on d�finie le temps que le serveur a pour r�pondre
		try {
			ServerSocket.setSoTimeout(3000);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		while(true) {
			System.out.println("ClientThread running");
			//si la liste contiens un message, on l'envoie
			if(MessageList.size() > 0) {				
		
				//et on pr�pare le datagramme pour la r�ponse
				DatagramPacket reponse = new DatagramPacket(buffer, buffer.length);
				
				//on envoie le message
				String Message = MessageList.removeFirst();
				System.out.println(Message);
				send(Message);
				System.out.println("ClientThread : message envoy�");
				//on attends d'avoir la r�ponse
				try {
					ServerSocket.receive(reponse);
					System.out.println("ClientThread : r�ponse recue:" + new String(reponse.getData()));
					if(ErrorNbr>0) {
						ErrorNbr-=1;
					}
				}catch (SocketTimeoutException e) {		
					System.out.println("ClientThread : pas de r�ponse du server");			
					//si on a pas de r�ponse dans le temps imparti:					
					//soit on consid�re que l'erreur est acceptable
					if(ErrorNbr<ErrorRate) {
						ErrorNbr+=1;
					}
					//soit on renvoie
					else {
						MessageList.addFirst(Message);
						System.out.println("ClientThread : on renvoie le message");
					}
				}catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	
	}
	
	public void send(String Packet) {
		//on construit le datagramme
		System.out.println("ClientThread : construction du message");
		MICDatagram datagram = new MICDatagram(this.sequenceNumber, Packet, this.Local_IP_address, this.Distant_IP_address, this.Ports.get("Distant_Server_Port"));
		
		//on envoie le datagramme
		try {
			ClientSocket.send(datagram.getDatagramPacket());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("ClientThread: message:"+ Packet);
	}
	
	public void addMessage(String Message){
		MessageList.add(Message);
		
	}

}
