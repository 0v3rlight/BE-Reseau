package ServerSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import ClientSocket.MICDatagram;

public class MICServerThread extends Thread{
	
	public DatagramSocket ServerSocket;
	public DatagramSocket ClientSocket;
	private byte[] buffer = new byte[1024];
	public MICServerThreadAnswer serverThreadAnswer;
	public ArrayList<String> Messages;
	public int Local_Port;
	public int Distant_Port;
	public InetAddress Distant_IP_address;
	public boolean run;
	
	public MICServerThread(String Distant_IP_address) {
		
		try {
			this.ServerSocket = new DatagramSocket();
			this.ClientSocket = new DatagramSocket();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.Distant_IP_address = InetAddress.getByName(Distant_IP_address);
			this.serverThreadAnswer = new MICServerThreadAnswer(this.Distant_IP_address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		this.serverThreadAnswer = new MICServerThreadAnswer();
		this.serverThreadAnswer.start();
		System.out.println("ServerThread créé");
	}
	
	public MICServerThread(String Distant_IP_address, Map<String, Integer> Ports ) {
		/*
		this.Distant_Port = Distant_Port;
		this.Local_Port = Local_Port;
		*/
		
		this.Messages = new ArrayList<String>();
		try {
			this.ServerSocket = new DatagramSocket(Ports.get("Local_Server_Port"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			this.Distant_IP_address = InetAddress.getByName(Distant_IP_address);
			this.serverThreadAnswer = new MICServerThreadAnswer(this.Distant_IP_address, Ports);
			this.serverThreadAnswer.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("ServerThread cr��");
	}
	
	
	public void run() {
		this.run = true;
		while(run) {
			System.out.println("ServerThread running");
			try {
				ServerSocket.setSoTimeout(100000);
			} catch (SocketException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			DatagramPacket messageDatagram = new DatagramPacket(buffer, buffer.length);
			try {
				ServerSocket.receive(messageDatagram);
				MICDatagram message = new MICDatagram(messageDatagram); 
				System.out.println("ServerThread : message recieved");
				
				System.out.println(new String(message.getData()));
				Messages.add(buffer.toString());
				this.serverThreadAnswer.repondre(message);
				
			}catch (SocketTimeoutException e) {
				ServerSocket.close();
				this.run = false;				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	public ArrayList<String> getMessages() {
		ArrayList<String> liste = new ArrayList<String>();
		while (Messages.size()>0) {
			liste.add(Messages.remove(0));
		}
		return Messages;
		
	}

}
