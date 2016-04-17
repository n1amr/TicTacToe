import java.awt.Point;
import java.util.Random;

public class GameController {

	private Board board;
	private char player;
	private Random random;

	// private void randomTurn() {
	// player = random.nextInt() % 2 == 0 ? Board.X_PLAYER : Board.O_PLAYER;
	// }

	public GameController() {
		board = new Board();

		// random = new Random(System.currentTimeMillis());
		// randomTurn();
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
		if (!board.gameFinished()) {
			p = board.smartPlay(Board.O_PLAYER);
			System.out.println(board);
		}
		return p;
	}

	public void run() {
		if (board.getStatus() == Board.X_WINS)
			System.out.println("X wins");
		else if (board.getStatus() == Board.O_WINS)
			System.out.println("O wins");
		else if (board.getStatus() == Board.DRAW)
			System.out.println("Draw");

		// char player;
		// System.out.println(board);
		// for (int game = 0; !board.gameFinished(); game++) {
		// player = (game % 2 == 0) ? Board.X_PLAYER : Board.O_PLAYER;
		// if (player == Board.X_PLAYER) {
		// int i = in.nextInt() - 1;
		// int j = in.nextInt() - 1;
		// board.play(i, j, player);
		// } else {
		// board = board.smartPlay(board.O_PLAYER);
		// }
		// System.out.println(board);
		// }
		//
		// if (board.getStatus() == Board.X_WINS)
		// System.out.println("X wins");
		// else if (board.getStatus() == Board.O_WINS)
		// System.out.println("O wins");
		// else if (board.getStatus() == Board.DRAW)
		// System.out.println("Draw");
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
