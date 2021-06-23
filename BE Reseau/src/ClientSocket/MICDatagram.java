package ClientSocket;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MICDatagram{

    private String data;
    private int seq;
    private DatagramPacket datagram;
    private String stringPacket;
    private byte[] bytePacket;
    
    private String Distant_IP_address;
    private InetAddress Distant_IP_address_INET;
    private String Local_IP_address;
    private int Distant_Server_Port;

    
    public MICDatagram(DatagramPacket datagramRecieved){

		String strDatagram = new String(datagram.getData(), datagram.getOffset(),datagram.getLength());
		String[] temp = strDatagram.split("/",2);
		this.seq = Integer.valueOf(temp[0]);
		this.Distant_IP_address = temp[1];
		try {
			this.Distant_IP_address_INET = InetAddress.getByName(Distant_IP_address);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.data = temp[2];
    }
    
    public MICDatagram(int seq, String data, InetAddress local_IP, InetAddress distant_IP, int port){
    	this.seq = seq;
    	this.data = data;
    	this.Local_IP_address = local_IP.toString();
    	this.Distant_IP_address = distant_IP.toString();
    	this.Distant_IP_address_INET = distant_IP;
    	this.Distant_Server_Port = port;
    	this.stringPacket = String.valueOf(seq)
    						.concat("/")
    						.concat(this.Local_IP_address)
    						.concat("/")
    						.concat(data);
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

}