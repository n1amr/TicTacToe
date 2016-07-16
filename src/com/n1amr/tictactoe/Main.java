package com.n1amr.tictactoe;

import javax.swing.*;

public class Main {

	public static void main(String[] args) {
		/** Run the GUI form */
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameForm().showForm();
			}
		});
	}
}