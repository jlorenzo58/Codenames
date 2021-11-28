package dev.turra.codenames.client.mechanics;

import dev.turra.codenames.client.gui.GameUI;
import dev.turra.codenames.client.network.IPacketListener;
import dev.turra.codenames.common.CardColor;
import dev.turra.codenames.common.Role;
import dev.turra.codenames.common.Team;
import dev.turra.codenames.common.network.Packet;
import dev.turra.codenames.common.network.cb.*;
import dev.turra.codenames.common.network.sb.PacketServerHint;
import dev.turra.codenames.common.network.sb.PacketServerLogin;
import dev.turra.codenames.common.network.sb.PacketServerTeamRole;

import javax.swing.*;
import java.io.IOException;

/**
 * Represents the game manager. Everything that is received from the server is handled here.
 */
public class GameManager implements IPacketListener {

	private GameUI ui;
	private Player player;

	public GameManager() {

	}

	/**
	 * Attempts to connect to the server
	 * @param username The username of the player
	 * @param ip The ip of the server
	 * @param port The port of the server
	 * @throws IOException Thrown when the connection fails
	 */
	public void connectionAttempt(String username, String ip, int port) throws IOException {
		System.out.println("Attempting to connect...");
		Player client = new Player(username, ip, port);
		client.connect();
		client.sendPacket(new PacketServerLogin(username));
		this.player = client;
	}

	/**
	 * Assigns the ui to {@link GameUI} provided. Called when GameUI is created
	 * @param ui The {@link GameUI} to assign
	 */
	public void assignGameUI(GameUI ui){
		this.ui = ui;
	}

	/**
	 * Join a team and a role and lets the server know
	 * @param team The team to join
	 * @param role The role to join
	 */
	public void joinTeam(Team team, Role role){
		player.setTeam(team);
		player.setRole(role);
		player.sendPacket(new PacketServerTeamRole(team, role));
		ui.hideTeamButtons();
		ui.showRoleUI(role);
	}

	/**
	 *
	 * @return Returns the {@link Player} of the client
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Handles the received packets. Called when any {@link Packet} is received
	 * @param p Packet that was received
	 */
	@Override
	public void received(Packet p) {
		if (p instanceof PacketClientUpdatePlayers packet){
			JTextArea label = packet.getTeam() == Team.RED ? packet.getRole() == Role.OPERATIVE ? ui.redOperatives : ui.redSpymasters : packet.getRole() == Role.OPERATIVE ? ui.blueOperatives : ui.blueSpymasters;
			label.setText(packet.getPlayers());
		} else if (p instanceof PacketClientCard packet){
			ui.updateCard(packet.getX(), packet.getY(), packet.getWord(), packet.getColor() == null ? CardColor.EMPTY.getColor() : packet.getColor().getColor());
		} else if (p instanceof PacketClientCardReveal packet){
	        ui.revealCard(packet.getX(), packet.getY(), packet.getColor().getColor());
		} else if (p instanceof PacketClientTeamTurn packet){
			ui.clearHint();
			ui.setAnnouncerText(packet.getTeam().getName() + " team is giving a hint...", packet.getTeam().getColor().getColor());
		} else if (p instanceof PacketClientHint packet){
			ui.setHint(packet.getHint(), packet.getWordAmount());
			ui.setAnnouncerText(packet.getTeam().getName() + " team is guessing...", packet.getTeam().getColor().getColor());
		} else if (p instanceof PacketClientAnnouncer packet){
			ui.setAnnouncerText(packet.getMessage(), packet.getColor());
		} else if (p instanceof PacketClientUpdateScore packet){
			if(packet.getTeam() == Team.RED){
				ui.redScore.setText(packet.getScore() + "");
			}else{
				ui.blueScore.setText(packet.getScore() + "");
			}
		}
	}

	/**
	 * Makes sure the wordAmountString is an integer, sends a hint and a number to the server, and runs a runnable on success
	 * @param hint The hint to send
	 * @param wordAmountString The amount of words associated with the hint
	 * @param onSuccess The runnable to run on success
	 */
	// If wordAmountString is an integer, then onSuccess will run, which will clear the text fields.
	public void sendHint(String hint, String wordAmountString, Runnable onSuccess){
		int wordAmount;
		try {
			wordAmount = Integer.parseInt(wordAmountString);
		}catch (NumberFormatException e){
			return;
		}
		player.sendPacket(new PacketServerHint(hint, wordAmount));
		onSuccess.run();
	}

}
