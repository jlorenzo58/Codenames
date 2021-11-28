package dev.turra.codenames.client.mechanics;

import dev.turra.codenames.client.network.Client;
import dev.turra.codenames.common.Role;
import dev.turra.codenames.common.Team;

/**
 * Represents the player. Extends the {@link Client} class.
 */
public class Player extends Client {

	private String name;
	private Team team;
	private Role role;

	public Player(String name, String host, int port) {
		super(host, port);

		this.name = name;
	}

	/**
	 * @return Returns the name of the player
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return Returns the team of the player
	 */
	public Team getTeam() {
		return team;
	}

	/**
	 * @param team Sets the team of the player
	 */
	public void setTeam(Team team) {
		this.team = team;
	}

	/**
	 * @return Returns the role of the player
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role Sets the role of the player
	 */
	public void setRole(Role role) {
		this.role = role;
	}

}
