package dev.turra.codenames.client;


import dev.turra.codenames.client.network.Client;
import dev.turra.codenames.common.network.sb.PacketServerChat;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {

		Client client = new Client("localhost",5252);
		client.connect();

//		AddConnectionPacket packet = new AddConnectionPacket();
//		client.sendObject(packet);


		Scanner scanner = new Scanner(System.in);
		while (true){
			String cmd = scanner.nextLine();
			PacketServerChat packetOutChat = new PacketServerChat(cmd);
			client.sendObject(packetOutChat);
		}
	}
}
