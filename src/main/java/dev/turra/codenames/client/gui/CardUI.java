package dev.turra.codenames.client.gui;

import dev.turra.codenames.common.CardColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CardUI extends JPanel {

	GameUI ui;

	public int x;
	public int y;

	private JLabel word;
	private Color color;

	public CardUI(GameUI ui, int x, int y) {
		this.setMaximumSize(new Dimension(150, 100));
		this.ui = ui;
        this.word = new JLabel("");
		this.x = x;
		this.y = y;
		this.color = CardColor.BG.getColor();
        this.setBackground(color);
        this.add(this.word);

		this.word.setBorder(new EmptyBorder(50, 50, 50, 50));
		this.word.setHorizontalAlignment(SwingConstants.CENTER);
		this.word.setVerticalAlignment(SwingConstants.CENTER);
		this.word.setFont(new Font("JetBrains Mono", Font.BOLD, 20));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ui.cardClicked(x, y);
				System.out.println("Clicked on card " + x + " " + y);
			}
		});

		ui.boardPanel.add(this);
    }

	public JLabel getWordLabel() {
        return word;
    }

	public String getWord() {
		return word.getText();
	}

	public void setWord(String word) {
		this.word.setText(word);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void reveal(Color color) {
		word.setForeground(CardColor.REVEALED.getColor());
		setBackground(color);
	}
}
