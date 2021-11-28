package dev.turra.codenames.client.gui;

import dev.turra.codenames.client.mechanics.GameManager;
import dev.turra.codenames.common.Role;
import dev.turra.codenames.common.Team;
import dev.turra.codenames.common.network.cb.PacketClientCardReveal;
import dev.turra.codenames.common.network.cb.PacketClientHint;
import dev.turra.codenames.common.network.sb.PacketServerCardClick;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Represents the entire game UI.
 */
public class GameUI extends JFrame {
	private GameManager manager;

	private CardLayout layout = new CardLayout();
	private JPanel mainPanel = new JPanel(layout);

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
	private JPanel spymasterUI;
	private JTextField hint;
	private JTextField hintWordAmount;
	private JPanel operativeUI;
	private JLabel givenHint;
	private JButton submitButton;
	public JTextArea blueOperatives;
	public JTextArea blueSpymasters;
	public JTextArea redOperatives;
	public JTextArea redSpymasters;
	private JButton joinBlueOperativesButton;
	private JButton joinBlueSpymastersButton;
	private JButton joinRedOperativesButton;
	private JButton joinRedSpymastersButton;
	public JPanel boardPanel;
	private JLabel announcerText;
	public JLabel blueScore;
	public JLabel redScore;

	private CardUI[][] board = new CardUI[5][5];

	public GameUI(GameManager gameManager) {
		manager = gameManager;
		manager.assignGameUI(this);

		mainPanel.add(loginPanel, "login");
		mainPanel.add(gamePanel, "game");
		add(mainPanel);

		setTitle("Codenames");
		setSize(1280, 720);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setVisible(true);
		loginWindow();

		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

//		board.col
	}

	/**
	 * Set the current view to the login view
	 */
	private void loginWindow() {
		layout.show(mainPanel, "login");
		connectButton.addActionListener(e -> {
			try {
				manager.connectionAttempt(inputUsername.getText(), inputIP.getText(), Integer.parseInt(inputPort.getText()));
				gameWindow();
				errorText.setText(" ");
			} catch (IOException | NumberFormatException ex) {
				errorText.setText("Could not connect to the server.");
				ex.printStackTrace();
			}
		});
	}

	/**
	 * Set the current view to the game view
	 */
	private void gameWindow() {
		boardPanel.setLayout(new GridLayout(5, 5, 2, 2));
		layout.show(mainPanel, "game");

		joinBlueOperativesButton.addActionListener(e -> manager.joinTeam(Team.BLUE, Role.OPERATIVE));
		joinBlueSpymastersButton.addActionListener(e -> manager.joinTeam(Team.BLUE, Role.SPYMASTER));
		joinRedOperativesButton.addActionListener(e -> manager.joinTeam(Team.RED, Role.OPERATIVE));
		joinRedSpymastersButton.addActionListener(e -> manager.joinTeam(Team.RED, Role.SPYMASTER));

		submitButton.addActionListener(e -> manager.sendHint(hint.getText(), hintWordAmount.getText(), () -> {
			hint.setText("");
			hintWordAmount.setText("");
		}));

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				CardUI card = new CardUI(this, i, j);
				card.setWord("Word " + i + "x" + j);
				board[i][j] = card;
			}
		}
	}

	/**
	 * Hides the team and roles join buttons
	 */
	public void hideTeamButtons() {
		joinBlueOperativesButton.setVisible(false);
		joinBlueSpymastersButton.setVisible(false);
		joinRedOperativesButton.setVisible(false);
		joinRedSpymastersButton.setVisible(false);
	}

	/**
	 * Shows the appropriate UI for the player's role
	 * @param role
	 */
	public void showRoleUI(Role role) {
		if (role == Role.SPYMASTER) {
			spymasterUI.setVisible(true);
		} else {
			operativeUI.setVisible(true);
		}
	}

	/**
	 * Sets the word and color of the card at the x and y coordinates
	 * @param x The x coordinate of the card
	 * @param y The y coordinate of the card
	 * @param word The word to be displayed on the card
	 * @param color The color of the card
	 */
	public void updateCard(int x, int y, String word, Color color) {
		// debug
		System.out.println("Updating card " + x + "x" + y + " to " + word);
		board[x][y].setWord(word);
		if (color != null)
			board[x][y].getWordLabel().setForeground(color);
	}

	/**
	 * Triggered when a player clicks a card. This will send the {@link PacketServerCardClick} to the server
	 * @param x The x coordinate of the card
	 * @param y The y coordinate of the card
	 */
	public void cardClicked(int x, int y) {
		if (manager.getPlayer().getTeam() == null || manager.getPlayer().getRole() == null)
			return;

		manager.getPlayer().sendPacket(new PacketServerCardClick(x, y));
	}

	/**
	 * Triggered when the client receives {@link PacketClientCardReveal}
	 * @param x The x coordinate of the card
	 * @param y The y coordinate of the card
	 * @param color The color of the card
	 */
	public void revealCard(int x, int y, Color color) {
		board[x][y].reveal(color);
	}

	/**
	 * Triggered when the client receives {@link PacketClientHint}
	 * @param hint The hint
	 * @param wordAmount The amount of words corresponding to the hint
	 */
	public void setHint(String hint, int wordAmount) {
		givenHint.setText(hint + " " + wordAmount);
	}

	/**
	 * Clears the hint text
	 */
	public void clearHint() {
		givenHint.setText("");
	}

	/**
	 * Sets the announcement text. Usually when the turn switches, when players guess the cards, or when a team wins
	 * @param text The text to be displayed
	 * @param color The color of the text
	 */
	public void setAnnouncerText(String text, Color color) {
		announcerText.setText(text);
		announcerText.setForeground(color);
	}
}
