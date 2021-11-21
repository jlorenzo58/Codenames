package dev.turra.codenames.client.gui;

import dev.turra.codenames.client.mechanics.GameManager;
import dev.turra.codenames.client.mechanics.Player;
import dev.turra.codenames.common.Role;
import dev.turra.codenames.common.Team;
import dev.turra.codenames.common.network.sb.PacketServerCardClick;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class GameUI extends JFrame{
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
	private JLabel currentTurnLabel;

	public CardUI[][] board = new CardUI[5][5];

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
		boardPanel.setLayout(new GridLayout(5, 5, 2, 2));
		layout.show(mainPanel, "game");

		joinBlueOperativesButton.addActionListener(e -> manager.joinTeam(Team.BLUE, Role.OPERATIVE));
		joinBlueSpymastersButton.addActionListener(e -> manager.joinTeam(Team.BLUE, Role.SPYMASTER));
		joinRedOperativesButton.addActionListener(e -> manager.joinTeam(Team.RED, Role.OPERATIVE));
		joinRedSpymastersButton.addActionListener(e -> manager.joinTeam(Team.RED, Role.SPYMASTER));
		submitButton.addActionListener(e -> {

		});

		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				CardUI card = new CardUI(this, i, j);
				card.setWord("Word " + i + "x" + j);
				board[i][j] = card;
			}
		}
	}

	public void hideTeamButtons(){
		joinBlueOperativesButton.setVisible(false);
        joinBlueSpymastersButton.setVisible(false);
        joinRedOperativesButton.setVisible(false);
        joinRedSpymastersButton.setVisible(false);
	}

	public void showRoleUI(Role role){
		if(role == Role.SPYMASTER){
			spymasterUI.setVisible(true);
		}else{
			operativeUI.setVisible(true);
		}
	}

	public void updateCard(int x, int y, String word, Color color){
		board[x][y].setWord(word);
		if(color != null)
        	board[x][y].getWordLabel().setForeground(color);
	}

	public void cardClicked(int x, int y){
		if(manager.getPlayer().getTeam() == null || manager.getPlayer().getRole() == null)
			return;

		manager.getPlayer().sendPacket(new PacketServerCardClick(x, y));
	}

	public void revealCard(int x, int y, Color color) {
		board[x][y].reveal(color);
	}

	public void setHint(String hint, int wordAmount){
		givenHint.setText(hint + " " + wordAmount);
	}

	public void clearHint(){
		givenHint.setText("");
	}

	public void setTurn(Team team){
		currentTurnLabel.setText("Current Turn: " + team.toString());
		currentTurnLabel.setForeground(team.getColor().getColor());
	}

	public static void main(String[] args) {
		GameUI ui = new GameUI(new GameManager());
		ui.gameWindow();
	}
}
