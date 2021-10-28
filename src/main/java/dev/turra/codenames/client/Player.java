package dev.turra.codenames.client;

import dev.turra.codenames.client.network.Client;

public class Player extends Client {

	private Team team;
	private Role role;

	public Player(String host, int port) {
		super(host, port);
	}

	public enum Team{
		RED, BLUE;
	}
	// this is a comment

	public enum Role{
		OPERATIVE, SPYMASTER;
	}

}
