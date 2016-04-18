import java.awt.Point;

public class Game {
	public static final int PLAYER1_WINS = 0;
	public static final int PLAYER2_WINS = 1;
	public static final int DRAW = 2;
	public static final int UNFINISHED = 3;

	private int gameState;
	private boolean multiplayer;
	private int nextPlayer;
	private Board board;

	public Game(int firstPlayer, char firstPlayerSymbol, boolean multiplayer) {
		this.multiplayer = multiplayer;
		this.nextPlayer = firstPlayer;
		this.board = new Board();
		this.gameState = UNFINISHED;
	}

	/** Play and return result of the game */
	public boolean play(int i, int j, int player) {
		if (gameState == UNFINISHED && player == nextPlayer) {
			boolean valid = board.play(i, j, player);
			if (valid) {
				updateGameState();
				changeTurn();
			}
			return valid;
		} else {
			if (gameState != UNFINISHED)
				System.out.println("Error: Game is finished");
			else if (player != nextPlayer)
				System.out.println("Error: That's not " + board.getPlayerSymbol(player) + " turn");
			return false;
		}
	}

	public boolean play(Point point, int player) {
		return play((int) point.getX(), (int) point.getY(), player);
	}

	public int getGameState() {
		return gameState;
	}

	public void updateGameState() {
		if (board.playerWins(Board.PLAYER1))
			gameState = PLAYER1_WINS;
		else if (board.playerWins(Board.PLAYER2))
			gameState = PLAYER2_WINS;
		else if (board.isDraw())
			gameState = DRAW;
		else
			gameState = UNFINISHED;
	}

	private void changeTurn() {
		nextPlayer = board.getOpponent(nextPlayer);
	}

	public boolean isGameFinished() {
		return gameState != UNFINISHED;
	}

	public Board getBoard() {
		return board;
	}

	public void resetGame(int firstPlayer) {
		board = new Board();
		gameState = UNFINISHED;
		nextPlayer = firstPlayer;
	}

	public String getStatus() {
		switch (gameState) {
		case PLAYER1_WINS:
			return (multiplayer) ? "Player 1 wins" : "You win";
		case PLAYER2_WINS:
			return ((multiplayer) ? "Player 2" : "Computer") + " wins";
		case DRAW:
			return "Draw";
		case UNFINISHED:
			if (multiplayer)
				return "Player " + (nextPlayer == Board.PLAYER1 ? "1" : "2") + "'s turn";
			else
				return (nextPlayer == Board.PLAYER1 ? "Your turn" : "Thinking...");
		default:
			return "Error";
		}
	}

}
