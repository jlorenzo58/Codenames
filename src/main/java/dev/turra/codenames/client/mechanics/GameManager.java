package dev.turra.codenames.client.mechanics;

import dev.turra.codenames.client.gui.GameUI;
import dev.turra.codenames.client.network.IPacketListener;
import dev.turra.codenames.common.Role;
import dev.turra.codenames.common.Team;
import dev.turra.codenames.common.network.cb.PacketClientChat;
import dev.turra.codenames.common.network.cb.PacketClientUpdatePlayers;
import dev.turra.codenames.common.network.sb.PacketServerLogin;
import dev.turra.codenames.common.network.sb.PacketServerTeamRole;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;

public class GameManager implements IPacketListener {

	private GameUI ui;
	private Player player;

	public GameManager() {

	}

	public void connectionAttempt(String username, String ip, int port) throws IOException {
		System.out.println("Attempting to connect...");
		Player client = new Player(username, ip, port);
		client.connect();
		client.sendPacket(new PacketServerLogin(username));
		this.player = client;
	}

	public void assignGameUI(GameUI ui){
		this.ui = ui;
	}

	public void joinTeam(Team team, Role role){
		player.setTeam(team);
		player.setRole(role);
		player.sendPacket(new PacketServerTeamRole(team, role));
	}

	public Player getPlayer() {
		return player;
	}

	// Handle incoming packets
	@Override
	public void received(Object p) {
		if (p instanceof PacketClientChat packet){
			System.out.println(packet.sender + ": " + packet.message);
		} else if (p instanceof PacketClientUpdatePlayers packet){
			// Get the label that holds players from packet's team and role
			JTextArea label = packet.team == Team.RED ? packet.role == Role.OPERATIVE ? ui.redOperatives : ui.redSpymasters : packet.role == Role.OPERATIVE ? ui.blueOperatives : ui.blueSpymasters;
			label.setText(packet.players);
		}
	}
}
