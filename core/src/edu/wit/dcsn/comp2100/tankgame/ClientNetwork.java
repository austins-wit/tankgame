package edu.wit.dcsn.comp2100.tankgame;

import java.io.IOException;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;

public class ClientNetwork {
	
	static final int TG_START = 0;
	static final int TG_MOVE = 1;
	static final int TG_FIRE = 2;
	static final int TG_HIT = 3;
	
	DatagramChannel channel;
	InetAddress hostAddress;

	public ClientNetwork(String hostIp) {
		try {
			channel = DatagramChannel.open();
			channel.configureBlocking(false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			hostAddress = InetAddress.getByName(hostIp);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendStart(int time) {
		ByteBuffer bf = ByteBuffer.allocate(24);
		bf.putInt(time);
		bf.putInt(TG_START);
		try {
			channel.send(bf, new InetSocketAddress(hostAddress,55231));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ByteBuffer receive() {
		ByteBuffer bf = ByteBuffer.allocate(24);
		SocketAddress remote_address = null;
		try {
			remote_address = channel.receive(bf);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (remote_address == null) ? null : bf;
	}
	
	public void send(ByteBuffer bf) {
		try {
			channel.send(bf, new InetSocketAddress(hostAddress, 55231));
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
