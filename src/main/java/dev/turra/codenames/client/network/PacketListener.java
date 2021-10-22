package dev.turra.codenames.client.network;

import dev.turra.codenames.common.network.cb.PacketClientChat;

public class PacketListener {

	public void received(Object p) {
		if (p instanceof PacketClientChat){
			PacketClientChat packet = (PacketClientChat) p;
			System.out.println(packet.sender + ": " + packet.message);
		}
	}

}
