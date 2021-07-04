package ClientSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class MICDatagram{

    private String data;
    private int sequenceNumber;
    private DatagramPacket datagram;
    private String stringPacket;
    private byte[] bytePacket;
    
    private String Distant_IP_address;
    private InetAddress Distant_IP_address_INET;
    private String Local_IP_address;
    private int Distant_Server_Port;

    
    public MICDatagram(DatagramPacket datagramRecieved){

		String strDatagram = new String(datagramRecieved.getData(), datagramRecieved.getOffset(),datagramRecieved.getLength());
		String[] temp = strDatagram.split("/",4);
		this.sequenceNumber = Integer.valueOf(temp[0]);
		this.Distant_IP_address = temp[2];
		try {
			this.Distant_IP_address_INET = InetAddress.getByName(Distant_IP_address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.data = temp[3];
    }
    
    
    public MICDatagram(byte[] buffer, int length) {
		String s = new String(buffer, StandardCharsets.UTF_8);
		String[] temp = s.split("/",4);
		this.sequenceNumber = Integer.valueOf(temp[0]);
		this.Distant_IP_address = temp[2];
		try {
			this.Distant_IP_address_INET = InetAddress.getByName(Distant_IP_address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.data = temp[3];
		
	}
    
    
    public MICDatagram(int seq, String data, InetAddress local_IP, InetAddress distant_IP, int port){
    	this.sequenceNumber = seq;
    	this.data = data;
    	this.Local_IP_address = local_IP.toString();
    	this.Distant_IP_address = distant_IP.toString();
    	this.Distant_IP_address_INET = distant_IP;
    	this.Distant_Server_Port = port;
    	this.stringPacket = String.valueOf(this.sequenceNumber)
    						.concat("/")
    						.concat(this.Local_IP_address)
    						.concat("/")
    						.concat(this.data);
    	this.bytePacket = this.stringPacket.getBytes();
    	this.datagram = new DatagramPacket(bytePacket, bytePacket.length,
    										this.Distant_IP_address_INET,
    										this.Distant_Server_Port);
    }


	public byte[] getByte() {
    	return this.bytePacket;    	
    }
    
    public DatagramPacket getDatagramPacket(){
    	return this.datagram;
    }
    
    public int getSequenceNumber() {
    	return this.sequenceNumber;
    }


	public String getData() {
		return this.data;
	}
	
	public String getStringPacket() {
		return this.stringPacket;
		
	}

}