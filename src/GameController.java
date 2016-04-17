import java.awt.Point;
import java.util.Random;

public class GameController {

	private Board board;
	private char player;

	public GameController() {
		board = new Board();

		player = Board.X_PLAYER;
	}

	public void reset() {
		board = new Board();
	}

	public boolean play(int i, int j) {
		if (board.isEmptyCell(i, j)) {
			board.play(i, j, player);
			return true;
		}
		return false;
	}

	public Point respond() {
		Point p = null;
		if (!board.gameFinished())
			p = board.smartPlay(Board.O_PLAYER);
		return p;
	}

	public void run() {
		if (board.getStatus() == Board.X_WINS)
			System.out.println("X wins");
		else if (board.getStatus() == Board.O_WINS)
			System.out.println("O wins");
		else if (board.getStatus() == Board.DRAW)
			System.out.println("Draw");
	}

	public String getStatus() {
		if (board.getStatus() == Board.X_WINS) {
			return "You win";
		}
		if (board.getStatus() == Board.O_WINS) {
			return "Computer wins";
		}
		if (board.getStatus() == Board.DRAW) {
			return "Draw";
		}
		if (board.getStatus() == Board.UNFINISHED) {
			return "Your turn (X)";
		}
		return null;
	}

	public boolean isFinished() {
		return board.gameFinished();
	}
}
