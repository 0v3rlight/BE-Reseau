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
		System.out.println("ClientThread créé");
	}
	public ClientThread(InetAddress Local_IP_address, InetAddress Distant_IP_address, int Distant_Port) {
		
		this.Local_IP_address= Local_IP_address;
		this.Distant_IP_address = Distant_IP_address;
		this.Distant_Port = Distant_Port;
		this.Local_Port = -3;			
		MessageList = new LinkedList<String>();
		System.out.println("ClientThread créé");
	}
	*/
	
	public MICclientThread(InetAddress Local_IP_address, InetAddress Distant_IP_address,  Map<String, Integer> Ports, int ErrorRate, int startingSequenceNumber) {
		
		this.Local_IP_address= Local_IP_address;
		this.Distant_IP_address = Distant_IP_address;
		this.Ports = Ports;
		
		this.ErrorRate = ErrorRate;
		this.ErrorNbr = 0;
		this.sequenceNumber = startingSequenceNumber;
		/*
		this.Distant_Port = Distant_Port;
		this.Local_Port = Local_Port;
		*/
		
		MessageList = new LinkedList<String>();
		System.out.println("ClientThread créé");
	}

	@Override
	public void run() {

		//on crée le socket qui enverra les messages
		try {
			this.ServerSocket = new DatagramSocket(this.Ports.get("Local_Server_Port"));
			this.ClientSocket = new DatagramSocket(this.Ports.get("Local_Client_Port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//on définie le temps que le serveur a pour répondre
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
		
				//et on prépare le datagramme pour enregistrer la réponse
				DatagramPacket reponse = new DatagramPacket(buffer, buffer.length);
				
				//on envoie le message
				String Message = MessageList.removeFirst();
				System.out.println(Message);
				send(Message);
				System.out.println("ClientThread : message envoyé");
				//on attends d'avoir la réponse
				try {
					ServerSocket.receive(reponse);
					System.out.println("ClientThread : réponse recue:" + new String(reponse.getData()));
					if(ErrorNbr>0) {
						ErrorNbr-=1;
					}
				}catch (SocketTimeoutException e) {		
					System.out.println("ClientThread : pas de réponse du server");			
					//si on a pas de réponse dans le temps imparti:					
					//soit on considère que l'erreur est acceptable
					if(ErrorNbr<ErrorRate) {
						//dans ce cas, on prend en compte la nouvelle erreur dans le taux 
						ErrorNbr+=1;
						//mais, comme si on avais eu une réponse, on augmente le numseq
							//conséquence: le server acceptera le prochain packet
						sequenceNumber = (sequenceNumber+1)%2;
					}
					//soit on renvoie
					else {
						MessageList.addFirst(Message);
						System.out.println("ClientThread : on renvoie le message");
						//ici on n'augmente pas le numseq parce que:
							//si le serveur n'a pas recu le précédent msg, il recevra un msg avec le bon numseq
							//si le serveur a recu le précédent msg mais le client n'a pas recu le ACK, le serveur ne prendra pas en compte 2fois le même message
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
		System.out.println(datagram.getStringPacket());
		
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
