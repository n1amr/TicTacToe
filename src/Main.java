import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		System.out.println("Hello");

		Board board = new Board();

		char player;
		System.out.println(board);
		for (int game = 0; !board.gameFinished(); game++) {
			player = (game % 2 == 0) ? Board.X_PLAYER : Board.O_PLAYER;
			if (player == Board.X_PLAYER) {
				int i = in.nextInt() - 1;
				int j = in.nextInt() - 1;
				board.play(i, j, player);
			} else {
				board = board.smartPlay(board.O_PLAYER);
			}
			System.out.println(board);
		}

		if (board.getStatus() == Board.X_WINS)
			System.out.println("X wins");
		else if (board.getStatus() == Board.O_WINS)
			System.out.println("O wins");
		else if (board.getStatus() == Board.DRAW)
			System.out.println("Draw");
	}
}