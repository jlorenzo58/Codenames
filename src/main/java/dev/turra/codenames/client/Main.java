package dev.turra.codenames.client;


import dev.turra.codenames.client.gui.GameUI;
import dev.turra.codenames.client.mechanics.GameManager;
import dev.turra.codenames.client.network.Client;
import dev.turra.codenames.common.network.sb.PacketServerChat;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static GameManager manager;

	public static void main(String[] args) {

		manager = new GameManager();
		GameUI ui = new GameUI(manager);

		Scanner scanner = new Scanner(System.in);
		while (true){
			if(manager.getPlayer() == null)
				continue;

			String message = scanner.nextLine();
			PacketServerChat packetOutChat = new PacketServerChat(message);
			manager.getPlayer().sendPacket(packetOutChat);
		}
	}
}
