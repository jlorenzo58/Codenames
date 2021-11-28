package dev.turra.codenames.client;


import dev.turra.codenames.client.gui.GameUI;
import dev.turra.codenames.client.mechanics.GameManager;

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static GameManager manager;

	public static void main(String[] args) {

		manager = new GameManager();
		GameUI ui = new GameUI(manager);
	}
}
