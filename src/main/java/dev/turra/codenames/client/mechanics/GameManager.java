package dev.turra.codenames.client.mechanics;

import dev.turra.codenames.client.gui.GameUI;
import dev.turra.codenames.client.network.IPacketListener;
import dev.turra.codenames.common.CardColor;
import dev.turra.codenames.common.Role;
import dev.turra.codenames.common.Team;
import dev.turra.codenames.common.network.Packet;
import dev.turra.codenames.common.network.cb.*;
import dev.turra.codenames.common.network.sb.PacketServerLogin;
import dev.turra.codenames.common.network.sb.PacketServerTeamRole;

import javax.swing.*;
import java.io.IOException;

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
		ui.hideTeamButtons();
		ui.showRoleUI(role);
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void received(Packet p) {
		if (p instanceof PacketClientChat packet){
			System.out.println(packet.getSender() + ": " + packet.getMessage());
		} else if (p instanceof PacketClientUpdatePlayers packet){
			JTextArea label = packet.getTeam() == Team.RED ? packet.getRole() == Role.OPERATIVE ? ui.redOperatives : ui.redSpymasters : packet.getRole() == Role.OPERATIVE ? ui.blueOperatives : ui.blueSpymasters;
			label.setText(packet.getPlayers());
		} else if (p instanceof PacketClientCard packet){
			ui.updateCard(packet.getX(), packet.getY(), packet.getWord(), packet.getColor() == null ? CardColor.EMPTY.getColor() : packet.getColor().getColor());
		} else if (p instanceof PacketClientCardReveal packet){
	        ui.revealCard(packet.getX(), packet.getY(), packet.getColor().getColor());
		} else if (p instanceof PacketClientTeamTurn packet){
			ui.setTurn(packet.getTeam());
			ui.clearHint();
		}
	}
}
