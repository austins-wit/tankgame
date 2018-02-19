package edu.wit.dcsn.comp2100.tankgame;

import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class HostNetwork {

	static final int TG_START = 0;
	static final int TG_MOVE = 1;
	static final int TG_FIRE = 2;
	static final int TG_HIT = 3;
	
	DatagramChannel channel;
//	InetAddress clientAddress;
	InetSocketAddress clientAddress;
	
	public HostNetwork() {
		try {
			channel = DatagramChannel.open();
			channel.configureBlocking(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			channel.socket().bind(new InetSocketAddress(55231));
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getMyIpAddress() {
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean receiveStart() {
		ByteBuffer bf = ByteBuffer.allocate(24);
		InetSocketAddress remote_address = null;
		try {
			remote_address = (InetSocketAddress)channel.receive(bf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (remote_address != null) {
//			clientAddress = remote_address.getAddress();
			clientAddress = remote_address;
		}	
		return remote_address != null;
	}
	
	public ByteBuffer receive() {
		ByteBuffer bf = ByteBuffer.allocate(24);
		InetSocketAddress remote_address = null;
		try {
			remote_address = (InetSocketAddress)channel.receive(bf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean result = remote_address == null;
		if (result)
			return null;
		return bf;
//		return (remote_address == null) ? null : bf;
	}
	
	public void send(ByteBuffer bf) {
		try {
			channel.send(bf, clientAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() {
		try {
			channel.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
