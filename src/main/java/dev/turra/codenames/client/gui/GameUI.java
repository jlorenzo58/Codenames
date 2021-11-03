package dev.turra.codenames.client.gui;

import dev.turra.codenames.client.mechanics.GameManager;
import dev.turra.codenames.client.mechanics.Player;
import dev.turra.codenames.common.Role;
import dev.turra.codenames.common.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class GameUI extends JFrame{
	private GameManager manager;

	// Windows
	private JPanel gamePanel;
	private JPanel loginPanel;

	// Login view
	private JButton connectButton;
	private JTextField inputUsername;
	private JTextField inputIP;
	private JTextField inputPort;
	private JLabel errorText;

	// Game view
	private JTable board;
	private JTextField hint;
	private JTextField hintWordAmount;
	private JButton submitButton;
	public JTextArea blueOperatives;
	public JTextArea blueSpymasters;
	public JTextArea redOperatives;
	public JTextArea redSpymasters;
	private JButton joinBlueOperativesButton;
	private JButton joinBlueSpymastersButton;
	private JButton joinRedOperativesButton;
	private JButton joinRedSpymastersButton;

	private CardLayout layout = new CardLayout();
	private JPanel mainPanel = new JPanel(layout);

	public GameUI(GameManager gameManager) {
		manager = gameManager;
		manager.assignGameUI(this);

		mainPanel.add(loginPanel, "login");
		mainPanel.add(gamePanel, "game");
		add(mainPanel);

		setTitle("Codenames");
		setSize(1280, 720);
		setVisible(true);
		loginWindow();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private void loginWindow(){
		layout.show(mainPanel, "login");
		connectButton.addActionListener(e -> {
			try {
				manager.connectionAttempt(inputUsername.getText(), inputIP.getText(), Integer.parseInt(inputPort.getText()));
				gameWindow();
				errorText.setText(" ");
			}catch (IOException | NumberFormatException ex){
				errorText.setText("Could not connect to the server.");
				ex.printStackTrace();
			}
		});
	}

	private void gameWindow(){
		layout.show(mainPanel, "game");

		joinBlueOperativesButton.addActionListener(e -> manager.joinTeam(Team.BLUE, Role.OPERATIVE));
		joinBlueSpymastersButton.addActionListener(e -> manager.joinTeam(Team.BLUE, Role.SPYMASTER));
		joinRedOperativesButton.addActionListener(e -> manager.joinTeam(Team.RED, Role.OPERATIVE));
		joinRedSpymastersButton.addActionListener(e -> manager.joinTeam(Team.RED, Role.SPYMASTER));
		submitButton.addActionListener(e -> {

		});
	}

	public static void main(String[] args) {
		GameUI ui = new GameUI(new GameManager());
		ui.gameWindow();
	}

}
