package com.n1amr.tictactoe;

import java.awt.*;

public class Game {
	// com.n1amr.tictactoe.Game states constants
	public static final int PLAYER1_WINS = 0;
	public static final int PLAYER2_WINS = 1;
	public static final int DRAW = 2;
	public static final int UNFINISHED = 3;

	// com.n1amr.tictactoe.Game data
	private int gameState;
	private boolean multiplayer;
	private int nextPlayer;
	private Board board;

	public Game(int firstPlayer, char firstPlayerSymbol, boolean multiplayer) {
		reset(firstPlayer, multiplayer);
	}

	/**
	 * Reset all game data
	 */
	public void reset(int firstPlayer, boolean multiplayer) {
		this.gameState = UNFINISHED;
		this.multiplayer = multiplayer;
		this.nextPlayer = firstPlayer;
		this.board = new Board();
	}

	/**
	 * Play on board and return true if successful
	 */
	public boolean play(int i, int j, int player) {
		boolean valid = false;
		if (gameState == UNFINISHED && player == nextPlayer) {
			valid = board.play(i, j, player);
			if (valid) {
				updateState();
				changeTurn();
			}
		}
		return valid;
	}

	/**
	 * Play on board and return true if successful
	 */
	public boolean play(Point point, int player) {
		return play((int) point.getX(), (int) point.getY(), player);
	}

	private void changeTurn() {
		nextPlayer = board.getOpponent(nextPlayer);
	}

	public boolean isFinished() {
		return gameState != UNFINISHED;
	}

	private void updateState() {
		if (board.playerWins(Board.PLAYER1))
			gameState = PLAYER1_WINS;
		else if (board.playerWins(Board.PLAYER2))
			gameState = PLAYER2_WINS;
		else if (board.isDraw())
			gameState = DRAW;
		else
			gameState = UNFINISHED;
	}

	public String getStateText() {
		switch (gameState) {
			case PLAYER1_WINS:
				return ((multiplayer) ? "Player 1 wins" : "You win");
			case PLAYER2_WINS:
				return ((multiplayer) ? "Player 2" : "Computer") + " wins";
			case DRAW:
				return "Draw";
			case UNFINISHED:
				if (multiplayer)
					return "Player " + ((nextPlayer == Board.PLAYER1 ? "1" : "2") + "'s turn");
				else
					return ((nextPlayer == Board.PLAYER1) ? "Your turn" : "Thinking...");
			default:
				return "Error";
		}
	}

	public int getState() {
		return gameState;
	}

	public Board getBoard() {
		return board;
	}
}