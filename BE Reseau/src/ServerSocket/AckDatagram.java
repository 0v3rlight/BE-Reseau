package ServerSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class AckDatagram{

    private int seq;
    private DatagramPacket datagram;
    private String stringPacket;
    private byte[] bytePacket;
    
    private String Distant_IP_address;
    private InetAddress Distant_IP_address_INET;
    private int Distant_Server_Port;

    
    public AckDatagram(DatagramPacket datagramRecieved){

		String strDatagram = new String(datagramRecieved.getData(), datagramRecieved.getOffset(),datagramRecieved.getLength());
		String[] temp = strDatagram.split("/",2);
		this.seq = Integer.valueOf(temp[0]);
		this.Distant_IP_address = temp[1];
    }
    
    public AckDatagram(int seq, InetAddress distant_IP, int port){
    	this.seq = seq;
    	this.Distant_IP_address = distant_IP.toString();
    	this.Distant_IP_address_INET = distant_IP;
    	this.Distant_Server_Port = port;
    	this.stringPacket = String.valueOf(seq)
    						.concat("/")
    						.concat("ACK");
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
    
    public int getSequenceNumber(){
    	return this.seq;
    }

}