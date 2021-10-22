package dev.turra.codenames.client.gui;

import javax.swing.*;
import java.awt.*;

public class Window {

	private final String title = "Codenames";

	private final Color BACKGROUND = new Color(52,52,52);

	private static Window window;
	
	private Window(){
//		JFrame frame = new JFrame(title);
//
//		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//		frame.setResizable(false);
//		frame.setSize(500,500);
//		frame.setLocationRelativeTo(null);
//		frame.setLayout(null);
//		frame.setVisible(true);
//
//		frame.getContentPane().setBackground(BACKGROUND);

//		GameFrame frame = new GameFrame();
		Login login = new Login();
	}
	
	public static Window get(){
		if(window == null)
			window = new Window();
		
		return window;
	}

	public static void main(String[] args) {
		Window.get();
	}

}
