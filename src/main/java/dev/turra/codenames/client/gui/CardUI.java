package dev.turra.codenames.client.gui;

import dev.turra.codenames.common.CardColor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Represents a card UI in the game.
 */
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

	/**
	 *
	 * @return The {@link JLabel} that represents the word on the card.
	 */
	public JLabel getWordLabel() {
        return word;
    }

	/**
	 *
	 * @return The word on the card.
	 */
	public String getWord() {
		return word.getText();
	}

	/**
	 * Sets the word on the card.
	 * @param word Word to set.
	 */
	public void setWord(String word) {
		this.word.setText(word);
	}

	/**
	 *
	 * @return The color of the card.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Sets the color of the card.
	 * @param color Color to set.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Turn over the card with the given color
	 * @param color Color to turn over.
	 */
	public void reveal(Color color) {
		word.setForeground(CardColor.REVEALED.getColor());
		setBackground(color);
	}
}
