package Main;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class test2 extends Thread {
	
	public int integer;
	private byte[] buf = new byte[256];
	private DatagramSocket socket;

	public test2() {
		integer = 1;
		
	}
	public void run() {
		
		try {
			socket = new DatagramSocket(4445);
			socket.setSoTimeout(3000);
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		recieve();
		System.out.println("started recieve method");
		
	}
	
	public void recieve() {
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		try {
		System.out.println("starting to recieve");
		socket.receive(packet);
		}catch (SocketTimeoutException e) {
			System.out.println("timeout");
		} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
		
	}
	
	public void print() {
		System.out.println("print working");
	}
	public void un() {
		this.integer = 1;
	}

}
