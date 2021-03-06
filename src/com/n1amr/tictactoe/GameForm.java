package com.n1amr.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameForm extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;

	// com.n1amr.tictactoe.Game state variables
	private Game game;
	private boolean multiplayer;
	private int currentPlayer = Board.PLAYER1;

	// GUI components
	private JButton[][] gridButtons = new JButton[3][3];
	private JLabel resultLabel;
	private JButton startMultiPlayerButton;
	private JButton startSinglePlayerButton;
	private JButton exitButton;

	public GameForm() {
		setTitle("Tic Tac Toe");
		setResizable(false);

		game = new Game(Board.PLAYER1, Board.X_SYMBOL, false);

		// com.n1amr.tictactoe.Game result label
		resultLabel = new JLabel("None", SwingConstants.CENTER);
		resultLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
		resultLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// Control Buttons
		startSinglePlayerButton = new JButton("Start Singleplayer");
		startSinglePlayerButton.addActionListener(this);

		startMultiPlayerButton = new JButton("Start Multiplayer");
		startMultiPlayerButton.addActionListener(this);

		exitButton = new JButton("Exit");
		exitButton.addActionListener(this);

		// Grid buttons
		GridLayout gridLayout = new GridLayout(3, 3, 0, 0);
		JPanel gridPanel = new JPanel(gridLayout);
		gridPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++) {
				gridButtons[i][j] = new JButton();
				gridButtons[i][j].addActionListener(this);
				gridButtons[i][j].setFont(new Font(Font.MONOSPACED, 0, 25));
				gridButtons[i][j].setBackground(Color.WHITE);
				gridPanel.add(gridButtons[i][j]);
			}

		// Packing panels
		GridLayout mainLayout = new GridLayout(1, 0, 1, 1);
		setLayout(mainLayout);

		GridLayout controlsLayout = new GridLayout(4, 1, 0, 5);
		JPanel controlsPanel = new JPanel(controlsLayout);
		controlsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 0));

		controlsPanel.add(resultLabel);
		controlsPanel.add(startSinglePlayerButton);
		controlsPanel.add(startMultiPlayerButton);
		controlsPanel.add(exitButton);

		add(controlsPanel);
		add(gridPanel);

		resetGame();
		updateGameView();
	}

	public void showForm() {
		pack();

		// Show form on the center
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}

	/**
	 * Invoked when an action occurs.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// Get the GUI component of the event
		Object src = e.getSource();

		if (src instanceof JButton) {
			// Get the clicked button
			JButton button = (JButton) src;

			if (button == startSinglePlayerButton)
				startNewGame(false); // Start new a non-multiplayer game
			else if (button == startMultiPlayerButton)
				startNewGame(true); // Start new a multiplayer game
			else if (button == exitButton)
				System.exit(0);
			else
				for (int i = 0; i < 3; i++)
					for (int j = 0; j < 3; j++)
						if (button == gridButtons[i][j]) {
							play(button, i, j);
							return;
						}
		}
	}

	/**
	 * Starts a new game
	 *
	 * @param multiplayer</br> true: for multi player game </br>
	 *                         false: for single player game
	 */
	private void startNewGame(boolean multiplayer) {
		this.multiplayer = multiplayer;
		resetGame();
	}

	private void play(JButton button, int i, int j) {
		if (multiplayer) {
			boolean validPlay = game.play(i, j, currentPlayer);
			if (validPlay) {
				button.setText("" + game.getBoard().getPlayerSymbol(currentPlayer));
				currentPlayer = game.getBoard().getOpponent(currentPlayer);

				updateGameView();
			}
		} else {
			boolean validPlay = game.play(i, j, Board.PLAYER1);
			if (validPlay) {
				button.setText("" + game.getBoard().getPlayerSymbol(Board.PLAYER1));
				updateGameView();

				playAI();
			}

		}
	}

	private void playAI() {
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Point best_play = AI.getBestPlay(game.getBoard(), Board.PLAYER2);

				// Add delay for thinking
				try {
					Thread.sleep(400);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Attempt to play
				if (best_play != null) {
					boolean validPlay = game.play(best_play, Board.PLAYER2);
					if (validPlay) {
						// Write on button
						gridButtons[(int) best_play.getX()][(int) best_play.getY()]
							.setText("" + game.getBoard().getPlayerSymbol(Board.PLAYER2));
						updateGameView();
					}
				}
			}
		});
		thread.start();
	}

	/**
	 * Updates text in the result label and disables buttons if the game was
	 * finished
	 */
	private void updateGameView() {
		resultLabel.setText(game.getStateText());

		boolean buttonEnabled = game.getState() == Game.UNFINISHED;

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				gridButtons[i][j].setEnabled(buttonEnabled);
	}

	/**
	 * Reset current player, game controller and empty text on buttons
	 */
	private void resetGame() {
		int firstPlayer;
		if (multiplayer)
			firstPlayer = currentPlayer; // The last losing player
		else
			firstPlayer = (new Random().nextInt(2) == 0) ? Board.PLAYER1 : Board.PLAYER2;

		currentPlayer = firstPlayer;
		game.reset(currentPlayer, multiplayer);

		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				gridButtons[i][j].setText("" + Board.EMPTY);

		updateGameView();

		if (firstPlayer == Board.PLAYER2 && !multiplayer)
			playAI();
	}
}
