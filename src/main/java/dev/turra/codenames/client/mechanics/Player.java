package dev.turra.codenames.client.mechanics;

import dev.turra.codenames.client.network.Client;
import dev.turra.codenames.common.Role;
import dev.turra.codenames.common.Team;

public class Player extends Client {

	private String name;
	private Team team;
	private Role role;

	public Player(String name, String host, int port) {
		super(host, port);

		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
