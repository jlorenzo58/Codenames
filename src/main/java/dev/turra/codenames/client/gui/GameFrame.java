package dev.turra.codenames.client.gui;

import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

	private final String title = "Codenames";

	private final Color BACKGROUND = new Color(52,52,52);

	public GameFrame() throws HeadlessException {

		this.setTitle(this.title);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setSize(500,500);
		this.setVisible(true);

		JPanel titlePanel = new JPanel();
		titlePanel.setBackground(BACKGROUND);

		JLabel title = new JLabel();
		title.setText(this.title);
		title.setForeground(new Color(74, 200, 242));
		title.setFont(new Font("Franklin Gothic Medium", Font.BOLD, 72));
		title.setHorizontalAlignment(JLabel.CENTER);
		title.setVerticalAlignment(JLabel.TOP);
		title.setBounds(0, 0, 1000, 1000);

		titlePanel.add(title);
		this.add(titlePanel);
	}
}
