package ClientSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.Map;

import ServerSocket.AckDatagram;

public class MICclientThread extends Thread{
	
	private InetAddress Local_IP_address;
	private InetAddress Distant_IP_address;
	private int Distant_Port;
	private int Local_Port;
	private Map<String, Integer> Ports;
	private LinkedList<String> MessageList;
	private DatagramSocket ServerSocket;
	private DatagramSocket ClientSocket;
	private byte[] buffer = new byte[256];
	private boolean erreurAcceptable;
	
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
		
				//et on pr�pare le datagramme pour enregistrer la r�ponse
				DatagramPacket reponse = new DatagramPacket(buffer, buffer.length);
				
				//on envoie le message
				String Message = MessageList.removeFirst();
				System.out.println(Message);
				send(Message);
				System.out.println("ClientThread : message envoy�");
				//on attends d'avoir la r�ponse
				int recievedSequenceNumber = -1;
				try {
					//tant qu'on ne recoit pas le ACK du message envoy�:
					while(recievedSequenceNumber != sequenceNumber) {
						//on continue � attendre un ACK
						ServerSocket.receive(reponse);
						System.out.println("ClientThread : r�ponse recue:" + new String(reponse.getData()));
						AckDatagram MICReponse = new AckDatagram(reponse); 
						recievedSequenceNumber = MICReponse.getSequenceNumber();
						//ajuster le taux d'erreur en comptant les ACK recus tardivement comme valides
						// cela �vite de se retrouver dans une situation o� on envoie 2 fois, voi plus, le m�me packet apr�s chaque timeout, alors que celui ci met juste syst�matiquement trop de temps � arriver
						// En ajustant on se retrouve donc avec une communication qui marche, mais lentement
						// la ligne suivante est donc � enlever au cas o� l'argument serait faux:
						if(ErrorNbr>0) {ErrorNbr-=1;}
					}		
					//quand on recoit le bon ACK
					if(ErrorNbr>0) {
						ErrorNbr-=1;
					}
					this.sequenceNumber = (this.sequenceNumber+1)%2;	
					
				}catch (SocketTimeoutException e) {		
					System.out.println("ClientThread : pas de r�ponse du server");			
					//si on a pas de r�ponse dans le temps imparti:					
					//soit on consid�re que l'erreur est acceptable
					if(ErrorNbr<ErrorRate) {
						//dans ce cas, on prend en compte la nouvelle erreur dans le taux 
						ErrorNbr+=1;
						//mais, comme si on avais eu une r�ponse, on augmente le numseq
							//cons�quence: le server acceptera le prochain packet
						sequenceNumber = (sequenceNumber+1)%2;
					}
					//soit on renvoie
					else {
						MessageList.addFirst(Message);
						System.out.println("ClientThread : on renvoie le message");
						//ici on n'augmente pas le numseq parce que:
							//si le serveur n'a pas recu le pr�c�dent msg, il recevra un msg avec le bon numseq
							//si le serveur a recu le pr�c�dent msg mais le client n'a pas recu le ACK, le serveur ne prendra pas en compte 2fois le m�me message
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
		MICDatagram MICdatagram = new MICDatagram(this.sequenceNumber, Packet, this.Local_IP_address, this.Distant_IP_address, this.Ports.get("Distant_Server_Port"));
		
		//on envoie le datagramme
		try {
			ClientSocket.send(MICdatagram.getDatagramPacket());
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
